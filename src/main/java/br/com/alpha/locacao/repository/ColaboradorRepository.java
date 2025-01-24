package br.com.alpha.locacao.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.Colaborador;


@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
	Colaborador findByEmail(String email);
	
	Colaborador findByCpf(String cpf);
	
	Colaborador findByTelefone(String telefone);
}

