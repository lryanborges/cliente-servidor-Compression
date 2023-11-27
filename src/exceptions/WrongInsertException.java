package exceptions;

@SuppressWarnings("serial")
public class WrongInsertException extends Exception {
	
	@Override
	public String getMessage() {
        return "Um das variáveis não permite o tipo inserido.";
    }
	
}
