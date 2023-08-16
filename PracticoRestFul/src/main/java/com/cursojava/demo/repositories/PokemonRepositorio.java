package com.cursojava.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursojava.demo.models.Pokemon;

public interface PokemonRepositorio extends JpaRepository<Pokemon, Integer> {

	public Pokemon findByNombre(String nombre);

	public Boolean existsByNombre(String nombre);

}
