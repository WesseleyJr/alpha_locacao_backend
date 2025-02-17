package br.com.alpha.locacao.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alpha.locacao.dto.PessoaFisicaDTO;
import br.com.alpha.locacao.dto.PessoaFisicaInserirDTO;
import br.com.alpha.locacao.dto.PessoaJuridicaDTO;
import br.com.alpha.locacao.dto.PessoaJuridicaInserirDTO;
import br.com.alpha.locacao.service.PessoaJuridicaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa/juridica")
public class PessoaJuridicaController {
	
	@Autowired
	private PessoaJuridicaService pessoaJuridicaService;
	
	@GetMapping
	public List<PessoaJuridicaDTO> listarTodos() {
	        return pessoaJuridicaService.listarTodos();
	}

    @PostMapping
    public ResponseEntity<PessoaJuridicaDTO> salvar(@Valid @RequestBody PessoaJuridicaInserirDTO pessoaJuridicaInserirDTO ) {
    	PessoaJuridicaDTO pessoaJuridicaDTO = pessoaJuridicaService.inserir(pessoaJuridicaInserirDTO);
    	URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pessoaJuridicaDTO.id())
				.toUri();
    	
    	return ResponseEntity.created(uri).body(pessoaJuridicaDTO);
    }
}
