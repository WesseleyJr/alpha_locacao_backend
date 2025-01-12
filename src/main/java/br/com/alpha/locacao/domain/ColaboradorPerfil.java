package br.com.alpha.locacao.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "colaborador_perfil")
public class ColaboradorPerfil {

	
	@EmbeddedId
	private ColaboradorPerfilPK id = new ColaboradorPerfilPK();
	
	public ColaboradorPerfil() {
	}

	public ColaboradorPerfil(Colaborador colaborador, Perfil perfil) {
		this.id.setColaborador(colaborador);
		this.id.setPerfil(perfil);
	}

	
	public ColaboradorPerfilPK getId() {
		return id;
	}

	public void setId(ColaboradorPerfilPK id) {
		this.id = id;
	}

	
}