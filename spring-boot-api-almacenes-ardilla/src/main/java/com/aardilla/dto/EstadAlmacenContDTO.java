package com.aardilla.dto;

import java.util.List;

import lombok.Data;

@Data
public class EstadAlmacenContDTO {

	private List<EstadisticasAlmacenDTO> productos;
	private double totalInventario;
	
	
	public EstadAlmacenContDTO(List<EstadisticasAlmacenDTO> productos, double totalInventario) {
		super();
		this.productos = productos;
		this.totalInventario = totalInventario;
	}
}
