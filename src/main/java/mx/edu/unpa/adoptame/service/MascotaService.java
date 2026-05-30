package mx.edu.unpa.adoptame.service;

import java.util.Optional;

import mx.edu.unpa.adoptame.domain.Mascota;


public interface MascotaService {
	
		public Iterable<Mascota> findAll();
		public Optional<Mascota> findById(Integer id);
		public Optional<Mascota> findByName(String nombre);
		public Mascota save(Mascota mascota);
		public Mascota updateEstadoAdopcion(Integer idMascota, String estadoAdopcion);
		public void deleteById(Integer id);
	}


