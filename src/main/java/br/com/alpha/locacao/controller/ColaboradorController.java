package br.com.alpha.locacao.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    
	@GetMapping("/{id}")
	public ResponseEntity<ColaboradorDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(colaboradorService.buscarId(id));
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
    
//	@PatchMapping("/{id}")  
//	public ResponseEntity<ColaboradorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ColaboradorPatchDTO colaboradorPatchDTO) {
//		return ResponseEntity.ok(colaboradorService.atualizar(colaboradorPatchDTO,id));
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		colaboradorService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}


