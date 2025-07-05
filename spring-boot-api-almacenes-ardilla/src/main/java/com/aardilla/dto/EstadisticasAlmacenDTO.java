package com.aardilla.dto;

import lombok.Data;

@Data
public class EstadisticasAlmacenDTO {

	private String nombre;
	private String tipo;
	private double precio;
	private double stock;
	private double valorTotal;
	
	public EstadisticasAlmacenDTO(String nombre, String tipo, double precio, double stock) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.stock = stock;
		this.valorTotal = precio * stock;
	}
}
