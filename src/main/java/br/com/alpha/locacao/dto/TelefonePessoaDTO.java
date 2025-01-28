package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.constants.TipoTelefone;
import br.com.alpha.locacao.domain.Telefone;

public record TelefonePessoaDTO(

        Long id,
        String numero,
        TipoTelefone tipo,
        String ddd,
        String codigoPais

) {
    public Telefone toEntity() {
        Telefone telefone = new Telefone();
        telefone.setNumero(this.numero);
        telefone.setTipo(this.tipo);
        telefone.setDdd(this.ddd);
        telefone.setCodigoPais(this.codigoPais);

        return telefone;
    }

    public static TelefonePessoaDTO fromEntity(Telefone telefone) {
        return new TelefonePessoaDTO(
                telefone.getId(),
                telefone.getNumero(),
                telefone.getTipo(),
                telefone.getDdd(),
                telefone.getCodigoPais()
        );
    }
}
