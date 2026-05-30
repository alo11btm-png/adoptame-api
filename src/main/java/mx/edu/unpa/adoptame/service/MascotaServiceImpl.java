package mx.edu.unpa.adoptame.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.unpa.adoptame.domain.Mascota;
import mx.edu.unpa.adoptame.domain.Usuario;
import mx.edu.unpa.adoptame.repository.MascotaRepository;
import mx.edu.unpa.adoptame.repository.UsuarioRepository;


@Service
public class MascotaServiceImpl implements MascotaService{

	private static final Logger log = LoggerFactory.getLogger(MascotaServiceImpl.class);

	@Autowired
	private MascotaRepository mascotaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Mascota> findAll() {
		return mascotaRepository.findAllWithDonador();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Mascota> findById(Integer id) {
		return mascotaRepository.findByIdWithDetails(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Mascota> findByName(String nombre) {
		// TODO Auto-generated method stub
		return mascotaRepository.findByNombreIgnoreCase(nombre.trim());
	}

	@Override
	@Transactional
	public Mascota save(Mascota mascota) {
		if (mascota.getUsuarioDonador() != null && mascota.getUsuarioDonador().getIdUsuario() != null) {
			Integer userId = mascota.getUsuarioDonador().getIdUsuario();
			Usuario donador = usuarioRepository.findById(userId).orElseThrow(() -> {
				log.warn("Usuario donador no existe en BD: idUsuario={}", userId);
				return new IllegalArgumentException(
						"Usuario donador no encontrado: " + userId + ". Cierra sesión y vuelve a entrar.");
			});
			mascota.setUsuarioDonador(donador);
		} else {
			log.warn("POST /mascota sin idusuarioDonador en el JSON");
			throw new IllegalArgumentException("Debe enviar idusuarioDonador con idUsuario");
		}
		if (mascota.getFechaPublicacion() == null) {
			mascota.setFechaPublicacion(LocalDateTime.now());
		}
		if (mascota.getActivo() == null) {
			mascota.setActivo(true);
		}
		if (mascota.getEstadoAdopcion() == null || mascota.getEstadoAdopcion().isBlank()) {
			mascota.setEstadoAdopcion("Disponible");
		}
		return mascotaRepository.save(mascota);
	}

	@Override
	@Transactional
	public Mascota updateEstadoAdopcion(Integer idMascota, String estadoAdopcion) {
		if (estadoAdopcion == null || estadoAdopcion.isBlank()) {
			throw new IllegalArgumentException("Debe enviar estadoAdopcion");
		}
		String normalized = normalizeEstado(estadoAdopcion);
		Mascota mascota = mascotaRepository.findById(idMascota).orElseThrow(() -> {
			log.warn("Mascota no encontrada: idMascota={}", idMascota);
			return new IllegalArgumentException("Mascota no encontrada: " + idMascota);
		});
		mascota.setEstadoAdopcion(normalized);
		return mascotaRepository.save(mascota);
	}

	private String normalizeEstado(String estado) {
		String trimmed = estado.trim();
		if (trimmed.equalsIgnoreCase("En adopción") || trimmed.equalsIgnoreCase("En adopcion")) {
			return "En proceso";
		}
		if ("Disponible".equalsIgnoreCase(trimmed)
				|| "Adoptado".equalsIgnoreCase(trimmed)
				|| "En proceso".equalsIgnoreCase(trimmed)) {
			return switch (trimmed.toLowerCase()) {
				case "disponible" -> "Disponible";
				case "adoptado" -> "Adoptado";
				default -> "En proceso";
			};
		}
		throw new IllegalArgumentException(
				"Estado no válido. Use: Disponible, En proceso o Adoptado");
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		 mascotaRepository.deleteById(id);

	}
}
