package br.com.alpha.locacao.domain;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ColaboradorPerfilPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "id_colaborador")
	@JsonBackReference
	private Colaborador colaborador;

	@ManyToOne
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		return Objects.hash(colaborador, perfil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColaboradorPerfilPK other = (ColaboradorPerfilPK) obj;
		return Objects.equals(colaborador, other.colaborador) && Objects.equals(perfil, other.perfil);
	}

}