package br.com.alpha.locacao.domain;

import br.com.alpha.locacao.constants.TipoConta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "dados_bancarios")
public class DadosBancarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "instituicao_financeira")
	@NotBlank(message = "Instituição financeira nao pode estar vazio")
	@Size(max = 255, message = "Instituição financeira deve ter max. 255 caracteres.")
	private String instituicaoFinanceira;
	
	@Column(name = "codigo_instituicao")
	@NotBlank(message = "Codigo instituição nao pode estar vazio")
	@Size(min = 3, max = 3, message = "Codigo instituição deve ter 3 caracteres.")
	private String codigoInstituicao;
	
	@NotNull(message = "O tipo conta não pode estar vazio")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta")
	private TipoConta tipo;
	
	@Column(name = "outros")
	@Size(max = 100, message = "Outros deve ter max. 100 caracteres.")
	private String outros;
	
	@Column(name = "pix")
	@Size(max = 100, message = "Pix deve ter max. 100 caracteres.")
	private String pix;
	
	@Column(name = "agencia")
	@NotBlank(message = "Agencia nao pode estar vazio")
	@Size(max = 20, message = "Agencia deve ter max. 20 caracteres.")
	private String agencia;
	
	@Column(name = "conta")
	@NotBlank(message = "Conta nao pode estar vazio")
	@Size(max = 20, message = "Conta deve ter max. 20 caracteres.")
	private String conta;
	
	@Column(name = "digito_conta")
	@NotBlank(message = "Digito conta nao pode estar vazio")
	@Size(max = 5, message = "Digito conta deve ter max. 5 caracteres.")
	private String digitoConta;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id")
	private PessoaFisica pessoaFisica;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa_juridica", referencedColumnName = "id")
	private PessoaJuridica pessoaJuridica;
	
	@ManyToOne
	@JoinColumn(name = "id_colaborador", referencedColumnName = "id")
	private Colaborador colaborador;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstituicaoFinanceira() {
		return instituicaoFinanceira;
	}

	public void setInstituicaoFinanceira(String instituicaoFinanceira) {
		this.instituicaoFinanceira = instituicaoFinanceira;
	}

	public String getCodigoInstituicao() {
		return codigoInstituicao;
	}

	public void setCodigoInstituicao(String codigoInstituicao) {
		this.codigoInstituicao = codigoInstituicao;
	}

	public TipoConta getTipo() {
		return tipo;
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public String getPix() {
		return pix;
	}

	public void setPix(String pix) {
		this.pix = pix;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
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

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}
	
	

}
