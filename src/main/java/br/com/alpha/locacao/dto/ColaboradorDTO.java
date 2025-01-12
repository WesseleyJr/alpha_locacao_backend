package br.com.alpha.locacao.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ColaboradorDTO(
        
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @NotNull(message = "Data de nascimento não pode estar vazia.")
        Date dataNascimento,
        @NotBlank(message = "Nome não pode estar vazio")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres.")
        String nome,
        @CPF(message = "CPF inválido.")
        @NotBlank(message = "CPF não pode estar vazio.")
        String cpf,
        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail não pode estar vazio.")
        String email,
        @NotBlank(message = "Telefone não pode estar vazio")
        @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 dígitos.")
        String telefone,
        @NotBlank(message = "CEP não pode estar vazio")
        @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos.")
        List<String> perfis,
        Endereco endereco
) {
    public Colaborador toEntity() {
        Colaborador colaborador = new Colaborador();
        colaborador.setId(this.id);
        colaborador.setDataNascimento(this.dataNascimento);
        colaborador.setNome(this.nome);
        colaborador.setCpf(this.cpf);
        colaborador.setEmail(this.email);
        colaborador.setTelefone(this.telefone);
        return colaborador;
    }

    public static ColaboradorDTO toDto(Colaborador colaborador) {
        List<String> perfis = colaborador.getColaboradorPerfis().stream()
                                         .map(cp -> cp.getId().getPerfil().getNome())
                                         .collect(Collectors.toList());
                                         
        return new ColaboradorDTO(
                colaborador.getId(),
                colaborador.getDataNascimento(),
                colaborador.getNome(),
                colaborador.getCpf(),
                colaborador.getEmail(),
                colaborador.getTelefone(),
                perfis,
                colaborador.getEndereco()
        );
    }
}
