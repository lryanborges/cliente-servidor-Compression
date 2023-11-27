package application;

import entity.Veiculo;
import exceptions.SameKeyException;
import exceptions.WrongInsertException;
import codification.NoHuffman;
import codification.TreeHuffman;
import entity.Condutor;

public class Protocolo {

	// o servidor padrão é o de Encadeamento Exterior
	Servidor server = new Servidor();
	public static TreeHuffman huffman;

	public void enderecamentoAberto(boolean escolhido) {
		// daí caso o cliente queira utilizar o Endereçamento Aberto
		if (escolhido) {
			server = new ServidorEnderecamentoAberto();
		} else {
			// caso não, fica como Encadeamento Exterior mesmo
		}
	}

	public void listar() {
		server.printTable();
	}

	public String comprimir(Veiculo veic) {
		String comprimido = veic.toString();
		// comprimido = "ABACCDA";
		huffman = new TreeHuffman();
		char caracs[] = new char[comprimido.length()];
		int frequencias[] = new int[comprimido.length()];

		// contar caracs e suas frequencias
		NoHuffman nos[] = new NoHuffman[comprimido.length()];
		int contadorNos = 0;

		for (int i = 0; i < comprimido.length(); i++) {
			char caracter = comprimido.charAt(i);

			boolean encontrado = false;
			for (int j = 0; j < contadorNos; j++) {
				if (caracs[j] == caracter) {
					frequencias[j]++;
					encontrado = true;
					break;
				}
			}

			if (!encontrado) {
				caracs[contadorNos] = caracter;
				frequencias[contadorNos] = 1;
				nos[contadorNos] = new NoHuffman();
				nos[contadorNos].setCaractere(caracter);
				nos[contadorNos].setFrequencia(1);
				contadorNos++;
			}
		}
		// ---- já contou

		huffman.construirArvore(contadorNos, caracs, frequencias);
		huffman.imprimirCodigo(huffman.getRaiz(), "");
		
		String codificado = "";
		
		for(int i = 0; i < comprimido.length(); i++) {
			for(int j = 0; j < contadorNos; j++) {
				if (huffman.getCodigos()[j].containsKey(comprimido.charAt(i))) {
				    codificado = codificado + huffman.getCodigos()[j].get(comprimido.charAt(i));
				}
			}
		}

		return codificado;
	}
	
	public Veiculo buscar(int renavam) {
		No<Veiculo> encontrado = server.buscar(renavam);

		if (encontrado == null) {
			System.out.println("Renavam não cadastrado.");
			return null;
		} else {
			System.out.println();
			System.out.println("Placa: " + encontrado.getValor().getPlaca());
			System.out.println("Renavam: " + encontrado.getPkey());
			System.out.println("Condutor: " + encontrado.getValor().getCondutor().getNome() + ", "
					+ encontrado.getValor().getCondutor().getCpf());
			System.out.println("Modelo: " + encontrado.getValor().getModelo());
			System.out.println("Data de Fabricação: " + encontrado.getValor().getFabricacao());
			System.out.println("Frequência: " + encontrado.getFrequencia());
			System.out.println();
		}

		return encontrado.getValor();
	}

	public Veiculo buscar(int renavam, boolean flag) {
		No<Veiculo> encontrado = server.buscar(renavam, flag);

		if (encontrado == null) {
			System.out.println("Renavam não cadastrado.");
			return null;
		} else {
			System.out.println();
			System.out.println("Placa: " + encontrado.getValor().getPlaca());
			System.out.println("Renavam: " + encontrado.getPkey());
			System.out.println("Condutor: " + encontrado.getValor().getCondutor().getNome() + ", "
					+ encontrado.getValor().getCondutor().getCpf());
			System.out.println("Modelo: " + encontrado.getValor().getModelo());
			System.out.println("Data de Fabricação: " + encontrado.getValor().getFabricacao());
			System.out.println("Frequência: " + encontrado.getFrequencia());
			System.out.println();
		}

		return encontrado.getValor();
	}
	
	public void cadastrar(Veiculo veiculo) throws SameKeyException {
		server.inserir(veiculo.getRenavam(), veiculo);

		System.out.println("--------------------------------------------------");
		System.out.println("Veículo cadastrado com sucesso.");

	}
	
	// cadastrar só que com Veículo comprimido me String
	public void cadastrar(String veiculoComprimido) throws SameKeyException {
		server.inserir(veiculoComprimido, huffman);

		System.out.println("--------------------------------------------------");
		System.out.println("Veículo cadastrado com sucesso.");

	}

	public void alterar(int renavam, Veiculo valor) throws SameKeyException, WrongInsertException {
		No<Veiculo> alterado = server.alterar(renavam, valor);

		if (alterado != null) {
			System.out.println("--------------------------------------------------");
			System.out.println("Veículo alterado com sucesso.");
		}
	}
	
	// alterar só que com Veículo comprimido me String
	public void alterar(String veiculoComprimido) throws SameKeyException, WrongInsertException {
		No<Veiculo> alterado = server.alterar(veiculoComprimido, huffman);

		if (alterado != null) {
			System.out.println("--------------------------------------------------");
			System.out.println("Veículo alterado com sucesso.");
		}
	}

	public void remover(int renavam) throws WrongInsertException {
		No<Veiculo> removido = server.remover(renavam);

		if (removido != null) {
			System.out.println("--------------------------------------------------");
			System.out.println("Veículo removido com sucesso.");
		}
	}

	public void quantidadeVeiculos() {
		System.out.println("Quantidade de Veículos: " + server.contarVeiculos());
	}

	public void preencherServidor() throws SameKeyException {

		server.cleanLog();

		String comprimido = comprimir(new Veiculo("QWE1234", 867530912, new Condutor("Icaro Rabelo", 8888), "kwid", "13/02/2018"));
		server.inserir(comprimido, huffman);		
		comprimido = comprimir(new Veiculo("ABC9898", 249876530, new Condutor("Ryan Borges", 2407), "carro", "20/04/2014"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("POP2217", 431279865, new Condutor("Brenno Kevyn", 1000), "bike", "07/07/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LSY2424", 785043216, new Condutor("Davi Rabelo", 2424), "kwid", "29/06/2016"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("SWY8712", 592187643, new Condutor("Vinicius Dantas", 1024), "caminhão", "08/09/2011"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("CIS1010", 356908124, new Condutor("Mikael Alves", 6969), "moto", "31/01/2022"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LOL2020", 678459231, new Condutor("Jovit Sales", 9090), "hilux", "04/02/2015"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LPO0921", 123587690, new Condutor("JL Galdino", 7371), "motoca", "20/01/2005"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("QGX8321", 984021356, new Condutor("Whesley Felipe", 8210), "navio", "23/04/2001"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("DSA0231", 746205891, new Condutor("Sarah Tomaz", 9211), "jetski", "25/03/2000"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("EWQ2192", 315892467, new Condutor("Thiago Henrique", 7432), "quadriciclo", "14/09/2010"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("PEW3121", 650198432, new Condutor("Valentina Lacerda", 8431), "carro", "20/04/2014"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LOK1919", 879642310, new Condutor("Nickolas Emanuel", 2812), "bike", "07/07/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("VCS5416", 524769013, new Condutor("Mateus Lanuce", 2048), "caminhão", "08/09/2011"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("PLS7671", 167398245, new Condutor("Paulo Roberto", 8388), "moto", "31/01/2022"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("EWQ0001", 390847516, new Condutor("Arthur Peixoto", 5532), "hilux", "04/02/2015"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("ESQ0413", 218643759, new Condutor("Caio Vinicius", 5188), "motoca", "20/01/2005"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LSA1239", 467931028, new Condutor("Edivaldo Raimundo", 1112), "navio", "23/04/2001"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LSV9299", 859034162, new Condutor("Igor Vinicius", 9385), "jetski", "25/03/2000"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("PON9784", 712406935, new Condutor("Wilgner Elanio", 8321), "quadriciclo", "14/09/2010"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("GHI5432", 230915678, new Condutor("Rafael Oliveira", 2345), "caminhonete", "15/08/2017"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("JKL6789", 582630194, new Condutor("Carla Silva", 6789), "carro", "30/06/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("MNO4321", 946701823, new Condutor("Pedro Santos", 9876), "moto", "10/01/2022"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("PQR7654", 174398562, new Condutor("Mariana Fernandes", 4567), "caminhão", "25/05/2015"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("STU8765", 310275489, new Condutor("Lucas Almeida", 5432), "moto", "18/12/2020"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("VWX9876", 684931027, new Condutor("Larissa Costa", 8901), "carro", "07/09/2016"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("YZA1098", 542109783, new Condutor("Fernando Rodrigues", 1023), "caminhão", "12/04/2014"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("BCD2109", 890156342, new Condutor("Camila Pereira", 3456), "moto", "22/11/2018"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("EFG3210", 765498201, new Condutor("Eduardo Souza", 5678), "carro", "03/02/2021"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("HIJ4321", 923615874, new Condutor("Aline Castro", 7890), "moto", "09/10/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("KLM5432", 439802156, new Condutor("Isabel Ramos", 1111), "carro", "02/04/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("NOP6789", 207584316, new Condutor("Ricardo Alves", 2222), "moto", "17/10/2020"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("QRS4321", 681023495, new Condutor("Carolina Costa", 3333), "caminhão", "28/08/2015"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("TUV7654", 153496728, new Condutor("Thales Ferreira", 4444), "moto", "09/12/2021"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("WXY8765", 720185634, new Condutor("Fernanda Lima", 5555), "carro", "14/06/2017"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("ZAB9876", 368972451, new Condutor("Gustavo Oliveira", 6666), "caminhão", "21/03/2016"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("CDE1098", 596734028, new Condutor("Isadora Souza", 7777), "moto", "05/11/2018"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("FGH2109", 817362594, new Condutor("Marcelo Pereira", 8888), "carro", "23/01/2020"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("IJK3210", 265487903, new Condutor("Lívia Rodrigues", 9999), "moto", "30/07/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("LMN4321", 903124875, new Condutor("Leonardo Castro", 0000), "carro", "10/09/2022"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("OPQ5432", 481753296, new Condutor("Felipe Santos", 1111), "carro", "15/03/2021"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("RST6789", 637218945, new Condutor("Ana Paula Oliveira", 2222), "moto", "29/09/2020"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("UVW4321", 129546780, new Condutor("Carlos Mendes", 3333), "caminhão", "03/11/2016"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("XYZ7654", 874062931, new Condutor("Júlia Almeida", 4444), "moto", "18/05/2019"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("ABC8765", 356821407, new Condutor("Roberto Lima", 5555), "carro", "07/08/2018"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("DEF9876", 984370215, new Condutor("Kátia Abreu", 6666), "caminhão", "12/06/2017"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("GHI1098", 524896703, new Condutor("Larissa Souza", 7777), "moto", "25/04/2022"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("JKL2109", 769230158, new Condutor("Eduardo Pereira", 8888), "carro", "10/02/2015"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("MNO3210", 210395687, new Condutor("Amanda Rodrigues", 9999), "moto", "29/12/2021"));
		server.inserir(comprimido, huffman);
		comprimido = comprimir(new Veiculo("PQR4321", 643810972, new Condutor("Vinícius Castro", 0000), "carro", "03/07/2018"));
		server.inserir(comprimido, huffman);

	}

}
