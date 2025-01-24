package br.com.alpha.locacao.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.alpha.locacao.security.JwtAuthenticationFilter;
import br.com.alpha.locacao.security.JwtAuthorizationFilter;
import br.com.alpha.locacao.security.JwtUtil;


@Configuration
@EnableWebSecurity
public class ConfigSecurity {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.cors((cors) -> cors.configurationSource(corsConfigurationSource()))
		.authorizeHttpRequests(authorize -> 
	        authorize
	        	.requestMatchers(HttpMethod.POST, "/login").permitAll()
	            .requestMatchers(HttpMethod.GET, "/colaborador/**").hasAnyAuthority("ROLE_ADMIN")
	            .requestMatchers(HttpMethod.POST, "/colaborador/**").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/colaborador/**").hasAnyAuthority("ROLE_ADMIN")
	            .requestMatchers(HttpMethod.DELETE, "/colaborador/**").hasAnyAuthority("ROLE_ADMIN")
	            .requestMatchers(HttpMethod.GET, "/endereco/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_ADMINISTRACAO")
	            .requestMatchers(HttpMethod.POST, "/endereco/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_ADMINISTRACAO")
	            .requestMatchers(HttpMethod.PUT, "/endereco/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_ADMINISTRACAO")
	            .requestMatchers(HttpMethod.DELETE, "/endereco/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_ADMINISTRACAO")
	            .requestMatchers(HttpMethod.GET, "/telefone/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/telefone/**").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/telefone/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/telefone/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/dadosbancarios/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/dadosbancarios/**").permitAll()
	            .requestMatchers(HttpMethod.PUT, "/dadosbancarios/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/dadosbancarios/**").permitAll()
	    )
	    .httpBasic(Customizer.withDefaults())
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		JwtAuthenticationFilter jwtAuthenticationFilter = 
				new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))
						, jwtUtil);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		JwtAuthorizationFilter jwtAuthorizationFilter = 
				new JwtAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
						jwtUtil, userDetailsService);
		
		http.addFilter(jwtAuthenticationFilter);
		http.addFilter(jwtAuthorizationFilter);
		
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}