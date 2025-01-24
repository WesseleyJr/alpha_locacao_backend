package br.com.alpha.locacao.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.domain.Telefone;
import br.com.alpha.locacao.dto.TelefoneDTO;
import br.com.alpha.locacao.dto.TelefoneInserirDTO;
import br.com.alpha.locacao.exception.TelefoneException;
import br.com.alpha.locacao.repository.PessoaFisicaRepository;
import br.com.alpha.locacao.repository.PessoaJuridicaRepository;
import br.com.alpha.locacao.repository.TelefoneRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TelefoneService {

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	public List<TelefoneDTO> listar() {
		return telefoneRepository.findAll().stream().map(TelefoneDTO::new).collect(Collectors.toList());
	}

	public TelefoneDTO buscarPorId(Long id) {
		Telefone telefone = telefoneRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Telefone não encontrado"));
		return new TelefoneDTO(telefone);
	}

	public TelefoneDTO buscarPorNumeroEDdd(String numero, String ddd) {
		Telefone telefone = telefoneRepository.findTelefoneByNumeroAndDdd(numero, ddd);

		if (telefone == null) {
			throw new EntityNotFoundException("Telefone não encontrado");
		}
		return new TelefoneDTO(telefone);
	}

	@Transactional
	public TelefoneDTO inserir(TelefoneInserirDTO telefoneInserirDTO) {
		
		Telefone telefoneV = telefoneRepository.findTelefoneByNumeroAndDdd(telefoneInserirDTO.numero(), telefoneInserirDTO.ddd());
		
		if(telefoneV != null) {
			throw new TelefoneException("Telefone já cadastrado");
		}
		
		if (telefoneInserirDTO.idPessoaFisica() != null && telefoneInserirDTO.idPessoaJuridica() != null) {
			throw new TelefoneException(
					"Não é permitido associar o telefone a uma Pessoa Física e a uma Pessoa Jurídica ao mesmo tempo.");
		}
		if (telefoneInserirDTO.idPessoaFisica() == null && telefoneInserirDTO.idPessoaJuridica() == null) {
			throw new TelefoneException(
					"É necessário associar o telefone a uma Pessoa Física ou a uma Pessoa Jurídica.");
		}
		
		PessoaFisica pessoaFisica = null;
		PessoaJuridica pessoaJuridica = null;
		
		
		if(telefoneInserirDTO.idPessoaFisica() != null) {
			pessoaFisica = buscarPessoaFisicaPorId(telefoneInserirDTO.idPessoaFisica());
		}

		if (telefoneInserirDTO.idPessoaJuridica() != null) {
			pessoaJuridica = buscarPessoaJuridicaPorId(telefoneInserirDTO.idPessoaJuridica());
		}
		
		Telefone telefone = new Telefone();
		telefone.setCodigoPais(telefoneInserirDTO.codigoPais());
		telefone.setDdd(telefoneInserirDTO.ddd());
		telefone.setNumero(telefoneInserirDTO.numero());
		telefone.setTipo(telefoneInserirDTO.tipo());

		if (telefoneInserirDTO.idPessoaFisica() != null) {
			telefone.setPessoaFisica(pessoaFisica);
		}
		if (telefoneInserirDTO.idPessoaJuridica() != null) {
			telefone.setPessoaJuridica(pessoaJuridica);
		}

		telefone = telefoneRepository.save(telefone);
		return new TelefoneDTO(telefone);
	}

	@Transactional
	public TelefoneDTO atualizar(Long id, TelefoneInserirDTO telefoneInserirDTO) {

		Telefone telefone = telefoneRepository.findById(id)
				.orElseThrow(() -> new TelefoneException("Telefone não encontrado"));

		if (telefoneInserirDTO.idPessoaFisica() != null && telefoneInserirDTO.idPessoaJuridica() != null) {
			throw new TelefoneException(
					"Não é permitido associar o telefone a uma Pessoa Física e a uma Pessoa Jurídica ao mesmo tempo.");
		}
		if (telefoneInserirDTO.idPessoaFisica() == null && telefoneInserirDTO.idPessoaJuridica() == null) {
			throw new TelefoneException(
					"É necessário associar o telefone a uma Pessoa Física ou a uma Pessoa Jurídica.");
		}
		PessoaFisica pessoaFisica = null;
		PessoaJuridica pessoaJuridica = null;
		
		
		if(telefoneInserirDTO.idPessoaFisica() != null) {
			pessoaFisica = buscarPessoaFisicaPorId(telefoneInserirDTO.idPessoaFisica());
		}

		if (telefoneInserirDTO.idPessoaJuridica() != null) {
			pessoaJuridica = buscarPessoaJuridicaPorId(telefoneInserirDTO.idPessoaJuridica());
		}

		telefone.setCodigoPais(telefoneInserirDTO.codigoPais());
		telefone.setDdd(telefoneInserirDTO.ddd());
		telefone.setNumero(telefoneInserirDTO.numero());
		telefone.setTipo(telefoneInserirDTO.tipo());

		if (telefoneInserirDTO.idPessoaFisica() != null) {
			telefone.setPessoaFisica(pessoaFisica);
		}
		if (telefoneInserirDTO.idPessoaJuridica() != null) {
			telefone.setPessoaJuridica(pessoaJuridica);
		}

		telefone = telefoneRepository.save(telefone);
		return new TelefoneDTO(telefone);
	}

	@Transactional
	public void deletar(Long id) {
		if (!telefoneRepository.existsById(id)) {
			throw new EntityNotFoundException("Telefone não encontrado");
		}
		telefoneRepository.deleteById(id);
	}

	private PessoaFisica buscarPessoaFisicaPorId(Long id) {
		return Optional.ofNullable(id).flatMap(pessoaFisicaRepository::findById)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa Física não encontrada"));
	}

	private PessoaJuridica buscarPessoaJuridicaPorId(Long id) {
		return Optional.ofNullable(id).flatMap(pessoaJuridicaRepository::findById)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada"));
	}
}
