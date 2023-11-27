package application;

import entity.Veiculo;
import exceptions.SameKeyException;
import exceptions.WrongInsertException;

public class ServidorEnderecamentoAberto extends Servidor {
	
	boolean exibLog = true;
	int contRecursividade = 0;

	public ServidorEnderecamentoAberto() {
	}

	@Override
	void inserir(long num, Veiculo novo) {
		int hashedNovo = hash(num);

		while (table[hashedNovo] != null) {
			if (table[hashedNovo].getPkey() == num) {
				break;
			} else {
				hashedNovo = (hashedNovo + 1) % mod;
			}
			if(hashedNovo == mod - 1) {
				// aqui trocar a table[modAntigo] pela table[modNovo]
				extendTable();
			}
		}
		
		if (table[hashedNovo] == null) {
			table[hashedNovo] = new No<Veiculo>(num, novo);
			if(exibLog) {
				writeLog("\tVeículo ID " + num + " adicionado.\n");
				fatorDeCarga();
			}
		}

	}
	
	@Override
	No<Veiculo> alterar(long num, Veiculo valor) throws WrongInsertException{
		No<Veiculo> altera = buscar(num);
		
		for(int i = 0; i < table.length; i++) {
			if(altera == table[i]) {
				table[i].setValor(valor);
				return table[i];
			}
		}
		
		throw new WrongInsertException();
	}
	
	@Override
	No<Veiculo> remover(long num) throws WrongInsertException{
		No<Veiculo> remove = buscar(num);
		
		for(int i = 0; i < table.length; i++) {
			if(remove == table[i]) {
				table[i] = null;
				writeLog("\tVeículo ID " + num + " removido.\n");
				fatorDeCarga();
				return remove;
			} 
		}
		
		throw new WrongInsertException();
	}

	@Override
	No<Veiculo> buscar(long num) {
		int hashedNum = hash(num);

		while (table[hashedNum] != null) {

			if (table[hashedNum].getPkey() == num) {
				return table[hashedNum];
			} else {
				hashedNum = (hashedNum + 1) % mod;
			}

		}

		return null;

	}

	@Override
	void print() {
		for (No<Veiculo> each : table) {
			if (each != null) {
				System.out.println("--------------------------------------------------");
				System.out.println("Placa: " + each.getValor().getPlaca());
				System.out.println("Renavam: " + each.getPkey());
				System.out.println("Condutor: " + each.getValor().getCondutor().getNome() + ", "
						+ each.getValor().getCondutor().getCpf());
				System.out.println("Modelo: " + each.getValor().getModelo());
				System.out.println("Data de Fabricação: " + each.getValor().getFabricacao());
			}
		}
	}

	@Override
	void printTable() {
		for (int i = 0; i < mod; i++) {
			if (table[i] != null) {
				System.out.println(i + " --> " + table[i].getPkey());
			} else {
				System.out.println(i);
			}
		}
	}
	
	int nextMod() {
		mod = mod + 1;
		while(!(primo(mod))) {
			mod = mod + 1;
		}
		
		return mod;
	}
	
	void extendTable() {
		No<Veiculo>[] auxTable = new No[mod];
		
		for(int i = 0; i < table.length; i++) {
			auxTable[i] = table[i];
		}
		
		nextMod();
		
		table = new No[mod];
		
		for(No<Veiculo> each : auxTable) {
			exibLog = false;
			if(each != null) {
				contRecursividade++;
				inserir(each.getPkey(), each.getValor());
				contRecursividade--;
			}
		}
		if(contRecursividade == 0) {
			exibLog = true;
		}
	}
	
	@Override
	int contarVeiculos() {
		int cont = 0;

		for (int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				cont++;	
			}
		}
		
		return cont;
	}

}
