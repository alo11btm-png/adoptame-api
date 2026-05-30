package mx.edu.unpa.adoptame.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.unpa.adoptame.domain.Usuario;
import mx.edu.unpa.adoptame.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Usuario> findAll() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> findById(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail(email);
	}
	
	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		String email = usuario.getEmail();
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("El correo es obligatorio");
		}
		String normalized = email.trim().toLowerCase();
		if (!isGmailEmail(normalized)) {
			throw new IllegalArgumentException("Solo se permiten correos @gmail.com");
		}
		usuario.setEmail(normalized);

		if (usuario.getIdUsuario() == null && usuario.getFechaRegistro() == null) {
			usuario.setFechaRegistro(LocalDateTime.now());
		}
		return usuarioRepository.save(usuario);
	}

	private boolean isGmailEmail(String email) {
		final String suffix = "@gmail.com";
		if (!email.endsWith(suffix)) {
			return false;
		}
		String localPart = email.substring(0, email.length() - suffix.length());
		return !localPart.isBlank() && !localPart.contains("@") && email.contains("@");
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		usuarioRepository.deleteById(id);
	}
}
