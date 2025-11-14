package com.bookstore.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.api.model.Book;
import com.bookstore.api.service.AuthorService;
import com.bookstore.api.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;
	
	@PostMapping("/add/{authorId}")
	public ResponseEntity<?> addBook(@PathVariable Long authorId, @RequestBody Book book) {
		Book saved = bookService.addBook(authorId, book);
		if (saved == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Book added failed: valid author requried!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		if (books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.build();
		}
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("authorId/{authorId}")
	public ResponseEntity<?> getBooksByAuthor(@PathVariable Long authorId) {
		
		if (authorService.getAuthorById(authorId) == null)  {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Author not found witd id: " + authorId);
		}
		List<Book> books = bookService.getBooksByAuthor(authorId);
		if (books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No books found for author with id: " + authorId);
		}
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("title/{title}")
	public ResponseEntity<?> getBookById(@PathVariable String title) {
		List<Book> books = bookService.getBooksByTitle(title);
		if (books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Book not found witd title: " + title);
		}
		return ResponseEntity.ok(books);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book) {
		Book updated = bookService.updateBook(id, book);
		if (updated == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Book not found with id: " + id);
		}
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		Book book = bookService.getBookById(id);
		if (book == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Book not found with id: " + id);
		}
		bookService.deleteBook(id);
		return ResponseEntity.ok("Book deleted successfully!");
	}
	
}
