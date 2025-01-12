package br.com.alpha.locacao.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "Preencha o campo logradouro")
	@Column(name = "logradouro", columnDefinition = "TEXT", nullable = false)
	private String logradouro;

	@NotNull(message = "Preencha o campo numero")
	@Column(name = "numero")
	private Integer numero;

	@Size(max = 100, message = " Complemento maximo 100 caracteres")
	@Column(name = "complemento", length = 2)
	private String complemento;

	@Size(max = 255, message = "Bairro maximo 255 caracteres")
	@NotBlank(message = "Preencha o campo bairro")
	@Column(name = "bairro", nullable = false)
	private String bairro;

	@Size(max = 255, message = "Cidade maximo 255 caracteres")
	@NotBlank(message = "Preencha o campo cidade")
	@Column(name = "cidade", nullable = false)
	private String cidade;

	@Size(min = 2, max = 2, message = "Uf 2 caracteres")
	@NotBlank(message = "Preencha o campo uf")
	@Column(name = "uf", length = 2, nullable = false)
	private String uf;

	@Size(max = 20, message = "Codigo postal maximo 20 caracteres")
	@NotBlank(message = "Preencha o campo Codigo Postal")
	@Column(name = "codigo_postal")
	private String codigoPostal;

	@Size(max = 100, message = "Pais maximo 100 caracteres")
	@NotBlank(message = "Preencha o campo Pais")
	@Column(name = "pais", length = 100, nullable = false)
	private String pais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
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
		Endereco other = (Endereco) obj;
		return Objects.equals(id, other.id);
	}

}
