package br.com.alpha.locacao.constants;

public enum Sexo {
	
	MASCULINO("Masculino"),
	FEMININO("Feminino"),
	OUTROS("Outros");
	
	private String descricao;
	
	Sexo(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
