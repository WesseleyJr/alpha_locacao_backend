package br.com.alpha.locacao.service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.ColaboradorPerfil;
import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.Perfil;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.Telefone;
import br.com.alpha.locacao.dto.ColaboradorDTO;
import br.com.alpha.locacao.dto.ColaboradorInserirDTO;
import br.com.alpha.locacao.dto.DadosBancariosDTO;
import br.com.alpha.locacao.dto.DadosBancariosInserirDTO;
import br.com.alpha.locacao.dto.PessoaFisicaDTO;
import br.com.alpha.locacao.dto.PessoaFisicaInserirDTO;
import br.com.alpha.locacao.dto.TelefoneDTO;
import br.com.alpha.locacao.dto.TelefoneInserirDTO;
import br.com.alpha.locacao.exception.CpfException;
import br.com.alpha.locacao.exception.EmailException;
import br.com.alpha.locacao.exception.SenhaException;
import br.com.alpha.locacao.exception.TelefoneException;
import br.com.alpha.locacao.repository.EnderecoRepository;
import br.com.alpha.locacao.repository.PessoaFisicaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PessoaFisicaService {
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private EnderecoService enderecoService;
	
	
	public List<PessoaFisicaDTO> listarTodos() {
		return pessoaFisicaRepository.findAll().stream().map(PessoaFisicaDTO::toDto).toList();
	}
	
	public PessoaFisicaDTO buscarId(Long id) {
		return pessoaFisicaRepository.findById(id).map(PessoaFisicaDTO::toDto)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa Fisica com ID " + id + " não encontrado."));
	}
	
	@Transactional
	public PessoaFisicaDTO inserir(PessoaFisicaInserirDTO pessoaFisicaInserirDTO) throws EmailException, CpfException {
		if (pessoaFisicaRepository.findByEmail(pessoaFisicaInserirDTO.email()) != null) {
			throw new EmailException("Email já existente");
		}
		if (pessoaFisicaRepository.findByCpf(pessoaFisicaInserirDTO.cpf()) != null) {
			throw new CpfException("Cpf já existente");
		}
		
		Endereco enderecoAssociado = null;

		enderecoAssociado = inserirEndereco(pessoaFisicaInserirDTO.endereco());
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setNome(pessoaFisicaInserirDTO.nome());
		pessoaFisica.setCpf(pessoaFisicaInserirDTO.cpf());
		pessoaFisica.setDataNascimento(pessoaFisicaInserirDTO.dataNascimento());
		pessoaFisica.setEmail(pessoaFisicaInserirDTO.email());
		pessoaFisica.setProfissao(pessoaFisicaInserirDTO.profissao());
		pessoaFisica.setNacionalidade(pessoaFisicaInserirDTO.nacionalidade());
		pessoaFisica.setRg(pessoaFisicaInserirDTO.rg());
		pessoaFisica.setCnh(pessoaFisicaInserirDTO.cnh());
		pessoaFisica.setOrgaoEmissor(pessoaFisicaInserirDTO.orgaoEmissor());
		pessoaFisica.setDataEmissao(pessoaFisicaInserirDTO.dataEmissao());
		pessoaFisica.setEstadoCivil(pessoaFisicaInserirDTO.estadoCivil());
		pessoaFisica.setSexo(pessoaFisicaInserirDTO.sexo());
		pessoaFisica.setEndereco(enderecoAssociado);
		
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
		
		return PessoaFisicaDTO.toDto(pessoaFisica);
		
	}

	
	private Endereco inserirEndereco(Endereco enderecoRecebido){

		List<Endereco> byLogradouro = enderecoRepository
				.findByLogradouro(enderecoRecebido.getLogradouro());

		Endereco enderecoAssociado = null;

		for (Endereco endereco : byLogradouro) {
			if (endereco.getNumero().equals(enderecoRecebido.getNumero())
					&& endereco.getComplemento().toUpperCase().equals(enderecoRecebido.getComplemento().toUpperCase())
					&& endereco.getBairro().toUpperCase().equals(enderecoRecebido.getBairro().toUpperCase())
					&& endereco.getCidade().toUpperCase().equals(enderecoRecebido.getCidade().toUpperCase())) {
				enderecoAssociado = endereco;
				return enderecoAssociado;
			}
		}

		enderecoAssociado = enderecoService.inserir(enderecoRecebido);
		return enderecoAssociado;
	}

}
