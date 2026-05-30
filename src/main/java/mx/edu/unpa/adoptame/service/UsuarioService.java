package mx.edu.unpa.adoptame.service;

import java.util.Optional;

import mx.edu.unpa.adoptame.domain.Usuario;


public interface UsuarioService {
	public Iterable<Usuario> findAll();
	// public Page<Usuario> findAll(Pageable pageable);
	public Optional<Usuario> findById(Integer id);
	public Optional<Usuario> findByEmail(String email);
	public Usuario save(Usuario usuario);
	public void deleteById(Integer id);
}
