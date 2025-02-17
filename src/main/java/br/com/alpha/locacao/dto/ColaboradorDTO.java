package br.com.alpha.locacao.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.Telefone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ColaboradorDTO(

		Long id,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") @NotNull(message = "Data de nascimento não pode estar vazia.") Date dataNascimento,
		@NotBlank(message = "Nome não pode estar vazio") @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres.") String nome,
		@CPF(message = "CPF inválido.") @NotBlank(message = "CPF não pode estar vazio.") String cpf,
		@Email(message = "E-mail inválido") @NotBlank(message = "E-mail não pode estar vazio.") String email,
		List<String> perfis, String cargo,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") @NotNull(message = "Data de contratação não pode estar vazia.") Date dataContratacao,
		@NotNull(message = "Dependente não pode estar vazio.") Integer dependente,
		@NotNull(message = "Salário não pode estar vazio.") Double salario, EnderecoDTO endereco,
		@NotNull(message = "Os telefones não podem estar vazios") List<Telefone> telefones,
		@NotNull(message = "Os dados bancários não podem estar vazios") List<DadosBancarios> dadosBancarios

) {

	public Colaborador toEntity() {
		Colaborador colaborador = new Colaborador();
		colaborador.setId(this.id);
		colaborador.setDataNascimento(this.dataNascimento);
		colaborador.setNome(this.nome);
		colaborador.setCpf(this.cpf);
		colaborador.setEmail(this.email);
		colaborador.setCargo(this.cargo);
		colaborador.setDataContratacao(this.dataContratacao);
		colaborador.setDependente(this.dependente);
		colaborador.setSalario(this.salario);
		colaborador.setEndereco(this.endereco.toEntity());
		colaborador.setTelefones(telefones);
		colaborador.setDadosBancarios(dadosBancarios);


		return colaborador;
	}

	public static ColaboradorDTO toDto(Colaborador colaborador) {
		List<String> perfis = colaborador.getColaboradorPerfis().stream().map(cp -> cp.getId().getPerfil().getNome())
				.collect(Collectors.toList());

		return new ColaboradorDTO(
				colaborador.getId(),
				colaborador.getDataNascimento(),
				colaborador.getNome(),
				colaborador.getCpf(),
				colaborador.getEmail(),
				perfis,
				colaborador.getCargo(),
				colaborador.getDataContratacao(),
				colaborador.getDependente(),
				colaborador.getSalario(),
				EnderecoDTO.toDto(colaborador.getEndereco()),
				colaborador.getTelefones(),
				colaborador.getDadosBancarios()
				
				);

	}
}
