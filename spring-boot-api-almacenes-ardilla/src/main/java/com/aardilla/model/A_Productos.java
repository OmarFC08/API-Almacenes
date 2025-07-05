package com.aardilla.model;

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
@Table(name = "A_PRODUCTOS")
public class A_Productos {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_a_productos")
    @SequenceGenerator(name = "seq_a_productos", sequenceName = "SEQ_A_PRODUCTOS", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NOMBRE")
	private String nombre;
	@Column(name = "PRECIO")
	private Double precio;
	@Column(name = "STOCK")
	private Double stock;
	@ManyToOne
	@JoinColumn(name = "TIPO", referencedColumnName = "ID", nullable = false)
	private A_Tipo_Producto tipoProducto;
}
