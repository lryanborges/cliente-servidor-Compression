package application;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import codification.NoHuffman;
import codification.TreeHuffman;
import entity.Condutor;
import entity.Veiculo;
import exceptions.SameKeyException;
import exceptions.WrongInsertException;

// Este servidor é utiliza a estrutura de uma HashTable
public class Servidor {

	protected No<Veiculo>[] table;
	protected int mod = 23;
	private int fatorDeCarga = 0;

	public Servidor(int tamanho) {
		mod = tamanho;
		this.table = new No[mod];
	}

	public Servidor() {
		this.table = new No[mod];
	}

	int hash(long num) {
		return (int) (num % mod);
	}
	
	boolean autoAjustar(No<Veiculo> mexido) {
	    int hashedNum = hash(mexido.getPkey());
	    No<Veiculo> current = table[hashedNum];

	    while (current.getNext() != null) {
	        No<Veiculo> next = current.getNext();

	        if (current.getFrequencia() < next.getFrequencia()) {
	            Veiculo tempValue = current.getValor();
	            long tempPkey = current.getPkey();
	            int tempFrequency = current.getFrequencia();

	            current.setValor(next.getValor());
	            current.setPkey(next.getPkey());
	            current.setFrequencia(next.getFrequencia());

	            next.setValor(tempValue);
	            next.setPkey(tempPkey);
	            next.setFrequencia(tempFrequency);

	            current = table[hashedNum];  
	        } else {
	            current = current.getNext();  
	        }
	    }

	    return true;
	}

	Veiculo descomprimir(String codificado, TreeHuffman huffman) {
		
		String descodificado = "";
		NoHuffman next = huffman.getRaiz();

		for(int i = 0; i < codificado.length(); i++) {
			if(codificado.charAt(i) == '0') {
				next = next.getPrev();
			} else if(codificado.charAt(i) == '1') {
				next = next.getNext();
			}
			
			if(next.getPrev() == null && next.getNext() == null) {
				descodificado = descodificado + next.getCaractere();
				next = huffman.getRaiz();
			}
		}
		
		String[] dados = descodificado.split("#");
		
		return new Veiculo(dados[0], Long.parseLong(dados[1]), new Condutor(dados[2], Long.parseLong(dados[3])), dados[4], dados[5]);
	}
	
	void inserir(long num, Veiculo novo) throws SameKeyException {
		int hashedNum = hash(num);

		if (table[hashedNum] == null) {
			table[hashedNum] = new No<Veiculo>(num, novo);
			writeLog("\tVeículo ID " + num + " adicionado.\n");
			fatorDeCarga();
		} else {
			No<Veiculo> no = table[hashedNum];
			while (no.getNext() != null) {
				if (no.getPkey() == num) {
					throw new SameKeyException(num);
				}
				no = no.getNext();
			}

			if (no.getPkey() != num) {
				No<Veiculo> novoNo = new No<Veiculo>(num, novo);
				novoNo.setNext(table[hashedNum]);
				table[hashedNum] = novoNo;
				writeLog("\tVeículo ID " + num + " adicionado.\n");
				fatorDeCarga();
			}
		}
	}
	
	void inserir(String codificado, TreeHuffman huffman) throws SameKeyException {
		// novo e num viriam dos parametros, q agora é uma string codificada. agora os dois vem da descompressão
		Veiculo novo = descomprimir(codificado, huffman);
		long num = novo.getRenavam();
		
		inserir(num, novo);	
	}

	No<Veiculo> alterar(long num, Veiculo valor) throws WrongInsertException {
		int hashedNum = hash(num);
		No<Veiculo> no = table[hashedNum];

		while (no != null) {
			if (no.getPkey() == num) {
				no.setValor(valor);
			    no.setFrequencia(no.getFrequencia() + 1);
				// -- pra poder retornar o certo
				No<Veiculo> retornoNo = new No<Veiculo>();
				retornoNo.setPkey(no.getPkey());
				retornoNo.setValor(no.getValor());
				retornoNo.setFrequencia(no.getFrequencia());
				// --
				autoAjustar(no);
				return retornoNo;
			}

			no = no.getNext();
		}

		throw new WrongInsertException();
	}
	
	No<Veiculo> alterar(String codificado, TreeHuffman huffman) throws WrongInsertException{
		Veiculo alterado = descomprimir(codificado, huffman);
		long num = alterado.getRenavam();
		
		return alterar(num, alterado);
	}

	No<Veiculo> remover(long num) throws WrongInsertException {
		int hashedNum = hash(num);
		No<Veiculo> no = table[hashedNum];

		// se for o primeiro
		if (no.getPkey() == num) {
			table[hashedNum] = no.getNext();
			writeLog("\tVeículo ID " + num + " removido.\n");
			fatorDeCarga();
			return no;
		}

		while (no != null) {
			No<Veiculo> next = no.getNext();

			// se for o próximo
			if (next.getPkey() == num) {
				no.setNext(next.getNext());
				writeLog("\tVeículo ID " + num + " removido.\n");
				fatorDeCarga();
				return no;
			}

			no = no.getNext();
		}

		throw new WrongInsertException();
	}

	void printTable() {
		No<Veiculo> no;
		for (int i = 0; i < table.length; i++) {
			no = table[i];
			System.out.print("Linha " + i + ": ");

			while (no != null) {
				System.out.print(no.getValor().getRenavam() + " --> ");
				no = no.getNext();
			}

			System.out.println("null");
		}
	}

	void print() {
		No<Veiculo> no;
		for (int i = 0; i < table.length; i++) {
			no = table[i];

			while (no != null) {
				System.out.println("--------------------------------------------------");
				System.out.println("Placa: " + no.getValor().getPlaca());
				System.out.println("Renavam: " + no.getPkey());
				System.out.println("Condutor: " + no.getValor().getCondutor().getNome() + ", "
						+ no.getValor().getCondutor().getCpf());
				System.out.println("Modelo: " + no.getValor().getModelo());
				System.out.println("Data de Fabricação: " + no.getValor().getFabricacao());
				System.out.println("Frequência: " + no.getFrequencia());
				no = no.getNext();
			}
		}
	}

	int contarVeiculos() {
		int cont = 0;
		No<Veiculo> no;

		for (int i = 0; i < table.length; i++) {
			no = table[i];

			while (no != null) {
				cont++;
				no = no.getNext();
			}
		}

		return cont;
	}

	No<Veiculo> buscar(long num) {
		return buscar(num, false);
	}
	
	No<Veiculo> buscar(long num, boolean flag) {

		// hashedNum é o resto do número pelo modulo, meio que a linha da tabela que ele
		// vai ficar
		int hashedNum = hash(num);
		No<Veiculo> no = table[hashedNum];

		while (no != null) {
			if (no.getPkey() == num) {
				if(flag) {
					no.setFrequencia(no.getFrequencia() + 1);		
				}
				// -- pra poder retornar o certo
				No<Veiculo> retornoNo = new No<Veiculo>();
				retornoNo.setPkey(no.getPkey());
				retornoNo.setValor(no.getValor());
				retornoNo.setFrequencia(no.getFrequencia());
				// --
				autoAjustar(no);
				return retornoNo;
			}

			no = no.getNext();
		}

		return null; // quer dizer que não achou
	}
	
	void fatorDeCarga() {
		float fator = 0;
		DecimalFormat df = new DecimalFormat("#.###");
		
		fator = (float) contarVeiculos() / mod;
		
		writeLog("Fator de Carga: " + df.format(fator) + "\n");
	}
	
	boolean primo(long num) {
		int cont = 0;
		for(long i = num; i != 0; i--) {
			if(num % i == 0) {
				cont++;
			}
		}
		
		if(cont <= 2) {
			return true;
		} else {
			return false;	
		}
	}

	public void cleanLog() {
		String path = "src/log.txt";

		FileWriter writer;
		try {
			writer = new FileWriter(path, false);
			writer.write("");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeLog(String log) {
		String path = "src/log.txt";

		FileWriter writer;
		try {
			writer = new FileWriter(path, true);
			writer.write(log);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public No<Veiculo>[] getTable() {
		return table;
	}

	public void setTable(No<Veiculo>[] table) {
		this.table = table;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public int getFatorDeCarga() {
		return fatorDeCarga;
	}

	public void setFatorDeCarga(int fatorDeCarga) {
		this.fatorDeCarga = fatorDeCarga;
	}

}
