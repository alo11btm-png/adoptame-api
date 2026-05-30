package mx.edu.unpa.adoptame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import mx.edu.unpa.adoptame.domain.ImagenMascota;

public interface ImagenMascotaRepository extends JpaRepository<ImagenMascota, Integer>{
	Optional<ImagenMascota> findByUrlImagen(String urlImagen);
	List<ImagenMascota> findByMascota_IdMascotaOrderByImagenPrincipalDescIdImagenAsc(Integer idMascota);

	long countByMascota_IdMascota(Integer idMascota);
}
