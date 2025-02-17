package br.com.alpha.locacao.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.domain.Telefone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PessoaJuridicaDTO(

        Long id,

        @NotBlank(message = "Nome fantasia não pode estar vazio")
        String nomeFantasia,

        @NotBlank(message = "Razão social não pode estar vazia")
        String razaoSocial,

        @NotBlank(message = "CNPJ não pode estar vazio")
        @CNPJ(message = "CNPJ inválido")
        String cnpj,

        @NotBlank(message = "Inscrição estadual não pode estar vazia")
        @Size(max = 14, message = "A inscrição estadual deve ter no máximo 14 caracteres.")
        String inscricaoEstadual,

        @NotBlank(message = "Inscrição municipal não pode estar vazia")
        @Size(max = 30, message = "A inscrição municipal deve ter no máximo 30 caracteres.")
        String inscricaoMunicipal,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email não pode estar vazio")
        String email,

        Endereco endereco,

        @NotNull(message = "Os telefones não podem estar vazios")
        List<Telefone> telefones,

        @NotNull(message = "Os dados bancários não podem estar vazios")
        List<DadosBancarios> dadosBancarios,

        List<PessoaFisicaDTO> pessoasFisicas

) {

    public PessoaJuridica toEntity(PessoaJuridica pessoaJuridica) {
        pessoaJuridica.setId(id);
        pessoaJuridica.setNomeFantasia(nomeFantasia);
        pessoaJuridica.setRazaoSocial(razaoSocial);
        pessoaJuridica.setCnpj(cnpj);
        pessoaJuridica.setInscricaoEstadual(inscricaoEstadual);
        pessoaJuridica.setInscricaoMunicipal(inscricaoMunicipal);
        pessoaJuridica.setEmail(email);
        pessoaJuridica.setEndereco(endereco);
        pessoaJuridica.setTelefones(telefones);
        pessoaJuridica.setDadosBancarios(dadosBancarios);

        return pessoaJuridica;
    }

    public static PessoaJuridicaDTO toDto(PessoaJuridica pessoaJuridica) {
        return new PessoaJuridicaDTO(
                pessoaJuridica.getId(),
                pessoaJuridica.getNomeFantasia(),
                pessoaJuridica.getRazaoSocial(),
                pessoaJuridica.getCnpj(),
                pessoaJuridica.getInscricaoEstadual(),
                pessoaJuridica.getInscricaoMunicipal(),
                pessoaJuridica.getEmail(),
                pessoaJuridica.getEndereco(),
                pessoaJuridica.getTelefones(),
                pessoaJuridica.getDadosBancarios(),
                pessoaJuridica.getPessoaJuridicaPessoaFisicas().stream()
                        .map(relacao -> PessoaFisicaDTO.toDto(relacao.getId().getPessoaFisica()))
                        .collect(Collectors.toList())
        );
    }
}
