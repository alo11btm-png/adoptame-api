package mx.edu.unpa.adoptame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.unpa.adoptame.domain.ImagenMascota;
import mx.edu.unpa.adoptame.repository.ImagenMascotaRepository;

@Service
public class ImagenMascotaServiceImpl implements ImagenMascotaService{		
	@Autowired
	private ImagenMascotaRepository imagenmascotaRepository;

	@Override
	@Transactional(readOnly=true)
	public Iterable<ImagenMascota> findAll() {
		// TODO Auto-generated method stub
		return imagenmascotaRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<ImagenMascota> findById(Integer id) {
		// TODO Auto-generated method stub
		return imagenmascotaRepository.findById(id);
	}



	@Override
	@Transactional(readOnly = true)
	public List<ImagenMascota> findByMascotaId(Integer idMascota) {
		return imagenmascotaRepository.findByMascota_IdMascotaOrderByImagenPrincipalDescIdImagenAsc(idMascota);
	}

	@Override
	@Transactional
	public ImagenMascota save(ImagenMascota imagenMascota) {
		if (Boolean.TRUE.equals(imagenMascota.getImagenPrincipal())
				&& imagenMascota.getMascota() != null
				&& imagenMascota.getMascota().getIdMascota() != null) {
			Integer idMascota = imagenMascota.getMascota().getIdMascota();
			for (ImagenMascota existente : findByMascotaId(idMascota)) {
				if (imagenMascota.getIdImagen() != null
						&& imagenMascota.getIdImagen().equals(existente.getIdImagen())) {
					continue;
				}
				existente.setImagenPrincipal(false);
				imagenmascotaRepository.save(existente);
			}
		}
		return imagenmascotaRepository.save(imagenMascota);
	}

	@Override
	@Transactional(readOnly=true)
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		imagenmascotaRepository.deleteById(id);

	}

	@Override
	public Optional<ImagenMascota> findByUrlImagen(String urlImagen) {
		// TODO Auto-generated method stub
		return imagenmascotaRepository.findByUrlImagen(urlImagen);
	}

}
