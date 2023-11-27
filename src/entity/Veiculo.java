package entity;

public class Veiculo {

	private String placa;
	private long renavam;
	private Condutor condutor;
	private String modelo;
	private String fabricacao;
	
	public Veiculo(String placa, long renavam, Condutor condutor, String modelo, String fabricacao) {
		this.placa = placa;
		this.renavam = renavam;
		this.condutor = condutor;
		this.modelo = modelo;
		this.fabricacao = fabricacao;
	}
	
	public Veiculo() {}
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public long getRenavam() {
		return renavam;
	}
	public void setRenavam(long renavam) {
		this.renavam = renavam;
	}
	public Condutor getCondutor() {
		return condutor;
	}
	public void setCondutor(Condutor condutor) {
		this.condutor = condutor;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getFabricacao() {
		return fabricacao;
	}
	public void setFabricacao(String fabricacao) {
		this.fabricacao = fabricacao;
	}

	@Override
	public String toString() {
		return placa + "#" + renavam + "#" + condutor.toString() + "#" + modelo
				+ "#" + fabricacao;
	}
	
}
