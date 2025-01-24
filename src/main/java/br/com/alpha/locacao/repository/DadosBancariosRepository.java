package br.com.alpha.locacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.DadosBancarios;

@Repository
public interface DadosBancariosRepository extends JpaRepository<DadosBancarios, Long> {
	@Query(value = """
			SELECT * 
			FROM dados_bancarios db 
			WHERE db.conta = :conta AND db.agencia = :agencia AND db.codigo_instituicao = :codigoInstituicao
			""", nativeQuery = true)
	DadosBancarios findDadosBancariosByContaAndAgenciaAndCodigoInstituicao(@Param("conta") String conta,
			@Param("agencia") String agencia, @Param("codigoInstituicao") String codigoInstituicao);
}
