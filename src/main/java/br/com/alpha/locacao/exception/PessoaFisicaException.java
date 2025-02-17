package br.com.alpha.locacao.exception;

public class PessoaFisicaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PessoaFisicaException(String message) {
		super(message);
	}
}