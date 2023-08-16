package com.cursojava.demo.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cursojava.demo.models.Pokemon;
import com.cursojava.demo.repositories.PokemonRepositorio;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PokemonRepositorioTest {

	@Autowired
	private PokemonRepositorio pokemonRepositorio;

	private Pokemon pokemon;

	@BeforeEach
	void setup() {
		pokemon = new Pokemon(100, "Po", 100, 100, 100, 100);
	}

	@DisplayName("Test para guardar un poke")
	@Test
	void testGuardarPokemon() {

		// given
		// Pokemon pokemon = new Pokemon(100, "Po", 100, 100, 100, 100);
		// when
		Pokemon pokemonBD = pokemonRepositorio.save(pokemon);
		// then
		assertThat(pokemonBD).isNotNull();
		assertThat(pokemonBD.getId()).isGreaterThan(0);

	}

	@DisplayName("Test para listar pokemones")
	@Test
	void testListarPokemones() {
		Pokemon pokemonOne = new Pokemon(200, "Pika", 200, 200, 200, 200);

		pokemonRepositorio.save(pokemonOne);
		pokemonRepositorio.save(pokemon);

		List<Pokemon> listaDB = pokemonRepositorio.findAll();

		assertThat(listaDB).isNotNull();
		assertThat(listaDB.size()).isEqualTo(2);

	}

	@DisplayName("Test buscar pokemon por id")
	@Test
	void testBuscarrPokemonPorId() {
		pokemonRepositorio.save(pokemon);
		Pokemon pokemonBD = pokemonRepositorio.findById(pokemon.getId()).get();
		assertThat(pokemonBD).isNotNull();
	}

	@DisplayName("Test buscar pokemon por nombre")
	@Test
	void testBuscarrPokemonPorNombre() {
		pokemonRepositorio.save(pokemon);
		Pokemon pokemonBD = pokemonRepositorio.findByNombre(pokemon.getNombre());
		assertThat(pokemonBD).isNotNull();
	}

	@DisplayName("Test actualizar pokemon")
	@Test
	void testActualizarPokemon() {
		pokemonRepositorio.save(pokemon);

		Pokemon pokemonBD = pokemonRepositorio.findById(pokemon.getId()).get();

		pokemonBD.setNumero(300);
		pokemonBD.setNombre("Po");
		pokemonBD.setAtaque(300);
		pokemonBD.setDefensa(300);
		pokemonBD.setVelocidad(300);
		pokemonBD.setGeneracion(300);

		Pokemon pokemonUpdate = pokemonRepositorio.save(pokemonBD);

		assertThat(pokemonUpdate.getNumero()).isEqualTo(300);
		assertThat(pokemonUpdate.getNombre()).isEqualTo("Po");
		assertThat(pokemonUpdate.getAtaque()).isEqualTo(300);
		assertThat(pokemonUpdate.getDefensa()).isEqualTo(300);
		assertThat(pokemonUpdate.getVelocidad()).isEqualTo(300);
		assertThat(pokemonUpdate.getGeneracion()).isEqualTo(300);
	}

	@DisplayName("test para eliminar un pokemon")
	@Test
	void testEliminarPokemon() {

		pokemonRepositorio.save(pokemon);

		pokemonRepositorio.deleteById(pokemon.getId());

		Optional<Pokemon> pokemonBorrado = pokemonRepositorio.findById(pokemon.getId());

		assertThat(pokemonBorrado).isEmpty();

	}

	@DisplayName("test para ver existencia de pokemon")
	@Test
	void testExistePokemon() {

		pokemonRepositorio.save(pokemon);
		Boolean existePoke = pokemonRepositorio.existsByNombre(pokemon.getNombre());
		assertThat(existePoke).isTrue();

	}

}
