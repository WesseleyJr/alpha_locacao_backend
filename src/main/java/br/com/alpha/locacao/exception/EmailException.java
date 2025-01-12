package br.com.alpha.locacao.exception;

public class EmailException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EmailException(String message) {
		super(message);
	}
}
