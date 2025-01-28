package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.constants.TipoConta;
import br.com.alpha.locacao.domain.DadosBancarios;

public record DadosBancariosPessoaDTO(

        Long id,
        String instituicaoFinanceira,
        String codigoInstituicao,
        TipoConta tipo,
        String outros,
        String pix,
        String agencia,
        String conta,
        String digitoConta

) {
    public DadosBancarios toEntity() {
        DadosBancarios dadosBancarios = new DadosBancarios();
        dadosBancarios.setAgencia(agencia);
        dadosBancarios.setCodigoInstituicao(codigoInstituicao);
        dadosBancarios.setConta(conta);
        dadosBancarios.setInstituicaoFinanceira(instituicaoFinanceira);
        dadosBancarios.setOutros(outros);
        dadosBancarios.setPix(pix);
        dadosBancarios.setTipo(tipo);
        dadosBancarios.setDigitoConta(digitoConta);

        return dadosBancarios;
    }

    public static DadosBancariosPessoaDTO toDto(DadosBancarios dadosBancarios) {
        return new DadosBancariosPessoaDTO(
                dadosBancarios.getId(),
                dadosBancarios.getInstituicaoFinanceira(),
                dadosBancarios.getCodigoInstituicao(),
                dadosBancarios.getTipo(),
                dadosBancarios.getOutros(),
                dadosBancarios.getPix(),
                dadosBancarios.getAgencia(),
                dadosBancarios.getConta(),
                dadosBancarios.getDigitoConta()
        );
    }
}
