package com.aardilla.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "A_DETALLE_VENTA")
public class A_Detalle_Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_detalle_venta_seq")
    @SequenceGenerator(name = "a_detalle_venta_seq", sequenceName = "A_DETALLE_VENTA_SEQ", allocationSize = 1)
	@Column(name = "id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "id_venta", nullable = false)
	@JsonBackReference
	private A_Ventas venta;
	@ManyToOne()
	@JoinColumn(name = "id_producto")
	private A_Productos producto;
	@Column(name = "cantidad")
	private double cantidad;
	@Column(name = "precio_unitario")
	private double precioUnitario;
	@Column(name = "subtotal")
	private double subtotal;
}
