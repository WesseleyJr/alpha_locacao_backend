package br.com.alpha.locacao.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoajuridica_pessoafisica")
public class PessoaJuridicaPessoaFisica {
	
	@EmbeddedId
	private PessoaJuridicaPessoaFisicaPK id = new PessoaJuridicaPessoaFisicaPK();
	
	public PessoaJuridicaPessoaFisica() {
	}

	public PessoaJuridicaPessoaFisica(PessoaJuridica pessoaJuridica, PessoaFisica pessoaFisica, String cargo) {
		this.id.setPessoaJuridica(pessoaJuridica);
		this.id.setPessoaFisica(pessoaFisica);
		this.id.setCargo(cargo);
	}

	public PessoaJuridicaPessoaFisicaPK getId() {
		return id;
	}

	public void setId(PessoaJuridicaPessoaFisicaPK id) {
		this.id = id;
	}

	


}
