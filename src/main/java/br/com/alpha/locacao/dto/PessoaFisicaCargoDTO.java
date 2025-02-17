package br.com.alpha.locacao.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaFisicaCargoDTO(
    @NotNull(message = "ID da pessoa física não pode ser nulo")
    Long id,

    @NotBlank(message = "Cargo não pode ser nulo ou vazio")
    String cargo
) {}
