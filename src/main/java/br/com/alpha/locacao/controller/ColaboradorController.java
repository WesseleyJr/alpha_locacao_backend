package br.com.alpha.locacao.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alpha.locacao.dto.ColaboradorDTO;
import br.com.alpha.locacao.dto.ColaboradorInserirDTO;
import br.com.alpha.locacao.service.ColaboradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

	@Autowired
    private ColaboradorService colaboradorService;

    @GetMapping
    public List<ColaboradorDTO> listarTodos() {
        return colaboradorService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<ColaboradorDTO> salvar(@Valid @RequestBody ColaboradorInserirDTO colaboradorInserirDto) {
    	ColaboradorDTO novoColaborador = colaboradorService.inserir(colaboradorInserirDto);
    	URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(novoColaborador.id())
				.toUri();
    	
    	return ResponseEntity.created(uri).body(novoColaborador);
    }

}


