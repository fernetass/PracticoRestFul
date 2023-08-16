package com.cursojava.demo.dto;

import lombok.Data;

@Data
public class PokemonDTO {

	private Integer id;
	private Integer numero;
	private String nombre;
	private int ataque;
	private int defensa;
	private int velocidad;
	private int generacion;

}
