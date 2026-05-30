package mx.edu.unpa.adoptame.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad alineada con tabla Mascota (adoptame.sql).
 * Columnas: idMascota, idUsuarioDonador, nombre, tipo, raza, sexo, edadAproximada,
 * descripcion, estadoAdopcion, activo, fechaPublicacion.
 */
@Data
@Entity
@Table(name = "Mascota")
public class Mascota implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMascota")
	@JsonProperty("idMascota")
	private Integer idMascota;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioDonador", nullable = false)
	@JsonIgnoreProperties(value = { "mascotas", "password", "hibernateLazyInitializer", "handler" })
	private Usuario usuarioDonador;

	@OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ImagenMascota> imagenes;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "tipo", nullable = false, length = 50)
	private String tipo;

	@Column(name = "raza", length = 100)
	private String raza;

	@Column(name = "sexo", nullable = false, columnDefinition = "ENUM('Macho', 'Hembra')")
	private String sexo;

	@Column(name = "edadAproximada", length = 50)
	private String edadAproximada;

	@Column(name = "descripcion", columnDefinition = "TEXT")
	private String descripcion;

	@Column(name = "estadoAdopcion", columnDefinition = "ENUM('Disponible', 'Adoptado', 'En proceso')")
	private String estadoAdopcion;

	@Column(name = "activo")
	private Boolean activo;

	@Column(name = "fechaPublicacion")
	private LocalDateTime fechaPublicacion;

	/* --- JSON para Android (POST y GET) --- */

	@JsonProperty("idusuarioDonador")
	public void setIdusuarioDonador(Usuario donador) {
		this.usuarioDonador = donador;
	}

	@JsonIgnore
	public Usuario getUsuarioDonador() {
		return usuarioDonador;
	}

	/** Mismo nombre que columna SQL idUsuarioDonador (entero en JSON). */
	@JsonProperty("idUsuarioDonador")
	public Integer getIdUsuarioDonador() {
		return usuarioDonador != null ? usuarioDonador.getIdUsuario() : null;
	}

	@JsonProperty("idusuarioDonador")
	public UsuarioDonadorRef getIdusuarioDonadorRef() {
		if (usuarioDonador == null || usuarioDonador.getIdUsuario() == null) {
			return null;
		}
		UsuarioDonadorRef ref = new UsuarioDonadorRef();
		ref.setIdUsuario(usuarioDonador.getIdUsuario());
		return ref;
	}

	@Data
	public static class UsuarioDonadorRef implements Serializable {
		private static final long serialVersionUID = 1L;
		private Integer idUsuario;
	}
}
