package br.com.alpha.locacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.dto.EnderecoDTO;
import br.com.alpha.locacao.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<EnderecoDTO> listar() {
		return enderecoRepository.findAll().stream().map(EnderecoDTO::toDto).toList();
	}

	public EnderecoDTO buscarPorId(Long id) {
		return enderecoRepository.findById(id).map(EnderecoDTO::toDto)
				.orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado."));
	}

	@Transactional
	public EnderecoDTO inserir(Endereco enderecoD) {

		Endereco endereco = new Endereco();
		endereco.setBairro(enderecoD.getBairro().toLowerCase());
		endereco.setCidade(enderecoD.getCidade().toLowerCase());
		endereco.setCodigoPostal(enderecoD.getCodigoPostal());
		endereco.setComplemento(enderecoD.getComplemento() != null ? enderecoD.getComplemento().toLowerCase()
				: enderecoD.getComplemento());
		endereco.setLogradouro(enderecoD.getLogradouro().toLowerCase());
		endereco.setNumero(enderecoD.getNumero());
		endereco.setPais(enderecoD.getPais().toLowerCase());
		endereco.setUf(enderecoD.getUf().toLowerCase());

		endereco = enderecoRepository.save(endereco);
		return EnderecoDTO.toDto(endereco);
	}

	@Transactional
	public EnderecoDTO atualizar(Long id, Endereco enderecoAtualizado) {
		Optional<Endereco> byId = enderecoRepository.findById(id);

		if (!byId.isPresent()) {
			throw new EntityNotFoundException("Endereço nãoo encontrado");
		}

		Endereco endereco = byId.get();
		endereco.setBairro(enderecoAtualizado.getBairro());
		endereco.setCidade(enderecoAtualizado.getCidade());
		endereco.setCodigoPostal(enderecoAtualizado.getCodigoPostal());
		endereco.setComplemento(enderecoAtualizado.getComplemento());
		endereco.setLogradouro(enderecoAtualizado.getLogradouro());
		endereco.setNumero(enderecoAtualizado.getNumero());
		endereco.setPais(enderecoAtualizado.getPais());
		endereco.setUf(enderecoAtualizado.getUf());

		endereco = enderecoRepository.save(endereco);
		return EnderecoDTO.toDto(endereco);
	}

	@Transactional
	public void deletar(Long id) {
		if (!enderecoRepository.existsById(id)) {
			throw new EntityNotFoundException("Endereço não encontrado");
		}

		enderecoRepository.deleteById(id);

	}

}
