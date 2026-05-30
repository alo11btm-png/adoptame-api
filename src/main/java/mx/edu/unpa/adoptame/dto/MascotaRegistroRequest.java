package mx.edu.unpa.adoptame.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import mx.edu.unpa.adoptame.domain.Mascota;
import mx.edu.unpa.adoptame.domain.Usuario;

@Data
public class MascotaRegistroRequest {

	private String nombre;
	private String tipo;
	private String raza;
	private String sexo;
	private String estadoAdopcion;
	private Boolean activo;

	@JsonProperty("idusuarioDonador")
	@JsonAlias("idUsuarioDonador")
	private UsuarioDonadorRef idusuarioDonador;

	@Data
	public static class UsuarioDonadorRef {
		private Integer idUsuario;
	}

	public Mascota toEntity() {
		Mascota mascota = new Mascota();
		mascota.setNombre(nombre);
		mascota.setTipo(tipo);
		mascota.setRaza(raza);
		mascota.setSexo(sexo);
		mascota.setEstadoAdopcion(estadoAdopcion);
		mascota.setActivo(activo);
		if (idusuarioDonador != null && idusuarioDonador.getIdUsuario() != null) {
			Usuario donador = new Usuario();
			donador.setIdUsuario(idusuarioDonador.getIdUsuario());
			mascota.setIdusuarioDonador(donador);
		}
		return mascota;
	}
}
