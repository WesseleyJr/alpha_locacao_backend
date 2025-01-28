package br.com.alpha.locacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{
	
	PessoaFisica findByEmail(String email);
	
	PessoaFisica findByCpf(String cpf);

}
