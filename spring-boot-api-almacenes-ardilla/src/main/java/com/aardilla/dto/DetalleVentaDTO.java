package com.aardilla.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
	private Integer idProducto;
	private double cantidad;
	private double precioUnitario;
}
