package br.com.alpha.locacao.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alpha.locacao.domain.Endereco;
import br.com.alpha.locacao.service.EnderecoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping
	public List<Endereco> listar() {
		return enderecoService.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(enderecoService.buscarPorId(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Endereco> inserir(@Valid @RequestBody Endereco endereco) {
		System.out.println("tesssste" + endereco.getBairro());
		Endereco end = enderecoService.inserir(endereco);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(end).toUri();
		return ResponseEntity.created(uri).body(end);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
		return ResponseEntity.ok(enderecoService.atualizar(id, endereco));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		enderecoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
