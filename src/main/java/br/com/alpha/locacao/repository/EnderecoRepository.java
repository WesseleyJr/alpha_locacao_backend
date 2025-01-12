package br.com.alpha.locacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	
	List<Endereco> findByLogradouro(String logradouro);

}
