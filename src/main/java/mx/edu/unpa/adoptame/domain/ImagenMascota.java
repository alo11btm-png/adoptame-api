package mx.edu.unpa.adoptame.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad alineada con tabla ImagenMascota (adoptame.sql).
 */
@Data
@Entity
@Table(name = "ImagenMascota")
public class ImagenMascota implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idImagen")
	private Integer idImagen;

	@ManyToOne
	@JoinColumn(name = "idMascota", nullable = false)
	@JsonIgnore
	private Mascota mascota;

	@Column(name = "urlImagen", nullable = false, length = 255)
	private String urlImagen;

	@Column(name = "imagenPrincipal")
	private Boolean imagenPrincipal = false;

	@Column(name = "fechaSubida", insertable = false, updatable = false)
	private LocalDateTime fechaSubida;
}
