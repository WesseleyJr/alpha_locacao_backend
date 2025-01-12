package br.com.alpha.locacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.repository.ColaboradorRepository;


@Service
public class ColaboradorDetailsImpl implements UserDetailsService {

	@Autowired
    private  ColaboradorRepository colaboradorRepository;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Colaborador colaborador = colaboradorRepository.findByEmail(username);
		if (colaborador == null) {
			throw new RuntimeException();
		}
		return colaborador;
	}
}

