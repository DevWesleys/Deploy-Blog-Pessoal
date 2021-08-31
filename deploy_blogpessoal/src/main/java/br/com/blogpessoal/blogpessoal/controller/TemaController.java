package br.com.blogpessoal.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.blogpessoal.blogpessoal.model.Tema;
import br.com.blogpessoal.blogpessoal.repository.TemaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {

	@Autowired
	private TemaRepository temarepository;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temarepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id) {
		return temarepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(temarepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}

	@PostMapping
	public ResponseEntity<Tema> post(@RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(temarepository.save(tema));
	}

	@PutMapping
	public ResponseEntity<Tema> putTema(@RequestBody Tema tema){
		
		Optional<Tema> temaUpdate = temarepository.findById(tema.getId());
		
		if (temaUpdate.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(temarepository.save(tema));
		}else{
			throw new ResponseStatusException(
		          	HttpStatus.NOT_FOUND, "Tema não encontrado!", null);
		}
			
	}
	
	@DeleteMapping("/{id}")
	public void deleteTema(@PathVariable long id) {

		Optional<Tema> tema = temarepository.findById(id);
		
		if (tema.isPresent()) {
			temarepository.deleteById(id);
		}else{
			throw new ResponseStatusException(
		          	HttpStatus.NOT_FOUND, "Tema não encontrado!", null);
		}
	}

}