package br.com.alpha.locacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alpha.locacao.domain.Perfil;


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}