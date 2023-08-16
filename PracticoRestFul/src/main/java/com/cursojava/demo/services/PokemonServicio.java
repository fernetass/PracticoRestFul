package com.cursojava.demo.services;

import java.util.List;

import com.cursojava.demo.dto.PokemonDTO;
import com.cursojava.demo.dto.PokemonRespuestaDTO;

public interface PokemonServicio {

	public PokemonDTO guardarPokemon(PokemonDTO PokemonDto);

	public PokemonDTO guardarPokemonExt(PokemonDTO pokemonDto);

	public List<PokemonDTO> listarPokemones();

	public PokemonRespuestaDTO listarPokemonesPag(int numeroPagina, int tamanoDePagina);

	public List<PokemonDTO> listarPokemonesExt();

	public PokemonDTO buscarPokemonPorNombre(String nombre);

	public PokemonDTO buscarPokemonPorId(Integer id);

	public PokemonDTO buscarPokemonPorIdExt(Integer id);

	public PokemonDTO actualizarPokemon(PokemonDTO pokemonDto, Integer id);

	public void actualizarPokemonExt(PokemonDTO pokemonDto, Integer id);

	public void eliminarPokemon(Integer id);

	public void eliminarPokemonExt(Integer id);

}
