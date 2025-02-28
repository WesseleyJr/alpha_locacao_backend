package br.com.alpha.locacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long>{

	PessoaJuridica findByEmail(String email);
	
	PessoaJuridica findByCnpj(String cnpj);
	
	PessoaJuridica findByRazaoSocial(String razaoSocial);
	
}
