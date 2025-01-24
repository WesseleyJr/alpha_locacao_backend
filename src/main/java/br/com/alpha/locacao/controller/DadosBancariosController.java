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
import br.com.alpha.locacao.dto.DadosBancariosDTO;
import br.com.alpha.locacao.dto.DadosBancariosInserirDTO;
import br.com.alpha.locacao.dto.TelefoneDTO;
import br.com.alpha.locacao.dto.TelefoneInserirDTO;
import br.com.alpha.locacao.service.DadosBancariosService;
import br.com.alpha.locacao.service.TelefoneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dadosbancarios")
public class DadosBancariosController {
	
	@Autowired
	private DadosBancariosService dadosBancariosService;
	
	@GetMapping
	public List<DadosBancariosDTO> listar() {
		return dadosBancariosService.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosBancariosDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(dadosBancariosService.buscarPorId(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<DadosBancariosDTO> inserir(@Valid @RequestBody DadosBancariosInserirDTO dadosBancariosInserirDTO) {
		DadosBancariosDTO dadosBancariosDTO = dadosBancariosService.inserir(dadosBancariosInserirDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dadosBancariosDTO).toUri();
		return ResponseEntity.created(uri).body(dadosBancariosDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DadosBancariosDTO> atualizar(@PathVariable Long id, @Valid @RequestBody DadosBancariosInserirDTO dadosBancariosInserirDTO) {
		return ResponseEntity.ok(dadosBancariosService.atualizar(id, dadosBancariosInserirDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		dadosBancariosService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
