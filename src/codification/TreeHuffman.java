package codification;

import java.util.HashMap;
import java.util.PriorityQueue;

public class TreeHuffman {

	NoHuffman raiz;
	HashMap<Character, String>[] codigos = new HashMap[128];
;
	int cont = 0;

	public void construirArvore(int n, char[] arrayCaracteres, int[] arrayFrequencias) {
		PriorityQueue<NoHuffman> heapMinimo = new PriorityQueue<>();
		NoHuffman no;
		for(int i = 0; i < n; i++) {
			codigos[i] = new HashMap<>();
		}
		
		for (int i = 0; i < n; i++) {
			no = new NoHuffman();
			no.setCaractere(arrayCaracteres[i]);
			no.setFrequencia(arrayFrequencias[i]);
			no.setPrev(null);
			no.setNext(null);
			heapMinimo.offer(no);
		}

		raiz = null;

		while (heapMinimo.size() > 1) {
			NoHuffman x = heapMinimo.poll();
			NoHuffman y = heapMinimo.poll();
			NoHuffman z = new NoHuffman();
			z.setFrequencia(x.getFrequencia() + y.getFrequencia());
			z.setCaractere('-');
			z.setPrev(x);
			z.setNext(y);
			raiz = z;
			heapMinimo.offer(z);
		}
	}

	public void imprimirCodigo(NoHuffman no, String s) {
		if (no.getPrev() == null && no.getNext() == null && raiz.getCaractere() == '-') {
			//System.out.println(no.getCaractere() + ":" + s);
			codigos[cont++].put(no.getCaractere(), s);
		}

		if (no.getPrev() != null && no.getNext() != null) {
			imprimirCodigo(no.getPrev(), s + "0");
			imprimirCodigo(no.getNext(), s + "1");
		}
	}
	
	public NoHuffman getRaiz() {
		return raiz;
	}
	public void setRaiz(NoHuffman raiz) {
		this.raiz = raiz;
	}
	public int getCont() {
		return cont;
	}
	public void setCont(int cont) {
		this.cont = cont;
	}
	public HashMap<Character, String>[] getCodigos() {
		return codigos;
	}
	public void setCodgios(HashMap<Character, String>[] codigos) {
		this.codigos = codigos;
	}
	
}
