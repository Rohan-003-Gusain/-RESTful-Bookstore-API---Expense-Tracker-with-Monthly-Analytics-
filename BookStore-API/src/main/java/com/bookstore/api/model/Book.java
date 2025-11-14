package com.bookstore.api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title;
	
	@Column(unique = true, nullable = false)
	private String code;
	private String price;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	@JsonIgnoreProperties({"id", "email", "bio", "books"})
	private Author author;
	
	@PrePersist
	public void geberateCode() {
		this.code = "BK-" + UUID.randomUUID().toString().substring(0, 8);
	}
}
