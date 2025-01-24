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
import br.com.alpha.locacao.domain.Telefone;
import br.com.alpha.locacao.dto.ColaboradorDTO;
import br.com.alpha.locacao.dto.TelefoneDTO;
import br.com.alpha.locacao.dto.TelefoneInserirDTO;
import br.com.alpha.locacao.service.TelefoneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/telefone")
public class TelefoneController {
	
	@Autowired
	private TelefoneService telefoneService;
	
	@GetMapping
	public List<TelefoneDTO> listar() {
		return telefoneService.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TelefoneDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(telefoneService.buscarPorId(id));
	}
	
	@GetMapping("/{ddd}/{numero}")
	public ResponseEntity<TelefoneDTO> buscarPorNumeroEDdd(@PathVariable String numero, @PathVariable String ddd) {
		return ResponseEntity.ok(telefoneService.buscarPorNumeroEDdd(numero, ddd));
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TelefoneDTO> inserir(@Valid @RequestBody TelefoneInserirDTO telefoneInserirDTO) {
		TelefoneDTO telefoneDTO = telefoneService.inserir(telefoneInserirDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(telefoneDTO).toUri();
		return ResponseEntity.created(uri).body(telefoneDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TelefoneDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TelefoneInserirDTO telefoneInserirDTO) {
		return ResponseEntity.ok(telefoneService.atualizar(id, telefoneInserirDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		telefoneService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
