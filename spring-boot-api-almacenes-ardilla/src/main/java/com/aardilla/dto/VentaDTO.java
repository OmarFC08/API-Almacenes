package com.aardilla.dto;

import java.util.List;

import lombok.Data;

@Data
public class VentaDTO {
	private Integer idUsuario;
	private String formaPago;
	private List<DetalleVentaDTO> detalles;
}
