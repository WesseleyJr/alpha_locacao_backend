package br.com.alpha.locacao.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.Perfil;
import br.com.alpha.locacao.domain.Telefone;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ColaboradorInserirDTO {
	@NotBlank(message = "Nome não pode estar vazio")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres.")
	private String nome;
	
	@NotBlank(message = "Senha não pode estar vazia")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
	private String senha;
	
	@NotBlank(message = "Confirma senha não pode estar vazia")
	private String confirmaSenha;
	
	@CPF(message = "CPF inválido.")
    @NotBlank(message = "CPF não pode estar vazio.")
	private String cpf;
	
	 @Email(message = "E-mail inválido")
     @NotBlank(message = "E-mail não pode estar vazio.")
	private String email;
	 
	 @NotBlank(message = "Telefone não pode estar vazio")
     @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 dígitos.")
	private String telefone;
	 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Data de nascimento não pode estar vazia.")
	private Date dataNascimento;
	
	@NotBlank(message = "Cargo nao pode estar vazio")
	@Size(min = 3, max = 255, message = "O cargo deve ter entre 3 e 255 caracteres.")
	private String cargo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Contratação nao pode estar vazio")
	private Date dataContratacao;
	
	@NotNull(message = "Dependente nao pode estar vazio")
	private Integer dependente;
	
	@NotNull(message = "Salario nao pode estar vazio")
	private Double salario;
	
	private EnderecoDTO endereco;
	
	private List<TelefonePessoaDTO> telefones;
	
	private List<DadosBancariosPessoaDTO> dadosBancarios;
	
	private Set<Perfil> perfis;
	
	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public Integer getDependente() {
		return dependente;
	}

	public void setDependente(Integer dependente) {
		this.dependente = dependente;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public List<TelefonePessoaDTO> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefonePessoaDTO> telefones) {
		this.telefones = telefones;
	}

	public List<DadosBancariosPessoaDTO> getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(List<DadosBancariosPessoaDTO> dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

}