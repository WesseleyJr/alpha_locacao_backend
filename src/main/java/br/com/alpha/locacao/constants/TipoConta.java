package br.com.alpha.locacao.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.alpha.locacao.exception.ConstantsException;

public enum TipoConta {

    CONTACORRENTE("conta-corrente"),
    CONTAPOUPANCA("conta-poupança");

    private final String descricao;

    private TipoConta(String descricao) {
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
    public static TipoConta fromJson(String value) {
        for (TipoConta tipo : TipoConta.values()) {
            if (tipo.descricao.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new ConstantsException("Tipo de conta inválido: " + value);
    }
}
