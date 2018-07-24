package com.disney.challenge.cart.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disney.challenge.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

	Optional<Cart> findById(Long id);
}
