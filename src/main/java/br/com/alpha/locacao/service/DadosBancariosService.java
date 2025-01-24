package br.com.alpha.locacao.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Colaborador;
import br.com.alpha.locacao.domain.DadosBancarios;
import br.com.alpha.locacao.domain.PessoaFisica;
import br.com.alpha.locacao.domain.PessoaJuridica;
import br.com.alpha.locacao.dto.DadosBancariosDTO;
import br.com.alpha.locacao.dto.DadosBancariosInserirDTO;
import br.com.alpha.locacao.exception.DadosBancariosException;
import br.com.alpha.locacao.repository.ColaboradorRepository;
import br.com.alpha.locacao.repository.DadosBancariosRepository;
import br.com.alpha.locacao.repository.PessoaFisicaRepository;
import br.com.alpha.locacao.repository.PessoaJuridicaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DadosBancariosService {
	
	
	@Autowired
	private DadosBancariosRepository dadosBancariosRepository;

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;

	public List<DadosBancariosDTO> listar() {
		return dadosBancariosRepository.findAll().stream().map(DadosBancariosDTO::new).collect(Collectors.toList());
	}

	public DadosBancariosDTO buscarPorId(Long id) {
		DadosBancarios dadosBancarios = dadosBancariosRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Dados bancarios não encontrado"));
		return new DadosBancariosDTO(dadosBancarios);
	}

	@Transactional
	public DadosBancariosDTO inserir(DadosBancariosInserirDTO dadosBancariosInserirDTO) {
		
		DadosBancarios dadosBancariosV = dadosBancariosRepository.findDadosBancariosByContaAndAgenciaAndCodigoInstituicao(dadosBancariosInserirDTO.conta(), dadosBancariosInserirDTO.agencia(), dadosBancariosInserirDTO.codigoInstituicao());
		
		if(dadosBancariosV != null) {
			throw new DadosBancariosException("Dados Bancarios já cadastrado");
		}
		
		if (dadosBancariosInserirDTO.idPessoaFisica() != null && dadosBancariosInserirDTO.idPessoaJuridica() != null && dadosBancariosInserirDTO.idColaborador() != null) {
			throw new DadosBancariosException(
					"Não é permitido associar o dados bancarios a mais de uma pessoa.");
		}
		if (dadosBancariosInserirDTO.idPessoaFisica() == null && dadosBancariosInserirDTO.idPessoaJuridica() == null && dadosBancariosInserirDTO.idColaborador() == null) {
			throw new DadosBancariosException(
					"É necessário associar o dados bancarios a uma pessoa.");
		}
		
		PessoaFisica pessoaFisica = null;
		PessoaJuridica pessoaJuridica = null;
		Colaborador colaborador = null;
		
		
		if(dadosBancariosInserirDTO.idPessoaFisica() != null) {
			pessoaFisica = buscarPessoaFisicaPorId(dadosBancariosInserirDTO.idPessoaFisica());
		}

		if (dadosBancariosInserirDTO.idPessoaJuridica() != null) {
			pessoaJuridica = buscarPessoaJuridicaPorId(dadosBancariosInserirDTO.idPessoaJuridica());
		}
		
		if (dadosBancariosInserirDTO.idColaborador() != null) {
			colaborador = buscarColaboradorPorId(dadosBancariosInserirDTO.idColaborador());
		}
		
		DadosBancarios dadosBancarios = new DadosBancarios();
		dadosBancarios.setAgencia(dadosBancariosInserirDTO.agencia());
		dadosBancarios.setCodigoInstituicao(dadosBancariosInserirDTO.codigoInstituicao());
		dadosBancarios.setConta(dadosBancariosInserirDTO.conta());
		dadosBancarios.setInstituicaoFinanceira(dadosBancariosInserirDTO.instituicaoFinanceira());
		dadosBancarios.setOutros(dadosBancariosInserirDTO.outros());
		dadosBancarios.setPix(dadosBancariosInserirDTO.pix());
		dadosBancarios.setTipo(dadosBancariosInserirDTO.tipo());
		dadosBancarios.setDigitoConta(dadosBancariosInserirDTO.digitoConta());
		dadosBancarios.setPessoaFisica(pessoaFisica);
		dadosBancarios.setPessoaJuridica(pessoaJuridica);
		dadosBancarios.setColaborador(colaborador);

		dadosBancarios = dadosBancariosRepository.save(dadosBancarios);
		return new DadosBancariosDTO(dadosBancarios);
	}

	@Transactional
	public DadosBancariosDTO atualizar(Long id, DadosBancariosInserirDTO dadosBancariosInserirDTO) {

		DadosBancarios dadosBancarios = dadosBancariosRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Dados bancarios não encontrado"));

		if (dadosBancariosInserirDTO.idPessoaFisica() != null && dadosBancariosInserirDTO.idPessoaJuridica() != null && dadosBancariosInserirDTO.idColaborador() != null) {
			throw new DadosBancariosException(
					"Não é permitido associar o dados bancarios a mais de uma pessoa.");
		}
		if (dadosBancariosInserirDTO.idPessoaFisica() == null && dadosBancariosInserirDTO.idPessoaJuridica() == null && dadosBancariosInserirDTO.idColaborador() == null) {
			throw new DadosBancariosException(
					"É necessário associar o dados bancarios a uma pessoa.");
		}
		
		PessoaFisica pessoaFisica = null;
		PessoaJuridica pessoaJuridica = null;
		Colaborador colaborador = null;
		
		
		if(dadosBancariosInserirDTO.idPessoaFisica() != null) {
			pessoaFisica = buscarPessoaFisicaPorId(dadosBancariosInserirDTO.idPessoaFisica());
		}

		if (dadosBancariosInserirDTO.idPessoaJuridica() != null) {
			pessoaJuridica = buscarPessoaJuridicaPorId(dadosBancariosInserirDTO.idPessoaJuridica());
		}
		
		if (dadosBancariosInserirDTO.idColaborador() != null) {
			colaborador = buscarColaboradorPorId(dadosBancariosInserirDTO.idColaborador());
		}
		
		dadosBancarios.setAgencia(dadosBancariosInserirDTO.agencia());
		dadosBancarios.setCodigoInstituicao(dadosBancariosInserirDTO.codigoInstituicao());
		dadosBancarios.setConta(dadosBancariosInserirDTO.conta());
		dadosBancarios.setInstituicaoFinanceira(dadosBancariosInserirDTO.instituicaoFinanceira());
		dadosBancarios.setOutros(dadosBancariosInserirDTO.outros());
		dadosBancarios.setPix(dadosBancariosInserirDTO.pix());
		dadosBancarios.setTipo(dadosBancariosInserirDTO.tipo());
		dadosBancarios.setDigitoConta(dadosBancariosInserirDTO.digitoConta());
		dadosBancarios.setPessoaFisica(pessoaFisica);
		dadosBancarios.setPessoaJuridica(pessoaJuridica);
		dadosBancarios.setColaborador(colaborador);

		dadosBancarios = dadosBancariosRepository.save(dadosBancarios);
		return new DadosBancariosDTO(dadosBancarios);
	}

	@Transactional
	public void deletar(Long id) {
		if (!dadosBancariosRepository.existsById(id)) {
			throw new EntityNotFoundException("Dados bancarios não encontrado");
		}
		dadosBancariosRepository.deleteById(id);
	}

	private PessoaFisica buscarPessoaFisicaPorId(Long id) {
		return Optional.ofNullable(id).flatMap(pessoaFisicaRepository::findById)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa Física não encontrada"));
	}

	private PessoaJuridica buscarPessoaJuridicaPorId(Long id) {
		return Optional.ofNullable(id).flatMap(pessoaJuridicaRepository::findById)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada"));
	}
	
	private Colaborador buscarColaboradorPorId(Long id) {
		return Optional.ofNullable(id).flatMap(colaboradorRepository::findById)
				.orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada"));
	}

}
