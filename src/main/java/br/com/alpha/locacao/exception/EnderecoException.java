package br.com.alpha.locacao.exception;

public class EnderecoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EnderecoException(String message) {
		super(message);
	}
}