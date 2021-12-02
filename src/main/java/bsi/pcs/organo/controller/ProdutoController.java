package bsi.pcs.organo.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bsi.pcs.organo.entity.FornecedorEntity;
import bsi.pcs.organo.entity.ProdutoEntity;
import bsi.pcs.organo.service.FornecedorService;
import bsi.pcs.organo.service.ProdutoService;

@RestController
@RequestMapping("/produto/{cnpjFornecedor}")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FornecedorService fornecedorService;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> create(@RequestBody(required = true) ProdutoEntity produto,
									@PathVariable(required = true) String cnpjFornecedor) {
		
		FornecedorEntity fornecedorEncontrado = this.fornecedorService.retornar(cnpjFornecedor);
		produto.setFornecedor(fornecedorEncontrado);
		
		if(this.produtoService.retornar(produto.getFornecedor().getCnpj(), produto.getNome()) != null) {
			return ResponseEntity.badRequest().body("Produto já está cadastrado");
		}
		
		this.produtoService.cadastrar(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso."); 
		
	}
	
	@PutMapping("/cadastrarFoto/{produtoId}")
	public ResponseEntity<?> cadastrarFoto(@RequestParam("foto") MultipartFile foto, 
			@PathVariable(required = true) Long produtoId) throws IOException {
		
		if(foto == null) return ResponseEntity.badRequest().body("Foto vazia.");
		
		ProdutoEntity produtoEncontrado = this.produtoService.retornarById(produtoId);
		
		if(produtoEncontrado == null) {
			return ResponseEntity.badRequest().body("Produto não existe");
		}
		
		this.produtoService.cadastrarFoto(produtoEncontrado, foto);
		return ResponseEntity.status(HttpStatus.OK).body("Foto cadastrada com sucesso."); 
		
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> update(@RequestBody(required = true) ProdutoEntity produto) {
		
		if(this.produtoService.retornar(produto.getFornecedor().getCnpj(), produto.getNome()) == null) {
			return ResponseEntity.badRequest().body("Produto informado não existe");
		}
		
		this.produtoService.atualizar(produto);
		return ResponseEntity.status(HttpStatus.OK).body("Produto atualizado com sucesso."); 
		
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<?> getProduto(@PathVariable(required = true) Long produtoId) {
	
		ProdutoEntity produto = this.produtoService.retornarById(produtoId);
		if(produto == null) return ResponseEntity.badRequest().body("Os dados informados não estão associados a nenhum produto.");
		
		return ResponseEntity.status(HttpStatus.OK).body(produto);
	}
	
	@DeleteMapping("/deletar/{produtoId}")
	public ResponseEntity<?> deleteProduto(@PathVariable(required = true) Long produtoId) {
		ProdutoEntity produto = this.produtoService.retornarById(produtoId);
		if(produto == null) return ResponseEntity.badRequest().body("Os id informado não estão associados a nenhum produto.");
		
		this.produtoService.deletarProduto(produtoId);
		return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
		
	}
	
}