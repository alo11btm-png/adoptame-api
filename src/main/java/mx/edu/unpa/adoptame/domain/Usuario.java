package mx.edu.unpa.adoptame.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad alineada con tabla Usuario (adoptame.sql).
 */
@Data
@Entity
@Table(name = "Usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Integer idUsuario;

	@OneToMany(mappedBy = "usuarioDonador", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Mascota> mascotas;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "apellidoPaterno", nullable = false, length = 100)
	private String apellidoPaterno;

	@Column(name = "apellidoMaterno", length = 100)
	private String apellidoMaterno;

	@Column(name = "email", nullable = false, unique = true, length = 150)
	private String email;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "telefono", length = 20)
	private String telefono;

	@Column(name = "activo")
	private boolean activo;

	@Column(name = "fechaRegistro", insertable = false, updatable = false)
	private LocalDateTime fechaRegistro;
}
