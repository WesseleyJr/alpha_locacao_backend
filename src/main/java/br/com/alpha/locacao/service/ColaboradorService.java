package br.com.alpha.locacao.service;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.ColaboradorPerfil;
import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.Perfil;
import br.com.alpha.locacao.dto.ColaboradorDTO;
import br.com.alpha.locacao.dto.ColaboradorInserirDTO;
import br.com.alpha.locacao.exception.EmailException;
import br.com.alpha.locacao.exception.SenhaException;
import br.com.alpha.locacao.repository.ColaboradorRepository;
import br.com.alpha.locacao.repository.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public List<ColaboradorDTO> listarTodos() {
		return colaboradorRepository.findAll().stream().map(ColaboradorDTO::toDto).toList();
	}

	public ColaboradorDTO buscarId(Long id) {
		return colaboradorRepository.findById(id).map(ColaboradorDTO::toDto)
				.orElseThrow(() -> new RuntimeException("Colaborador com ID " + id + " não encontrado."));
	}

	@Transactional
	public ColaboradorDTO inserir(ColaboradorInserirDTO colaboradorInserirDto) throws EmailException, SenhaException {

		Endereco enderecoAssociado = null;

		enderecoAssociado = verificacao(colaboradorInserirDto);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setNome(Normalizer.normalize(colaboradorInserirDto.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
		colaborador.setCpf(colaboradorInserirDto.getCpf());
		colaborador.setDataNascimento(colaboradorInserirDto.getDataNascimento());
		colaborador.setEmail(colaboradorInserirDto.getEmail());
		colaborador.setTelefone(colaboradorInserirDto.getTelefone());
		colaborador.setEndereco(enderecoAssociado);

		colaborador.setSenha(encoder.encode(colaboradorInserirDto.getSenha()));

		Set<ColaboradorPerfil> perfis = new HashSet<>();
		for (Perfil perfil : colaboradorInserirDto.getPerfis()) {
			perfil = perfilService.buscar(perfil.getId());
			ColaboradorPerfil colaboradorPerfil = new ColaboradorPerfil(colaborador, perfil);
			perfis.add(colaboradorPerfil);
		}
		colaborador.setColaboradorPerfis(perfis);

		Colaborador colaboradorSalvo = colaboradorRepository.save(colaborador);

		return ColaboradorDTO.toDto(colaboradorSalvo);
	}

	@Transactional
	public ColaboradorDTO atualizar(ColaboradorInserirDTO colaboradorInserirDTO, Long id) {
		Optional<Colaborador> colaboradorOPT = colaboradorRepository.findById(id);

		if (colaboradorOPT.isEmpty()) {
			throw new RuntimeException("Colaborador não encontrado");
		}

		Endereco enderecoAssociado = null;

		enderecoAssociado = verificacao(colaboradorInserirDTO);

		Colaborador colaborador = colaboradorOPT.get();
		colaborador.setNome(colaboradorInserirDTO.getNome());
		colaborador.setCpf(colaboradorInserirDTO.getCpf());
		colaborador.setDataNascimento(colaboradorInserirDTO.getDataNascimento());
		colaborador.setEmail(colaboradorInserirDTO.getEmail());
		colaborador.setTelefone(colaboradorInserirDTO.getTelefone());
		colaborador.setEndereco(enderecoAssociado);

		colaborador.setSenha(encoder.encode(colaboradorInserirDTO.getSenha()));

		Set<ColaboradorPerfil> perfis = new HashSet<>();
		for (Perfil perfil : colaboradorInserirDTO.getPerfis()) {
			perfil = perfilService.buscar(perfil.getId());
			ColaboradorPerfil colaboradorPerfil = new ColaboradorPerfil(colaborador, perfil);
			perfis.add(colaboradorPerfil);
		}
		colaborador.setColaboradorPerfis(perfis);

		colaborador = colaboradorRepository.save(colaborador);

		return ColaboradorDTO.toDto(colaborador);

	}

	@Transactional
	public void deletar(Long id) {
		if (!colaboradorRepository.existsById(id)) {
			throw new RuntimeException("Colaborador não encontrado");
		}
		colaboradorRepository.deleteById(id);
	}

	private Endereco verificacao(ColaboradorInserirDTO colaboradorInserirDto) throws EmailException, SenhaException {

		if (!colaboradorInserirDto.getSenha().equals(colaboradorInserirDto.getConfirmaSenha())) {
			throw new SenhaException("Senha e Confirma Senha não são iguais");
		}

		if (colaboradorRepository.findByEmail(colaboradorInserirDto.getEmail()) != null) {
			throw new EmailException("Email já cadastrado");
		}

		if (colaboradorRepository.findByCpf(colaboradorInserirDto.getCpf()) != null) {
			throw new RuntimeException("Cpf já cadastrado");
		}

		List<Endereco> byLogradouro = enderecoRepository
				.findByLogradouro(colaboradorInserirDto.getEndereco().getLogradouro());

		Endereco enderecoAssociado = null;

		for (Endereco endereco : byLogradouro) {
			if (endereco.getNumero().equals(colaboradorInserirDto.getEndereco().getNumero())
					&& endereco.getComplemento().equals(colaboradorInserirDto.getEndereco().getComplemento())
					&& endereco.getBairro().equals(colaboradorInserirDto.getEndereco().getBairro())
					&& endereco.getCidade().equals(colaboradorInserirDto.getEndereco().getCidade())) {
				enderecoAssociado = endereco;
				return enderecoAssociado;
			}
		}

		enderecoAssociado = enderecoService.inserir(colaboradorInserirDto.getEndereco());
		return enderecoAssociado;
	}

}
