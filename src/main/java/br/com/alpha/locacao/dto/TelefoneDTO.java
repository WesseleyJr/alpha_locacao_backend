package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.constants.TipoTelefone;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.domain.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TelefoneDTO(
        Long id,

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

        String nomeTitular,
        String documentoTitular,
        Long idTitular
) {
    public TelefoneDTO(Telefone telefone) {
        this(
            telefone.getId(),
            telefone.getNumero(),
            telefone.getTipo(),
            telefone.getDdd(),
            telefone.getCodigoPais(),
            telefone.getPessoaFisica() != null ? telefone.getPessoaFisica().getNome() : telefone.getPessoaJuridica() != null ? telefone.getPessoaJuridica().getRazaoSocial() : null,
            telefone.getPessoaFisica() != null ? telefone.getPessoaFisica().getCpf() : telefone.getPessoaJuridica() != null ? telefone.getPessoaJuridica().getCnpj() : null,
            telefone.getPessoaFisica() != null ? telefone.getPessoaFisica().getId() : telefone.getPessoaJuridica() != null ? telefone.getPessoaJuridica().getId() : null
        );
    }

    public Telefone toEntity(PessoaFisica pessoaFisica, PessoaJuridica pessoaJuridica) {
        Telefone telefone = new Telefone();
        telefone.setNumero(this.numero);
        telefone.setTipo(this.tipo);
        telefone.setDdd(this.ddd);
        telefone.setCodigoPais(this.codigoPais);

        if (pessoaFisica != null && pessoaFisica.getId().equals(this.idTitular)) {
            telefone.setPessoaFisica(pessoaFisica);
        } else if (pessoaJuridica != null && pessoaJuridica.getId().equals(this.idTitular)) {
            telefone.setPessoaJuridica(pessoaJuridica);
        }

        return telefone;
    }

    public static TelefoneDTO fromEntity(Telefone telefone) {
        return new TelefoneDTO(telefone);
    }
}
