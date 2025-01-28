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

import br.com.alpha.locacao.dto.ColaboradorDTO;
import br.com.alpha.locacao.dto.ColaboradorInserirDTO;
import br.com.alpha.locacao.dto.PessoaFisicaDTO;
import br.com.alpha.locacao.dto.PessoaFisicaInserirDTO;
import br.com.alpha.locacao.service.PessoaFisicaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa/fisica")
public class PessoaFisicaController {
	
	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	@GetMapping
	public List<PessoaFisicaDTO> listarTodos() {
	        return pessoaFisicaService.listarTodos();
	}

    @PostMapping
    public ResponseEntity<PessoaFisicaDTO> salvar(@Valid @RequestBody PessoaFisicaInserirDTO pessoaFisicaInserirDTO) {
    	PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaService.inserir(pessoaFisicaInserirDTO);
    	URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pessoaFisicaDTO.id())
				.toUri();
    	
    	return ResponseEntity.created(uri).body(pessoaFisicaDTO);
    }
}
