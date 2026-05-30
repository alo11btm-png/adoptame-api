package mx.edu.unpa.adoptame.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.unpa.adoptame.domain.ImagenMascota;
import mx.edu.unpa.adoptame.domain.Mascota;
import mx.edu.unpa.adoptame.service.ImagenMascotaService;
import mx.edu.unpa.adoptame.service.MascotaService;

@RestController
@RequestMapping("/imagenMascota")
public class ImagenMascotaController {

	@Autowired
	private ImagenMascotaService imagenMascotaService;

	@Autowired
	private MascotaService mascotaService;

	@GetMapping
	public Iterable<ImagenMascota> queryAll() {
		return imagenMascotaService.findAll();
	}

	@GetMapping("/mascota/{idMascota}")
	public ResponseEntity<java.util.List<ImagenMascota>> queryByMascota(
			@PathVariable("idMascota") Integer idMascota) {
		Optional<Mascota> oMascota = mascotaService.findById(idMascota);
		if (oMascota.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(imagenMascotaService.findByMascotaId(idMascota));
	}

	@PostMapping
	public ResponseEntity<ImagenMascota> insert(@RequestBody Map<String, Object> body) {
		Object idObj = body.get("idMascota");
		Integer idMascota = idObj instanceof Number ? ((Number) idObj).intValue() : null;
		String urlImagen = (String) body.get("urlImagen");
		Boolean imagenPrincipal = body.get("imagenPrincipal") != null
				? (Boolean) body.get("imagenPrincipal")
				: true;

		if (idMascota == null || urlImagen == null || urlImagen.isBlank()) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Mascota> oMascota = mascotaService.findById(idMascota);
		if (oMascota.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		ImagenMascota imagen = new ImagenMascota();
		imagen.setMascota(oMascota.get());
		imagen.setUrlImagen(urlImagen);
		imagen.setImagenPrincipal(imagenPrincipal);

		return ResponseEntity.status(HttpStatus.CREATED).body(imagenMascotaService.save(imagen));
	}
}
