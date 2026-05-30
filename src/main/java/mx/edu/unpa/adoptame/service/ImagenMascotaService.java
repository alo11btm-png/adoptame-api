package mx.edu.unpa.adoptame.service;

import java.util.List;
import java.util.Optional;

import mx.edu.unpa.adoptame.domain.ImagenMascota;

public interface ImagenMascotaService {

	Iterable<ImagenMascota> findAll();

	Optional<ImagenMascota> findById(Integer id);

	Optional<ImagenMascota> findByUrlImagen(String urlImagen);

	List<ImagenMascota> findByMascotaId(Integer idMascota);

	ImagenMascota save(ImagenMascota imagenMascota);

	void deleteById(Integer id);
}

