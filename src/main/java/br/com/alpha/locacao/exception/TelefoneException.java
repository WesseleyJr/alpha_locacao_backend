package br.com.alpha.locacao.exception;

public class TelefoneException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TelefoneException(String message) {
		super(message);
	}
}