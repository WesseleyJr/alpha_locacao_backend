package br.com.alpha.locacao.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.domain.PessoaJuridicaPessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridicaPessoaFisicaPK;
import br.com.alpha.locacao.dto.PessoaFisicaCargoDTO;
import br.com.alpha.locacao.dto.PessoaJuridicaDTO;
import br.com.alpha.locacao.dto.PessoaJuridicaInserirDTO;
import br.com.alpha.locacao.exception.CnpjException;
import br.com.alpha.locacao.exception.CpfException;
import br.com.alpha.locacao.exception.EmailException;
import br.com.alpha.locacao.exception.EnderecoException;
import br.com.alpha.locacao.exception.PessoaFisicaException;
import br.com.alpha.locacao.exception.RazaoSocialException;
import br.com.alpha.locacao.repository.EnderecoRepository;
import br.com.alpha.locacao.repository.PessoaFisicaRepository;
import br.com.alpha.locacao.repository.PessoaJuridicaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PessoaJuridicaService {

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	public List<PessoaJuridicaDTO> listarTodos() {
		return pessoaJuridicaRepository.findAll().stream().map(PessoaJuridicaDTO::toDto).toList();
	}

	public PessoaJuridicaDTO buscarId(Long id) {
		return pessoaJuridicaRepository.findById(id).map(PessoaJuridicaDTO::toDto)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa juridica com ID " + id + " não encontrado."));
	}

	@Transactional
	public PessoaJuridicaDTO inserir(PessoaJuridicaInserirDTO pessoaJuridicaInserirDTO)
			throws EmailException, CnpjException, RazaoSocialException {
		if (pessoaJuridicaRepository.findByEmail(pessoaJuridicaInserirDTO.email()) != null) {
			throw new EmailException("Email já existente");
		}
		if (pessoaJuridicaRepository.findByCnpj(pessoaJuridicaInserirDTO.cnpj()) != null) {
			throw new CpfException("Cnpj já existente");
		}
		if (pessoaJuridicaRepository.findByRazaoSocial(pessoaJuridicaInserirDTO.razaoSocial()) != null) {
			throw new RazaoSocialException("Razão Social já existente");
		}

		Endereco enderecoAssociado = null;

		enderecoAssociado = verificacaoEndereco(pessoaJuridicaInserirDTO.endereco());

		PessoaJuridica pessoaJuridica = new PessoaJuridica();

		pessoaJuridica.setNomeFantasia(pessoaJuridicaInserirDTO.nomeFantasia());
		pessoaJuridica.setRazaoSocial(pessoaJuridicaInserirDTO.razaoSocial());
		pessoaJuridica.setEmail(pessoaJuridicaInserirDTO.email());
		pessoaJuridica.setInscricaoEstadual(pessoaJuridicaInserirDTO.inscricaoEstadual());
		pessoaJuridica.setInscricaoMunicipal(pessoaJuridicaInserirDTO.inscricaoMunicipal());
		pessoaJuridica.setCnpj(pessoaJuridicaInserirDTO.cnpj());
		pessoaJuridica.setEndereco(enderecoAssociado);

		Set<PessoaJuridicaPessoaFisica> pessoas = new HashSet<>();
		for (PessoaFisicaCargoDTO pessoasFisica : pessoaJuridicaInserirDTO.pessoasFisicas()) {
			Optional<PessoaFisica> pessoaFisicaOPT = pessoaFisicaRepository.findById(pessoasFisica.id());

		    
		    if (pessoaFisicaOPT.isEmpty()) {
		        throw new PessoaFisicaException("Pessoa fisica não cadastrada");
		    }
		    
		    PessoaFisica pessoaFisicaVerify = pessoaFisicaOPT.get();

		    String cargo = "Funcionário";

		    PessoaJuridicaPessoaFisicaPK pk = new PessoaJuridicaPessoaFisicaPK();
		    pk.setPessoaJuridica(pessoaJuridica);
		    pk.setPessoaFisica(pessoaFisicaVerify);
		    pk.setCargo(cargo);

		    PessoaJuridicaPessoaFisica pessoaJuridicaPessoaFisica = new PessoaJuridicaPessoaFisica();
		    pessoaJuridicaPessoaFisica.setId(pk);

		    pessoas.add(pessoaJuridicaPessoaFisica);
		}
		
		pessoaJuridica.setPessoaJuridicaPessoaFisicas(pessoas);

		pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

		return PessoaJuridicaDTO.toDto(pessoaJuridica);

	}

	private Endereco verificacaoEndereco(Endereco enderecoRecebido) throws EnderecoException {

		List<Endereco> byLogradouro = enderecoRepository
				.findByLogradouro(enderecoRecebido.getLogradouro().toLowerCase());

		Endereco enderecoAssociado = null;

		for (Endereco endereco : byLogradouro) {
			if (endereco.getComplemento() != null && enderecoRecebido.getComplemento() != null) {
				if (endereco.getNumero().equals(enderecoRecebido.getNumero())
						&& endereco.getComplemento().toUpperCase()
								.equals(enderecoRecebido.getComplemento().toUpperCase())
						&& endereco.getBairro().toUpperCase().equals(enderecoRecebido.getBairro().toUpperCase())
						&& endereco.getCidade().toUpperCase().equals(enderecoRecebido.getCidade().toUpperCase())) {
					throw new EnderecoException("Já existe uma empresa cadastrada nesse endereco");
				}
			} else {
				if (endereco.getNumero().equals(enderecoRecebido.getNumero())
						&& endereco.getBairro().toUpperCase().equals(enderecoRecebido.getBairro().toUpperCase())
						&& endereco.getCidade().toUpperCase().equals(enderecoRecebido.getCidade().toUpperCase())) {
					throw new EnderecoException("Já existe uma empresa cadastrada nesse endereco");
				}
			}
		}

		enderecoAssociado = enderecoService.inserir(enderecoRecebido).toEntity();
		return enderecoAssociado;
	}

}
