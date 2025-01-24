package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.constants.TipoConta;
import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosBancariosInserirDTO(

        @NotBlank(message = "Instituição financeira nao pode estar vazio")
        @Size(max = 255, message = "Instituição financeira deve ter max. 255 caracteres.")
        String instituicaoFinanceira,

        @NotBlank(message = "Codigo instituição nao pode estar vazio")
        @Size(min = 3, max = 3, message = "Codigo instituição deve ter 3 caracteres.")
        String codigoInstituicao,

        @NotNull(message = "O tipo conta não pode estar vazio")
        TipoConta tipo,

        @Size(max = 100, message = "Outros deve ter max. 100 caracteres.")
        String outros,

        @Size(max = 100, message = "Pix deve ter max. 100 caracteres.")
        String pix,

        @NotBlank(message = "Agencia nao pode estar vazio")
        @Size(max = 20, message = "Agencia deve ter max. 20 caracteres.")
        String agencia,

        @NotBlank(message = "Conta nao pode estar vazio")
        @Size(max = 20, message = "Conta deve ter max. 20 caracteres.")
        String conta,
        
    	@NotBlank(message = "Digito conta nao pode estar vazio")
    	@Size(max = 5, message = "Digito conta deve ter max. 5 caracteres.")
    	String digitoConta,

        Long idPessoaFisica,

        Long idPessoaJuridica,

        Long idColaborador

) {

    public DadosBancarios toEntity(PessoaFisica pessoaFisica, PessoaJuridica pessoaJuridica, Colaborador colaborador) {
        DadosBancarios dadosBancarios = new DadosBancarios();
        dadosBancarios.setAgencia(agencia);
        dadosBancarios.setCodigoInstituicao(codigoInstituicao);
        dadosBancarios.setConta(conta);
        dadosBancarios.setInstituicaoFinanceira(instituicaoFinanceira);
        dadosBancarios.setOutros(outros);
        dadosBancarios.setPix(pix);
        dadosBancarios.setTipo(tipo);
        dadosBancarios.setDigitoConta(digitoConta);

        if (idPessoaFisica != null && pessoaFisica != null) {
            dadosBancarios.setPessoaFisica(pessoaFisica);
        } else if (idPessoaJuridica != null && pessoaJuridica != null) {
            dadosBancarios.setPessoaJuridica(pessoaJuridica);
        } else if (idColaborador != null && colaborador != null) {
            dadosBancarios.setColaborador(colaborador);
        }

        return dadosBancarios;
    }

    public static DadosBancariosInserirDTO toDto(DadosBancarios dadosBancarios) {
        return new DadosBancariosInserirDTO(
                dadosBancarios.getInstituicaoFinanceira(),
                dadosBancarios.getCodigoInstituicao(),
                dadosBancarios.getTipo(),
                dadosBancarios.getOutros(),
                dadosBancarios.getPix(),
                dadosBancarios.getAgencia(),
                dadosBancarios.getConta(),
                dadosBancarios.getDigitoConta(),
                dadosBancarios.getPessoaFisica() != null ? dadosBancarios.getPessoaFisica().getId() : null,
                dadosBancarios.getPessoaJuridica() != null ? dadosBancarios.getPessoaJuridica().getId() : null,
                dadosBancarios.getColaborador() != null ? dadosBancarios.getColaborador().getId() : null
        );
    }
}
