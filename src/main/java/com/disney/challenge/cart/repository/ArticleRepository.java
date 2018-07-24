package com.disney.challenge.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disney.challenge.cart.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	Optional<Article> findById(Long id);
}
