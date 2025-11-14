package com.bookstore.api.service;

import java.util.List;

import com.bookstore.api.model.Book;

public interface BookService {
	Book addBook(Long authorId, Book book);
	Book getBookById(Long id);
	List<Book> getAllBooks();
	List<Book> getBooksByTitle(String title);
	List<Book> getBooksByAuthor(Long id);
	Book updateBook(Long id, Book book);
	void deleteBook(Long id);
}
