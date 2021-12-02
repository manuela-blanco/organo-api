package bsi.pcs.organo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bsi.pcs.organo.entity.CompradorEntity;
import bsi.pcs.organo.service.CompradorService;

@RestController
@RequestMapping("/comprador")
public class CompradorController {
	
	@Autowired
	private CompradorService compradorService;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> create(@RequestBody(required = true) CompradorEntity comprador) {
		
		if(this.compradorService.retornar(comprador.getCpf()) != null) {
			return ResponseEntity.badRequest().body("Comprador já está cadastrado");
		}
		
		this.compradorService.cadastrar(comprador);
		return ResponseEntity.status(HttpStatus.CREATED).body("Comprador cadastrado com sucesso."); 
		
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> update(@RequestBody(required = true) CompradorEntity comprador) {
		
		if(this.compradorService.retornar(comprador.getCpf()) == null) {
			return ResponseEntity.badRequest().body("Comprador informado não existe");
		}
		
		this.compradorService.atualizar(comprador);
		return ResponseEntity.status(HttpStatus.OK).body("Comprador atualizado com sucesso."); 
		
	}
	
	@GetMapping("/{cpfComprador}")
	public ResponseEntity<?> getComprador(@PathVariable(required = true) String cpfComprador) {
		
		CompradorEntity comprador = this.compradorService.retornar(cpfComprador);
		if(comprador == null) return ResponseEntity.badRequest().body("CPF informado não está associado a nenhum comprador.");
		
		return ResponseEntity.status(HttpStatus.OK).body(comprador);
	}
	
	@GetMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody(required = true) CompradorEntity comprador) {
		
		if(this.compradorService.autenticar(comprador)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Comprador autenticado com sucesso");
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não foi possível autenticar comprador.");
	}
}
