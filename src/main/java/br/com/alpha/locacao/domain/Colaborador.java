package br.com.alpha.locacao.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "colaborador")
public class Colaborador implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	@NotBlank(message = "Nome nao pode estar vazio")
	@Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres.")
	private String nome;

	@Column(name = "senha")
	@NotBlank(message = "Senha nao pode estar vazio")
	private String senha;

	@Column(name = "cpf")
	@NotBlank(message = "Cpf nao pode estar vazio")
	@CPF(message = "CPF inválido.")
	private String cpf;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@Column(nullable = false, name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Email(message = "Email invalido")
	@NotBlank(message = "Email nao pode estar vazio")
	@Column(name = "email")
	private String email;

	@NotBlank(message = "Complemento nao pode estar vazio")
	private String telefone;

	@ManyToOne
	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	private Endereco endereco;

	@JsonManagedReference
	@OneToMany(mappedBy = "id.colaborador", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ColaboradorPerfil> colaboradorPerfis = new HashSet<>();

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Set<ColaboradorPerfil> getColaboradorPerfis() {
		return colaboradorPerfis;
	}

	public void setColaboradorPerfis(Set<ColaboradorPerfil> colaboradorPerfis) {
		this.colaboradorPerfis = colaboradorPerfis;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaborador other = (Colaborador) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (ColaboradorPerfil colaboradorPerfil : getColaboradorPerfis()) {
			authorities.add(new SimpleGrantedAuthority(colaboradorPerfil.getId().getPerfil().getNome()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
