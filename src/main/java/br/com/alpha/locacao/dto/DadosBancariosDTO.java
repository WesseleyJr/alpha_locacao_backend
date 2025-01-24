package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.constants.TipoConta;
import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosBancariosDTO(
        Long id,

        @NotBlank(message = "Instituição financeira não pode estar vazia")
        @Size(max = 255, message = "Instituição financeira deve ter no máximo 255 caracteres.")
        String instituicaoFinanceira,

        @NotBlank(message = "Código da instituição não pode estar vazio")
        @Size(min = 3, max = 3, message = "Código da instituição deve ter exatamente 3 caracteres.")
        String codigoInstituicao,

        @NotNull(message = "O tipo de conta não pode estar vazio")
        TipoConta tipo,

        @Size(max = 100, message = "Outros deve ter no máximo 100 caracteres.")
        String outros,

        @Size(max = 100, message = "Pix deve ter no máximo 100 caracteres.")
        String pix,

        @NotBlank(message = "Agência não pode estar vazia")
        @Size(max = 20, message = "Agência deve ter no máximo 20 caracteres.")
        String agencia,

        @NotBlank(message = "Conta não pode estar vazia")
        @Size(max = 20, message = "Conta deve ter no máximo 20 caracteres.")
        String conta,
        
    	@NotBlank(message = "Digito conta nao pode estar vazio")
    	@Size(max = 5, message = "Digito conta deve ter max. 5 caracteres.")
    	String digitoConta,

        String nomeTitular,
        String documentoTitular,
        Long idTitular
) {
    public DadosBancariosDTO(DadosBancarios dadosBancarios) {
        this(
            dadosBancarios.getId(),
            dadosBancarios.getInstituicaoFinanceira(),
            dadosBancarios.getCodigoInstituicao(),
            dadosBancarios.getTipo(),
            dadosBancarios.getOutros(),
            dadosBancarios.getPix(),
            dadosBancarios.getAgencia(),
            dadosBancarios.getConta(),
            dadosBancarios.getDigitoConta(),
            dadosBancarios.getPessoaFisica() != null ? dadosBancarios.getPessoaFisica().getNome() : 
            dadosBancarios.getPessoaJuridica() != null ? dadosBancarios.getPessoaJuridica().getRazaoSocial() : 
            dadosBancarios.getColaborador() != null ? dadosBancarios.getColaborador().getNome() : null,
            dadosBancarios.getPessoaFisica() != null ? dadosBancarios.getPessoaFisica().getCpf() : 
            dadosBancarios.getPessoaJuridica() != null ? dadosBancarios.getPessoaJuridica().getCnpj() :
            dadosBancarios.getColaborador() != null ? dadosBancarios.getColaborador().getCpf() : null,
            dadosBancarios.getPessoaFisica() != null ? dadosBancarios.getPessoaFisica().getId() : 
            dadosBancarios.getPessoaJuridica() != null ? dadosBancarios.getPessoaJuridica().getId() : 
            dadosBancarios.getColaborador() != null ? dadosBancarios.getColaborador().getId() : null
        );
    }

    public DadosBancarios toEntity(PessoaFisica pessoaFisica, PessoaJuridica pessoaJuridica, Colaborador colaborador) {
        DadosBancarios dadosBancarios = new DadosBancarios();
        dadosBancarios.setId(this.id);
        dadosBancarios.setInstituicaoFinanceira(this.instituicaoFinanceira);
        dadosBancarios.setCodigoInstituicao(this.codigoInstituicao);
        dadosBancarios.setTipo(this.tipo);
        dadosBancarios.setOutros(this.outros);
        dadosBancarios.setPix(this.pix);
        dadosBancarios.setAgencia(this.agencia);
        dadosBancarios.setConta(this.conta);
        dadosBancarios.setDigitoConta(this.digitoConta);

        if (pessoaFisica != null && pessoaFisica.getId().equals(this.idTitular)) {
            dadosBancarios.setPessoaFisica(pessoaFisica);
        } else if (pessoaJuridica != null && pessoaJuridica.getId().equals(this.idTitular)) {
            dadosBancarios.setPessoaJuridica(pessoaJuridica);
        } else if (colaborador != null && colaborador.getId().equals(this.idTitular)) {
            dadosBancarios.setColaborador(colaborador);
        }

        return dadosBancarios;
    }

    public static DadosBancariosDTO fromEntity(DadosBancarios dadosBancarios) {
        return new DadosBancariosDTO(dadosBancarios);
    }
}
