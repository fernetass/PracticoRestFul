package com.cursojava.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "pokemon", uniqueConstraints = { @UniqueConstraint(columnNames = {"nombre"}) })
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer numero;
	@Column(nullable = false)
	private String nombre;
	private int ataque;
	private int defensa;
	private int velocidad;
	private int generacion;
	
	
	public Pokemon(Integer numero, String nombre, int ataque, int defensa, int velocidad, int generacion) {
		super();
		this.numero = numero;
		this.nombre = nombre;
		this.ataque = ataque;
		this.defensa = defensa;
		this.velocidad = velocidad;
		this.generacion = generacion;
	}
	
	

}
