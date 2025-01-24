package br.com.alpha.locacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long>{
	
	@Query(value = """
	        SELECT * 
	        FROM telefone t 
	        WHERE t.numero = :numero AND t.ddd = :ddd
	        """, nativeQuery = true)
	Telefone findTelefoneByNumeroAndDdd(@Param("numero") String numero, @Param("ddd") String ddd);


}
