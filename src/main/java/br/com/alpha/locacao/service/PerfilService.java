package br.com.alpha.locacao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Perfil;
import br.com.alpha.locacao.repository.PerfilRepository;


@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	public Perfil buscar(Long id) {
		Optional<Perfil> perfilOpt = perfilRepository.findById(id);
		return perfilOpt.get();
	}
}
