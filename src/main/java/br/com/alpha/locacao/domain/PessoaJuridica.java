package br.com.alpha.locacao.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome_fantasia", columnDefinition = "TEXT")
	@NotBlank(message = "Nome fantasia nao pode estar vazio")
	private String nomeFantasia;

	@Column(name = "razao_social", columnDefinition = "TEXT")
	@NotBlank(message = "Razao social nao pode estar vazio")
	private String razaoSocial;

	@Column(name = "cnpj")
	@NotBlank(message = "CNPJ nao pode estar vazio")
	@CNPJ(message = "CNPJ invalido")
	private String cnpj;

	@Column(name = "inscricao_estadual")
	@NotBlank(message = "Inscricao estadual nao pode estar vazio")
	@Size(max = 14, message = "O nome deve ter max. 14 caracteres.")
	private String inscricaoEstadual;

	@Column(name = "inscricao_municipal")
	@NotBlank(message = "Inscricao municipal nao pode estar vazio")
	@Size(max = 30, message = "O nome deve ter max. 30 caracteres.")
	private String inscricaoMunicipal;

	@Email(message = "Email invalido")
	@NotBlank(message = "Email nao pode estar vazio")
	@Column(name = "email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	private Endereco endereco;

	@JsonManagedReference
	@OneToMany(mappedBy = "pessoaJuridica", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Telefone> telefones;

	@JsonManagedReference
	@OneToMany(mappedBy = "pessoaJuridica", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<DadosBancarios> dadosBancarios;

	@JsonManagedReference
	@OneToMany(mappedBy = "id.pessoaJuridica", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PessoaJuridicaPessoaFisica> pessoaJuridicaPessoaFisicas = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<DadosBancarios> getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(List<DadosBancarios> dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public Set<PessoaJuridicaPessoaFisica> getPessoaJuridicaPessoaFisicas() {
		return pessoaJuridicaPessoaFisicas;
	}

	public void setPessoaJuridicaPessoaFisicas(Set<PessoaJuridicaPessoaFisica> pessoaJuridicaPessoaFisicas) {
		this.pessoaJuridicaPessoaFisicas = pessoaJuridicaPessoaFisicas;
	}

}
