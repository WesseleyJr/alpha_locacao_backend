package br.com.alpha.locacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.ColaboradorPerfil;
import br.com.alpha.locacao.domain.ColaboradorPerfilPK;

@Repository
public interface ColaboradorPerfilRepository extends JpaRepository<ColaboradorPerfil, ColaboradorPerfilPK> {

}
