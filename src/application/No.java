package application;

// Nó para Árvore AVL
public class No<T> implements Comparable<Long> {

	private long pkey;
	private T valor;
	private No<T> next;
	private int frequencia = 0;
	
	public No(long pkey, T valor) {
		this.pkey = pkey;
		this.valor = valor;
		this.next = null;
	}
	
	public No() {}
	
	@Override
	public int compareTo(Long anotherKey) {
		// TODO Auto-generated method stub
		
		if(this.getPkey() < anotherKey) {
			return -1;
		} else if(this.getPkey() > anotherKey) {
			return 1;
		}
		
		return 0;
	}
	
	public long getPkey() {
		return pkey;
	}
	public void setPkey(long pkey) {
		this.pkey = pkey;
	}
	public T getValor() {
		return valor;
	}
	public void setValor(T valor) {
		this.valor = valor;
	}
	public No<T> getNext() {
		return next;
	}
	public void setNext(No<T> next) {
		this.next = next;
	}
	public int getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
	
}
