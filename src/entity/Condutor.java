package entity;

public class Condutor {

	private String nome;
	private long cpf;
	
	public Condutor(String nome, long cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public Condutor() {}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getCpf() {
		return cpf;
	}
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return nome + "#" + cpf;
	}
	
}
