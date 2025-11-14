package com.bookstore.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.api.model.Author;
import com.bookstore.api.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@PostMapping
	public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
		Author savedAuthor = authorService.createAuthor(author);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
		Author author =  authorService.getAuthorById(id);
		if (author == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Author not found witd id: " + id);
		}
		return ResponseEntity.ok(author);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Author>> getAllAuthor() {
		List<Author> authors = authorService.getAllAuthors();
		if (authors.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(authors);
		}
		return ResponseEntity.ok(authors);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
		Author updatedAuthor = authorService.updateAuthor(id, author);
		if (updatedAuthor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Author not found with id: " + id);
		}
		return ResponseEntity.ok(updatedAuthor);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
	    Author author = authorService.getAuthorById(id); 
	    if (author == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Author not found with id: " + id);
	    }
	    authorService.deleteAuthor(id); 
	    return ResponseEntity.ok("Author deleted successfully");
	}


}
