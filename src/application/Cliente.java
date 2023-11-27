package application;

import java.io.IOException;
import java.util.Scanner;

import entity.Condutor;
import entity.Veiculo;
import exceptions.SameKeyException;
import exceptions.WrongInsertException;

public class Cliente {

	private String login;
	private String user;
	private String password;
	static Protocolo protocolo = new Protocolo();

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws SameKeyException, IOException {
		//escolherTipoColisão();
		protocolo.preencherServidor();
		System.out.println("--------------------------------------------------");
		System.out.println("Sistema de Veículos");
		escolherOpc();
	}
	
	// substituir retorno pra definir o tipo de tratamento de colisão lá no servidor
	public static void escolherTipoColisão() {
		
		int opc = 0;
		
		System.out.println("--------------------------------------------------");
		System.out.println("[1] - Colisões por Encadeamento Exterior");
		System.out.println("[2] - Colisões por Endereçamento Aberto");
		System.out.println("--------------------------------------------------");
		System.out.print("Sua opção: ");
		opc = scan.nextInt();
		
		switch(opc) {
		case 1:
			protocolo.enderecamentoAberto(false);
			break;
		case 2:
			protocolo.enderecamentoAberto(true);
			break;
		default:
			// Encadeamento Exterior é o padrão
			protocolo.enderecamentoAberto(false);
		}
		
	}

	public static void escolherOpc() {

		int opc = 0;

		while (opc >= 0 && opc < 7) {
			
			System.out.println("--------------------------------------------------");
			System.out.println("[1] - Listar");
			System.out.println("[2] - Buscar");
			System.out.println("[3] - Cadastrar");
			System.out.println("[4] - Editar");
			System.out.println("[5] - Excluir");
			System.out.println("[6] - Checar número de Veículos");
			System.out.println("[7] - Sair");
			System.out.println("--------------------------------------------------");
			System.out.print("Sua opção: ");
			opc = scan.nextInt();

			switch (opc) {
			case 1:
				protocolo.listar();
				break;
			case 2:
				System.out.println("-----------------------");
				System.out.println("Busca de Veículo");
				System.out.println("-----------------------");
				System.out.print("Renavam da busca: ");
				int renavamBuscar = scan.nextInt();

				protocolo.buscar(renavamBuscar, true);

				break;
			case 3:
				System.out.println("-----------------------");
				System.out.println("Cadastro de Veículo");
				System.out.println("-----------------------");
				System.out.print("Renavam: ");
				int renavam = scan.nextInt();
				System.out.print("Placa: ");
				String placa = scan.next();
				System.out.print("Modelo: ");
				String modelo = scan.next();
				System.out.print("Data de Fabricação: ");
				String fabricacao = scan.next();
				System.out.print("Nome do Motorista: ");
				String nome = scan.next() + scan.nextLine();
				System.out.print("CPF do Motorista: ");
				long cpf = scan.nextLong();
				
				// compressão do veículo

				try {
					String comprimido = protocolo.comprimir(new Veiculo(placa, renavam, new Condutor(nome, cpf), modelo, fabricacao));
					protocolo.cadastrar(comprimido);
				} catch (SameKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				break;
			case 4:
				System.out.println("-----------------------");
				System.out.println("Alteração de Veículo");
				System.out.println("-----------------------");
				System.out.print("Renavam pra alterar: ");
				int renavamAlterar = scan.nextInt();

				Veiculo alterar = protocolo.buscar(renavamAlterar, false);
				System.out.println("-----------------------");
				System.out.println("[1] - Alterar renavam");
				System.out.println("[2] - Alterar placa");
				System.out.println("[3] - Alterar condutor");
				System.out.println("[4] - Alterar modelo");
				System.out.println("[5] - Alterar data de fabricação");
				System.out.println("[6] - Cancelar alteração");
				System.out.println("-----------------------");
				System.out.print("Sua opção: ");
				int alterarOpc = scan.nextInt();

				switch (alterarOpc) {
				case 1:
					System.out.print("Novo renavam: ");
					alterar.setRenavam(scan.nextInt());
					break;
				case 2:
					System.out.print("Nova placa: ");
					alterar.setPlaca(scan.next());
					break;
				case 3:
					System.out.print("Novo nome do condutor: ");
					String novoNome = scan.next() + scan.nextLine();
					System.out.print("Novo CPF do condutor: ");
					alterar.setCondutor(new Condutor(novoNome, scan.nextLong()));
					break;
				case 4: 
					System.out.print("Novo modelo: ");
					alterar.setModelo(scan.next());
					break;
				case 5: 
					System.out.print("Nova data de fabricação: ");
					alterar.setFabricacao(scan.next());
					break;
				case 6:
				default: 
					break;
				}

				try {
					protocolo.alterar(renavamAlterar, alterar);
				} catch (SameKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WrongInsertException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 5:
				System.out.println("-----------------------");
				System.out.println("Remoção de Veículo");
				System.out.println("-----------------------");
				System.out.print("Renavam pra excluir: ");
				int renavamRemover = scan.nextInt();

				try {
					protocolo.remover(renavamRemover);
				} catch (WrongInsertException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 6:
				System.out.println("-----------------------");
				protocolo.quantidadeVeiculos();
				System.out.println("-----------------------");
				
				break;
			case 7:
			default:
				break;
			}

		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
