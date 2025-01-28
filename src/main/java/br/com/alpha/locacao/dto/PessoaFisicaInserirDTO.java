package br.com.alpha.locacao.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.alpha.locacao.constants.EstadoCivil;
import br.com.alpha.locacao.constants.Sexo;
import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.Telefone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PessoaFisicaInserirDTO(
        @NotBlank(message = "Nome não pode estar vazio") 
        @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.") String nome,

        @NotBlank(message = "CPF não pode estar vazio") 
        @CPF(message = "CPF inválido.") String cpf,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
        @NotNull(message = "Data de nascimento não pode estar vazia") Date dataNascimento,

        @Email(message = "E-mail inválido") 
        @NotBlank(message = "E-mail não pode estar vazio") String email,

        @NotBlank(message = "Profissão não pode estar vazia") 
        @Size(max = 255, message = "A profissão deve ter no máximo 255 caracteres.") String profissao,

        @NotBlank(message = "Nacionalidade não pode estar vazia") 
        @Size(max = 255, message = "A nacionalidade deve ter no máximo 255 caracteres.") String nacionalidade,

        @NotBlank(message = "RG não pode estar vazio") 
        @Size(min = 10, max = 11, message = "O RG deve ter 10 ou 11 caracteres.") String rg,

        @NotBlank(message = "CNH não pode estar vazia") 
        @Size(min = 9, max = 9, message = "A CNH deve ter 9 caracteres.") String cnh,

        @NotBlank(message = "Órgão emissor não pode estar vazio") 
        @Size(max = 255, message = "O órgão emissor deve ter no máximo 255 caracteres.") String orgaoEmissor,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") 
        @NotNull(message = "Data de emissão não pode estar vazia") Date dataEmissao,

        @NotNull(message = "O estado civil não pode estar vazio") EstadoCivil estadoCivil,

        @NotNull(message = "O sexo não pode estar vazio") Sexo sexo,

        @NotNull(message = "O endereço não pode estar vazio") Endereco endereco

) {

    public PessoaFisica toEntity(PessoaFisica pessoaFisica) {
        pessoaFisica.setNome(nome);
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setDataNascimento(dataNascimento);
        pessoaFisica.setEmail(email);
        pessoaFisica.setProfissao(profissao);
        pessoaFisica.setNacionalidade(nacionalidade);
        pessoaFisica.setRg(rg);
        pessoaFisica.setCnh(cnh);
        pessoaFisica.setOrgaoEmissor(orgaoEmissor);
        pessoaFisica.setDataEmissao(dataEmissao);
        pessoaFisica.setEstadoCivil(estadoCivil);
        pessoaFisica.setSexo(sexo);
        pessoaFisica.setEndereco(endereco);
        return pessoaFisica;
    }

    public static PessoaFisicaInserirDTO toDto(PessoaFisica pessoaFisica) {
        return new PessoaFisicaInserirDTO(
                pessoaFisica.getNome(),
                pessoaFisica.getCpf(),
                pessoaFisica.getDataNascimento(),
                pessoaFisica.getEmail(),
                pessoaFisica.getProfissao(),
                pessoaFisica.getNacionalidade(),
                pessoaFisica.getRg(),
                pessoaFisica.getCnh(),
                pessoaFisica.getOrgaoEmissor(),
                pessoaFisica.getDataEmissao(),
                pessoaFisica.getEstadoCivil(),
                pessoaFisica.getSexo(),
                pessoaFisica.getEndereco()

        );
    }
}
