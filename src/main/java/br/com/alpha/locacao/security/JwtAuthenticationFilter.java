package br.com.alpha.locacao.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.ColaboradorPerfil;
import br.com.alpha.locacao.dto.LoginDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginDTO login = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getUsername(),
					login.getPassword(), new ArrayList<>());
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		} catch (AuthenticationException e) {
			throw new RuntimeException("Falha ao autenticar usuário: Credenciais inválidas", e);
		} catch (IOException e) {
			throw new RuntimeException("Falha ao autenticar usuario", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((Colaborador) authResult.getPrincipal()).getUsername();
		String colaboradorNome = ((Colaborador) authResult.getPrincipal()).getNome();

		Set<ColaboradorPerfil> colaboradorRole = ((Colaborador) authResult.getPrincipal()).getColaboradorPerfis();
		String role = saveRole(colaboradorRole);

		String token = jwtUtil.generateToken(username, colaboradorNome, role);
		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
	}

	private String saveRole(Set<ColaboradorPerfil> colaboradorRole) {

		String role = null;

		for (ColaboradorPerfil colaboradorPerfil : colaboradorRole) {
			if (role == null) {
				role = colaboradorPerfil.getId().getPerfil().getNome();

			} else {
				role = role + "." + colaboradorPerfil.getId().getPerfil().getNome();
			}
		}
		return role;
	}
}