package com.cursojava.demo.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter @Setter
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String nombrerecurso;
	private String nombreCampo;
	private Integer valor;
	
	
	public ResourceNotFoundException(String nombrerecurso, String nombreCampo, Integer valor) {
		super(String.format("%s no encontrada con: %s,'%s'",nombrerecurso,nombreCampo,valor));
		this.nombrerecurso = nombrerecurso;
		this.nombreCampo = nombreCampo;
		this.valor = valor;
	}

}
