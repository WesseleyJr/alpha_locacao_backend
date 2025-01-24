package br.com.alpha.locacao.domain;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.alpha.locacao.constants.EstadoCivil;
import br.com.alpha.locacao.constants.Sexo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	@NotBlank(message = "Nome nao pode estar vazio")
	@Size(max = 255, message = "O nome deve ter max. 255 caracteres.")
	private String nome;

	@Column(name = "cpf")
	@NotBlank(message = "Cpf nao pode estar vazio")
	@CPF(message = "CPF inválido.")
	private String cpf;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Nascimento nao pode estar vazio")
	@Column(nullable = false, name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Email(message = "Email invalido")
	@NotBlank(message = "Email nao pode estar vazio")
	@Column(name = "email")
	private String email;

	@Column(name = "profissao")
	@NotBlank(message = "Profissão nao pode estar vazio")
	@Size(max = 255, message = "A profissão deve ter max. 255 caracteres.")
	private String profissao;
	
	@Column(name = "nacionalidade")
	@NotBlank(message = "Nacionalidade nao pode estar vazio")
	@Size(max = 255, message = "A nacionalidade deve ter max. 255 caracteres.")
	private String nacionalidade;
	
	@Column(name = "rg")
	@NotBlank(message = "Rg nao pode estar vazio")
	@Size(min = 10, max = 11, message = "O rg deve ter 10 ou 11 caracteres.")
	private String rg;
	
	@Column(name = "cnh")
	@Size(min = 9, max = 9, message = "A cnh deve ter 9 caracteres.")
	private String cnh;
	
	@Column(name = "orgao_emissor")
	@NotBlank(message = "Orgão emissor nao pode estar vazio")
	@Size(max = 255, message = "O orgão emissor deve ter max. 255 caracteres.")
	private String orgaoEmissor;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = "Data emissão nao pode estar vazio")
	@Column(nullable = false, name = "data_emissao")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;
	
	@NotNull(message = "O estado civil nao pode estar vazio")
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;
	
	@NotNull(message = "O sexo nao pode estar vazio")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@ManyToOne
	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	@NotNull(message = "O endereço nao pode estar vazio")
	private Endereco endereco;
	
    @OneToMany(mappedBy = "pessoaFisica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Telefone> telefones;
    
    @OneToMany(mappedBy = "pessoaFisica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DadosBancarios> dadosBancarios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
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
    
    
}
