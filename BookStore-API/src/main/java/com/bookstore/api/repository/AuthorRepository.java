package com.bookstore.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookstore.api.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	
}
