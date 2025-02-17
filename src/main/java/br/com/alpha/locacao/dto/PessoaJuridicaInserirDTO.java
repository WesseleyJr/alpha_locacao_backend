package br.com.alpha.locacao.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.domain.PessoaJuridicaPessoaFisica;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaJuridicaInserirDTO(

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

    Set<PessoaFisicaCargoDTO> pessoasFisicas
) {

    public PessoaJuridica toEntity() {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNomeFantasia(nomeFantasia);
        pessoaJuridica.setRazaoSocial(razaoSocial);
        pessoaJuridica.setCnpj(cnpj);
        pessoaJuridica.setInscricaoEstadual(inscricaoEstadual);
        pessoaJuridica.setInscricaoMunicipal(inscricaoMunicipal);
        pessoaJuridica.setEmail(email);
        pessoaJuridica.setEndereco(endereco);

        return pessoaJuridica;
    }

    public Set<PessoaJuridicaPessoaFisica> criarRelacoes(PessoaJuridica pessoaJuridica, Set<PessoaFisica> donos) {
        return donos.stream()
            .map(dono -> {
                PessoaFisicaCargoDTO cargoDTO = pessoasFisicas.stream()
                    .filter(p -> p.id().equals(dono.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cargo não encontrado para pessoa física ID " + dono.getId()));

                return new PessoaJuridicaPessoaFisica(pessoaJuridica, dono, cargoDTO.cargo());
            })
            .collect(Collectors.toSet());
    }

    public static PessoaJuridicaInserirDTO toDto(PessoaJuridica pessoaJuridica) {
        return new PessoaJuridicaInserirDTO(
            pessoaJuridica.getNomeFantasia(),
            pessoaJuridica.getRazaoSocial(),
            pessoaJuridica.getCnpj(),
            pessoaJuridica.getInscricaoEstadual(),
            pessoaJuridica.getInscricaoMunicipal(),
            pessoaJuridica.getEmail(),
            pessoaJuridica.getEndereco(),
            pessoaJuridica.getPessoaJuridicaPessoaFisicas().stream()
                .map(relacao -> new PessoaFisicaCargoDTO(
                    relacao.getId().getPessoaFisica().getId(),
                    relacao.getId().getCargo()
                ))
                .collect(Collectors.toSet())
        );
    }
}
