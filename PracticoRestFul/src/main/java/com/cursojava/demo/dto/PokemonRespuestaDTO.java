package com.cursojava.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PokemonRespuestaDTO {
	private List<PokemonDTO> contenido;
	private int numeroDePagina;
	private int tamanoPagina;
	private Long CantidadElementos;
	private int CantidadPaginas;
	private Boolean ultima;

}
