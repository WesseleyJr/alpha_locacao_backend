package br.com.alpha.locacao.domain;

import br.com.alpha.locacao.constants.TipoTelefone;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "telefone")
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "numero")
	@NotBlank(message = "Numero não pode estar vazio")
	@Size(max = 30, message = "Numero deve ter max. 30 caracteres.")
	private String numero;
	
	@NotNull(message = "O tipo telefone nao pode estar vazio")
	@Enumerated(EnumType.STRING)
	private TipoTelefone tipo;
	
	@Column(name = "ddd")
	@NotBlank(message = "DDD não pode estar vazio")
	@Size(max = 5, message = "DDD deve ter max. 5 caracteres.")
	private String ddd;
	
	@Column(name = "codigo_pais")
	@NotBlank(message = "Código pais não pode estar vazio")
	@Size(max = 5, message = "Código pais deve ter max. 5 caracteres.")
	private String codigoPais;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id")
	private PessoaFisica pessoaFisica;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa_juridica", referencedColumnName = "id")
	private PessoaJuridica pessoaJuridica;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_colaborador", referencedColumnName = "id")
	private Colaborador colaborador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
