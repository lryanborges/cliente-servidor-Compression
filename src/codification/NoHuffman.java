package codification;

public class NoHuffman implements Comparable<NoHuffman>{

	int frequencia;
	char caractere;
	NoHuffman next;
	NoHuffman prev;
	
	@Override
    public int compareTo(NoHuffman outroNo) {
        return this.frequencia - outroNo.frequencia;
    }
	
	public int getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
	public char getCaractere() {
		return caractere;
	}
	public void setCaractere(char caractere) {
		this.caractere = caractere;
	}
	public NoHuffman getNext() {
		return next;
	}
	public void setNext(NoHuffman next) {
		this.next = next;
	}
	public NoHuffman getPrev() {
		return prev;
	}
	public void setPrev(NoHuffman prev) {
		this.prev = prev;
	}
	
}
