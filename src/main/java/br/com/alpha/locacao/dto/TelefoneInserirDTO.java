package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.constants.TipoTelefone;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.domain.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TelefoneInserirDTO(
        
        @NotBlank(message = "Numero não pode estar vazio")
        @Size(max = 30, message = "Numero deve ter max. 30 caracteres.")
        String numero,

        @NotNull(message = "O tipo telefone nao pode estar vazio")
        TipoTelefone tipo,

        @NotBlank(message = "DDD não pode estar vazio")
        @Size(max = 5, message = "DDD deve ter max. 5 caracteres.")
        String ddd,

        @NotBlank(message = "Código pais não pode estar vazio")
        @Size(max = 5, message = "Código pais deve ter max. 5 caracteres.")
        String codigoPais,

        Long idPessoaFisica,

        Long idPessoaJuridica
) {

    public Telefone toEntity(PessoaFisica pessoaFisica, PessoaJuridica pessoaJuridica) {
        Telefone telefone = new Telefone();
        telefone.setNumero(this.numero);
        telefone.setTipo(this.tipo);
        telefone.setDdd(this.ddd);
        telefone.setCodigoPais(this.codigoPais);
        
        if (idPessoaFisica != null) {
            if (pessoaFisica != null) {
                telefone.setPessoaFisica(pessoaFisica);
            }
        } else if (idPessoaJuridica != null) {
            if (pessoaJuridica != null) {
                telefone.setPessoaJuridica(pessoaJuridica);
            }
        }

        return telefone;
    }

    public static TelefoneInserirDTO toDto(Telefone telefone) {
        return new TelefoneInserirDTO(
                telefone.getNumero(),
                telefone.getTipo(),
                telefone.getDdd(),
                telefone.getCodigoPais(),
                telefone.getPessoaFisica() != null ? telefone.getPessoaFisica().getId() : null,
                telefone.getPessoaJuridica() != null ? telefone.getPessoaJuridica().getId() : null
        );
    }
}
