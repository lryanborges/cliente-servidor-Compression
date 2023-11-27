package exceptions;

@SuppressWarnings("serial")
public class SameKeyException extends Exception {

	long pkey;
	
	public SameKeyException(long pkey) {
		this.pkey = pkey;
	}
	
	@Override
    public String getMessage() {
        return "O renavam " + pkey + " já está sendo usado.";
    }
	
}
