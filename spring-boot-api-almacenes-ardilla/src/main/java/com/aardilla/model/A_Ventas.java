package com.aardilla.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "A_VENTAS")
public class A_Ventas {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_ventas_seq")
    @SequenceGenerator(name = "a_ventas_seq", sequenceName = "A_VENTAS_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", nullable = false)
	private A_Usuario Usuario;
	@Column(name = "forma_pago")
	private String formaPago;
	@Column(name = "total")
	private double total;
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<A_Detalle_Venta> detalles;
	
}
