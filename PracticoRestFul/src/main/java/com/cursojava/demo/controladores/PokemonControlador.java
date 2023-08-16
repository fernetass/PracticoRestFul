package com.cursojava.demo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.demo.dto.PokemonDTO;
import com.cursojava.demo.dto.PokemonRespuestaDTO;
import com.cursojava.demo.services.PokemonServicio;

@RestController
@RequestMapping("/apiV1/pokemons")
public class PokemonControlador {

	@Autowired
	private PokemonServicio pokemonServicio;

	@GetMapping
	public List<PokemonDTO> obtenerTodosLosPokemos() {
		return pokemonServicio.listarPokemones();
	}

	@RequestMapping("/list/Pag")
	public PokemonRespuestaDTO obtenerTodosLosPokemosPag(
			@RequestParam(value = "PagNo", defaultValue = "0", required = false) int numeroDePagina,
			@RequestParam(value = "PageSize", defaultValue = "2", required = false) int tamanoDePagina) {
		return pokemonServicio.listarPokemonesPag(numeroDePagina, tamanoDePagina);
	}

	@RequestMapping("/ByNombre/{nombre}")
	public PokemonDTO obtenerPokemonesPorId(@PathVariable(name = "nombre") String nombre) {
		return pokemonServicio.buscarPokemonPorNombre(nombre);
	}

	@GetMapping("/ById/{id}")
	public ResponseEntity<PokemonDTO> obtenerPokemonPorId(@PathVariable(name = "id") Integer id) {
		return new ResponseEntity<>(pokemonServicio.buscarPokemonPorId(id), HttpStatus.OK);

	}

	@RequestMapping("/Ext")
	public ResponseEntity<List<PokemonDTO>> obtenerTodosLosPokemosExt() {
		return new ResponseEntity<>(pokemonServicio.listarPokemonesExt(), HttpStatus.OK);
	}

	@RequestMapping("/Ext/{id}")
	public ResponseEntity<PokemonDTO> obtenerPokemonPorIdExt(@PathVariable(name = "id") Integer id) {
		return new ResponseEntity<>(pokemonServicio.buscarPokemonPorIdExt(id), HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PokemonDTO> guardar(@RequestBody PokemonDTO pokemonDto) {

		PokemonDTO respuesta = pokemonServicio.guardarPokemon(pokemonDto);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardarExt/Persis/", method = RequestMethod.POST)
	public ResponseEntity<PokemonDTO> guardarExt(@RequestBody PokemonDTO pokemonDto) {
		PokemonDTO respuesta = pokemonServicio.guardarPokemonExt(pokemonDto);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PokemonDTO> actualizarPublicacion(@RequestBody PokemonDTO pokemonDTO,
			@PathVariable(name = "id") Integer id) {

		PokemonDTO pokemonRespuesta = pokemonServicio.actualizarPokemon(pokemonDTO, id);
		return new ResponseEntity<>(pokemonRespuesta, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/Ext/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePokemonExt(@PathVariable(name = "id") Integer id, @RequestBody PokemonDTO pokemonDTO) {
		pokemonServicio.actualizarPokemonExt(pokemonDTO, id);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPokemon(@PathVariable(name = "id") Integer id) {
		pokemonServicio.eliminarPokemon(id);
		return new ResponseEntity<>("Pokemon eliminado correctamente", HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/Ext/{id}", method = RequestMethod.DELETE)
	public void eliminarPokemonExt(@PathVariable(name = "id") Integer id) {
		pokemonServicio.eliminarPokemonExt(id);
	}

}
