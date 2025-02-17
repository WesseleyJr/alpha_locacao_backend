package br.com.alpha.locacao.service;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.ColaboradorPerfil;
import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.Perfil;
import br.com.alpha.locacao.dto.ColaboradorDTO;
import br.com.alpha.locacao.dto.ColaboradorInserirDTO;
import br.com.alpha.locacao.exception.CpfException;
import br.com.alpha.locacao.exception.EmailException;
import br.com.alpha.locacao.exception.SenhaException;
import br.com.alpha.locacao.exception.TelefoneException;
import br.com.alpha.locacao.repository.ColaboradorRepository;
import br.com.alpha.locacao.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
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
				.orElseThrow(() -> new EntityNotFoundException("Colaborador com ID " + id + " não encontrado."));
	}

	@Transactional
	public ColaboradorDTO inserir(ColaboradorInserirDTO colaboradorInserirDTO) throws EmailException, SenhaException, CpfException {
		if (!colaboradorInserirDTO.getSenha().equals(colaboradorInserirDTO.getConfirmaSenha())) {
			throw new SenhaException("Senha e Confirma Senha não são iguais");
		}
		if (colaboradorRepository.findByEmail(colaboradorInserirDTO.getEmail()) != null) {
			throw new EmailException("Email já existente");
		}
		if (colaboradorRepository.findByCpf(colaboradorInserirDTO.getCpf()) != null) {
			throw new CpfException("Cpf já existente");
		}
				
		Endereco enderecoAssociado = verificacaoEndereco(colaboradorInserirDTO.getEndereco().toEntity());
		
		Colaborador colaborador = new Colaborador();
		colaborador.setNome(Normalizer.normalize(colaboradorInserirDTO.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
		colaborador.setCpf(colaboradorInserirDTO.getCpf());
		colaborador.setDataNascimento(colaboradorInserirDTO.getDataNascimento());
		colaborador.setEmail(colaboradorInserirDTO.getEmail());
		colaborador.setTelefone(colaboradorInserirDTO.getTelefone());
		colaborador.setEndereco(enderecoAssociado);
		colaborador.setCargo(colaboradorInserirDTO.getCargo());
		colaborador.setSalario(colaboradorInserirDTO.getSalario());
		colaborador.setDependente(colaboradorInserirDTO.getDependente());
		colaborador.setDataContratacao(colaboradorInserirDTO.getDataContratacao());
		

		colaborador.setSenha(encoder.encode(colaboradorInserirDTO.getSenha()));

		Set<ColaboradorPerfil> perfis = new HashSet<>();
		for (Perfil perfil : colaboradorInserirDTO.getPerfis()) {
			perfil = perfilService.buscar(perfil.getId());
			ColaboradorPerfil colaboradorPerfil = new ColaboradorPerfil(colaborador, perfil);
			perfis.add(colaboradorPerfil);
		}
		colaborador.setColaboradorPerfis(perfis);

		Colaborador colaboradorSalvo = colaboradorRepository.save(colaborador);

		return ColaboradorDTO.toDto(colaboradorSalvo);
	}

	
	@Transactional
	public void deletar(Long id) {
		if (!colaboradorRepository.existsById(id)) {
			throw new EntityNotFoundException("Colaborador não encontrado");
		}
		colaboradorRepository.deleteById(id);
	}

	
	private Endereco verificacaoEndereco(Endereco enderecoRecebido){

		List<Endereco> byLogradouro = enderecoRepository
				.findByLogradouro(enderecoRecebido.getLogradouro().toLowerCase());

		Endereco enderecoAssociado = null;

		for (Endereco endereco : byLogradouro) {
			if (endereco.getNumero().equals(endereco.getNumero())
					&& endereco.getComplemento().toUpperCase().equals(enderecoRecebido.getComplemento().toUpperCase())
					&& endereco.getBairro().toUpperCase().equals(enderecoRecebido.getBairro().toUpperCase())
					&& endereco.getCidade().toUpperCase().equals(enderecoRecebido.getCidade().toUpperCase())) {
				enderecoAssociado = endereco;
				return enderecoAssociado;
			}
		}

		enderecoAssociado = enderecoService.inserir(enderecoRecebido).toEntity();
		return enderecoAssociado;
	}
	

}
