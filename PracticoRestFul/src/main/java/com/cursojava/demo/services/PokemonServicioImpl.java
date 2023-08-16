package com.cursojava.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cursojava.demo.dto.PokemonDTO;
import com.cursojava.demo.dto.PokemonRespuestaDTO;
import com.cursojava.demo.excepciones.ResourceNotFoundException;
import com.cursojava.demo.models.Pokemon;
import com.cursojava.demo.repositories.PokemonRepositorio;

@Service
public class PokemonServicioImpl implements PokemonServicio {

	@Autowired
	private PokemonRepositorio pokemonRepositorio;

	@Autowired
	private RestTemplate restTemplate;

	private final String URL_BASE = "https://64d6cdde2a017531bc12db6a.mockapi.io/apiExt/Pokemon/";

	private Pokemon mapearDto(PokemonDTO pokemonDto) {

		Pokemon pokemon = new Pokemon();

		pokemon.setNumero(pokemonDto.getNumero());
		pokemon.setNombre(pokemonDto.getNombre());
		pokemon.setAtaque(pokemonDto.getAtaque());
		pokemon.setDefensa(pokemonDto.getDefensa());
		pokemon.setVelocidad(pokemonDto.getVelocidad());
		pokemon.setGeneracion(pokemonDto.getGeneracion());

		return pokemon;
	}

	private PokemonDTO mapearEntidad(Pokemon pokemon) {

		PokemonDTO pokemonRespuesta = new PokemonDTO();
		pokemonRespuesta.setId(pokemon.getId());
		pokemonRespuesta.setNumero(pokemon.getNumero());
		pokemonRespuesta.setNombre(pokemon.getNombre());
		pokemonRespuesta.setAtaque(pokemon.getAtaque());
		pokemonRespuesta.setDefensa(pokemon.getDefensa());
		pokemonRespuesta.setVelocidad(pokemon.getVelocidad());
		pokemonRespuesta.setGeneracion(pokemon.getGeneracion());

		return pokemonRespuesta;
	}

	@Override
	public PokemonDTO guardarPokemon(PokemonDTO PokemonDto) {

		// convierto DTO A ENTIDAD
		Pokemon pokemon = mapearDto(PokemonDto);

		Pokemon nuevaPokemon = pokemonRepositorio.save(pokemon);

		// CONVIERTO ENTIDAD A DTO
		PokemonDTO pokemonRespuesta = mapearEntidad(nuevaPokemon);

		return pokemonRespuesta;
	}

	@Override
	public List<PokemonDTO> listarPokemones() {

		List<Pokemon> pokemones = pokemonRepositorio.findAll();

		List<PokemonDTO> pokemonesDto = new ArrayList<>();

		for (Pokemon pokemon : pokemones) {
			pokemonesDto.add(mapearEntidad(pokemon));

		}
		return pokemonesDto;
	}

	@Override
	public PokemonRespuestaDTO listarPokemonesPag(int numeroPagina, int tamanoDePagina) {
		// TODO Auto-generated method stub

		Pageable pageable = PageRequest.of(numeroPagina, tamanoDePagina);
		Page<Pokemon> pokemones = pokemonRepositorio.findAll(pageable);

		List<Pokemon> PokemonesPaginados = pokemones.getContent();

		List<PokemonDTO> contenido = new ArrayList<>();

		for (Pokemon pokemon : PokemonesPaginados) {
			contenido.add(mapearEntidad(pokemon));
		}

		PokemonRespuestaDTO pokemonRespuesta = new PokemonRespuestaDTO();

		pokemonRespuesta.setContenido(contenido);
		pokemonRespuesta.setCantidadPaginas(pokemones.getTotalPages());
		pokemonRespuesta.setTamanoPagina(pokemones.getSize());
		pokemonRespuesta.setCantidadElementos(pokemones.getTotalElements());
		pokemonRespuesta.setNumeroDePagina(pokemones.getNumber());
		pokemonRespuesta.setUltima(pokemones.isLast());

		return pokemonRespuesta;

	}

	public PokemonDTO buscarPokemonPorNombre(String nombre) {
		Pokemon pokemon = pokemonRepositorio.findByNombre(nombre);
		return mapearEntidad(pokemon);
	}

	@Override
	public PokemonDTO buscarPokemonPorId(Integer id) {
		Pokemon pokemon = pokemonRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("pokemon", "id", id));
		return mapearEntidad(pokemon);
	}

	@Override
	public PokemonDTO actualizarPokemon(PokemonDTO pokemonDto, Integer id) {
		Pokemon pokemon = pokemonRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("pokemon", "id", id));

		pokemon.setNumero(pokemonDto.getNumero());
		pokemon.setNombre(pokemonDto.getNombre());
		pokemon.setAtaque(pokemonDto.getAtaque());
		pokemon.setDefensa(pokemonDto.getDefensa());
		pokemon.setVelocidad(pokemonDto.getVelocidad());
		pokemon.setGeneracion(pokemonDto.getGeneracion());

		Pokemon nuevoPokemon = pokemonRepositorio.save(pokemon);

		return mapearEntidad(nuevoPokemon);

	}

	@Override
	public void eliminarPokemon(Integer id) {
		Pokemon pokemon = pokemonRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("pokemon", "id", id));
		pokemonRepositorio.delete(pokemon);

	}

	@Override
	public List<PokemonDTO> listarPokemonesExt() {
		PokemonDTO[] respuesta = restTemplate.getForObject(URL_BASE, PokemonDTO[].class);
		return Arrays.asList(respuesta);
	}

	@Override
	public PokemonDTO buscarPokemonPorIdExt(Integer id) {
		PokemonDTO respuesta = restTemplate.getForObject(URL_BASE + id, PokemonDTO.class);
		return respuesta;
	}

	@Override
	public PokemonDTO guardarPokemonExt(PokemonDTO pokemonDto) {
		restTemplate.postForEntity(URL_BASE, pokemonDto, PokemonDTO.class);

		// convierto DTO A ENTIDAD
		Pokemon pokemon = mapearDto(pokemonDto);

		Pokemon nuevaPokemon = pokemonRepositorio.save(pokemon);

		// CONVIERTO ENTIDAD A DTO
		PokemonDTO pokemonRespuesta = mapearEntidad(nuevaPokemon);

		return pokemonRespuesta;
	}

	@Override
	public void actualizarPokemonExt(PokemonDTO pokemonDto, Integer id) {

		PokemonDTO pokemonDtoExt = buscarPokemonPorIdExt(id);
		Boolean existPoke = pokemonRepositorio.existsByNombre(pokemonDtoExt.getNombre());

		if (existPoke) {
			Pokemon pokemon = pokemonRepositorio.findByNombre(pokemonDtoExt.getNombre());

			pokemon.setNumero(pokemonDto.getNumero());
			pokemon.setNombre(pokemonDto.getNombre());
			pokemon.setAtaque(pokemonDto.getAtaque());
			pokemon.setDefensa(pokemonDto.getDefensa());
			pokemon.setVelocidad(pokemonDto.getVelocidad());
			pokemon.setGeneracion(pokemonDto.getGeneracion());

			pokemonRepositorio.save(pokemon);

			System.out.println("existe id =" + pokemon.getId());
		}

		restTemplate.put(URL_BASE + id, pokemonDto);
	}

	@Override
	public void eliminarPokemonExt(Integer id) {

		PokemonDTO pokemonDtoExt = buscarPokemonPorIdExt(id);
		Boolean existPoke = pokemonRepositorio.existsByNombre(pokemonDtoExt.getNombre());

		if (existPoke) {
			Pokemon pokemon = pokemonRepositorio.findByNombre(pokemonDtoExt.getNombre());
			eliminarPokemon(pokemon.getId());
			System.out.println("existe id=" + pokemon.getId());
		}

		restTemplate.delete(URL_BASE + id);
	}

}
