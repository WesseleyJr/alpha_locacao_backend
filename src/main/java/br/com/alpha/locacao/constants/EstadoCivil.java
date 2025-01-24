package br.com.alpha.locacao.constants;

public enum EstadoCivil {
	CASADO("Casado"),
	SOLTEIRO("Solteiro"),
	UNIAO_ESTAVEL("União estavel"),
	DIVORCIADO("Divorciado"),
	VIUVO("Viúvo");
	
	private String descricao;
	
	EstadoCivil(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
		
}
