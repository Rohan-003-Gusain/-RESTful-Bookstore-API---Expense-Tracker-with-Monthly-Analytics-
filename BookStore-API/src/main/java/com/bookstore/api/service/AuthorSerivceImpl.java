package com.bookstore.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.api.model.Author;
import com.bookstore.api.repository.AuthorRepository;

@Service
public class AuthorSerivceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	@Override
	public Author createAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Author getAuthorById(Long id) {
		return authorRepository.findById(id).orElse(null);
	}

	@Override
	public List<Author> getAllAuthors() {
		List<Author> authors = authorRepository.findAll();
		return authors;
	}

	@Override
	public Author updateAuthor(Long id, Author author) {
		Author existingAuthor = getAuthorById(id);
		
		if (existingAuthor == null) {
			return null;
		}
		existingAuthor.setName(author.getName());
		existingAuthor.setEmail(author.getEmail());
		existingAuthor.setBio(author.getBio());
		return authorRepository.save(existingAuthor); 
	}

	@Override
	public void deleteAuthor(Long id) {
		Author author = getAuthorById(id);
		if (author != null) {
			authorRepository.delete(author);
		}
	}

}
