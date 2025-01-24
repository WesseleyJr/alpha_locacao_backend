package br.com.alpha.locacao.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import br.com.alpha.locacao.exception.ConstantsException;

public enum TipoTelefone {

	RESIDENCIAL("Residencial"),
	CELULAR("Celular");
	
	private String descricao;
	
	private TipoTelefone(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
    @JsonValue
    public String toJson() {
        return descricao;
    }

    @JsonCreator
    public static TipoTelefone fromJson(String value) {
        for (TipoTelefone tipo : TipoTelefone.values()) {
            if (tipo.descricao.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new ConstantsException("Tipo de telefone inválido: " + value);
    }
}
