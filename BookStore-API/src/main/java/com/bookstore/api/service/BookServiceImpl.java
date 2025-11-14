package com.bookstore.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.api.model.Author;
import com.bookstore.api.model.Book;
import com.bookstore.api.repository.AuthorRepository;
import com.bookstore.api.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public Book addBook(Long authorId, Book book) {
		Author author = authorRepository.findById(authorId).orElse(null);
		if (author == null) {
			return null;
		}
		book.setAuthor(author);
		Book savedBook = bookRepository.save(book);
		author.getBooks().add(savedBook);
		authorRepository.save(author);
		return savedBook;
	}

	@Override
	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public List<Book> getBooksByTitle(String title) {
		return bookRepository.findByTitleContainingIgnoreCase(title);
	}

	@Override
	public List<Book> getBooksByAuthor(Long id) {
		return bookRepository.findByAuthorId(id);
	}

	@Override
	public Book updateBook(Long id, Book book) {
		Book existingBook = getBookById(id);
		
		if (existingBook != null) {
			existingBook.setTitle(book.getTitle());
			existingBook.setPrice(book.getPrice());
			if (book.getAuthor() != null) {
				Author author = authorRepository.findById(book.getAuthor().getId()).orElse(null);
				existingBook.setAuthor(author);
			}
			return bookRepository.save(existingBook);
		}
		return null;
	}

	@Override
	public void deleteBook(Long id) {
		Book book = getBookById(id);
		if (book != null) {
			bookRepository.delete(book);
		}
		
	}
	

}
