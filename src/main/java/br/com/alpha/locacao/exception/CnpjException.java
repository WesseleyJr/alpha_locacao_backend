package br.com.alpha.locacao.exception;

public class CnpjException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CnpjException(String message) {
		super(message);
	}
}