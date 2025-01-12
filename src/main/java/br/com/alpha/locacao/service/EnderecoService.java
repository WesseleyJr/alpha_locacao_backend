package br.com.alpha.locacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.repository.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Endereco> listar() {
		return enderecoRepository.findAll();
	}

	public Endereco buscarPorId(Long id) {
		Optional<Endereco> byId = enderecoRepository.findById(id);
		
		if (!byId.isPresent()) {
			throw new RuntimeException("Endereço não encontrado");
		}
		
		Endereco endereco = byId.get();
		return endereco;
	}

	@Transactional
	public Endereco inserir(Endereco enderecoD) {
		
		Endereco endereco = new Endereco();
		endereco.setBairro(enderecoD.getBairro());
		endereco.setCidade(enderecoD.getCidade());
		endereco.setCodigoPostal(enderecoD.getCodigoPostal());
		endereco.setComplemento(enderecoD.getComplemento());
		endereco.setLogradouro(enderecoD.getLogradouro());
		endereco.setNumero(enderecoD.getNumero());
		endereco.setPais(enderecoD.getPais());
		endereco.setUf(enderecoD.getUf());
		
		return enderecoRepository.save(endereco);
	}

	@Transactional
	public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
		Optional<Endereco> byId = enderecoRepository.findById(id);

		if (!byId.isPresent()) {
			throw new RuntimeException("Endereço nãoo encontrado");
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
		return endereco;
	}

	@Transactional
	public void deletar(Long id) {
		if (!enderecoRepository.existsById(id)) {
			throw new RuntimeException("Endereço não encontrado");
		}

		enderecoRepository.deleteById(id);

	}

}
