package mx.edu.unpa.adoptame.controller;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import jakarta.servlet.http.HttpServletRequest;
import mx.edu.unpa.adoptame.domain.Usuario;
import mx.edu.unpa.adoptame.service.UsuarioService;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	// Get all users: http://localhost:8181/usuario
	@GetMapping
	public Iterable<Usuario> queryAll() {
	    return usuarioService.findAll();
	}
	
		// Return the list in JSON format: HTTP 200 OK
	    
	
	
	// Get a user by email: http://localhost:8181/usuario/email?email=isc.josed2@gmail.com
	@GetMapping("/email")
    public ResponseEntity<Usuario> queryByEmail(@RequestParam(value = "email") String email) {
        Optional<Usuario> oUsuario = usuarioService.findByEmail(email);
        
        if (oUsuario.isPresent()) {
            return ResponseEntity.ok(oUsuario.get()); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }

	// Get a user by id: http://localhost:8181/usuario/1
	@GetMapping("/{idUsuario:\\d+}")
    public ResponseEntity<Usuario> queryById(@PathVariable("idUsuario") Integer idUsuario) {
        Optional<Usuario> oUsuario = usuarioService.findById(idUsuario);
        if (oUsuario.isPresent()) {
            return ResponseEntity.ok(oUsuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }
		
	// Insert a user: http://localhost:8181/usuario
	/*
		{
		  "nombre": "Pedro",
		  "apellidoPaterno": "Lopez",
		  "apellidoMaterno": "Garcia",
		  "email": "pedro@test.com",
		  "telefono": "123456789",
		  "password": "1234",
		  "activo": true
		}
	*/
	@PostMapping
    public ResponseEntity<?> insert(@RequestBody Usuario usuario) {
		try {
			Usuario savedUsuario = usuarioService.save(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
		}
    }
		
	// Update a user:http://localhost:8181/usuario/1
	/*
		{
		  "nombre": "Juan Actualizado",
		  "apellidoPaterno": "Perez",
		  "apellidoMaterno": "Lopez",
		  "email": "juan@test.com",
		  "telefono": "999999999",
		  "password": "1234",
		  "activo": true
		}
	*/
	@PutMapping("/{idUsuario}")
    public ResponseEntity<?> update(@PathVariable("idUsuario") Integer idUsuario, @RequestBody Usuario usuario) {
		Optional<Usuario> oUsuario = usuarioService.findById(idUsuario);

        if (oUsuario.isPresent()) {
        	Usuario existing = oUsuario.get();
        	usuario.setIdUsuario(idUsuario);
        	if (usuario.getFechaRegistro() == null) {
        		usuario.setFechaRegistro(
        			existing.getFechaRegistro() != null
        				? existing.getFechaRegistro()
        				: LocalDateTime.now()
        		);
        	}
        	if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
        		usuario.setPassword(existing.getPassword());
        	}
        	try {
        		Usuario updated = usuarioService.save(usuario);
        		return ResponseEntity.ok(updated);
        	} catch (IllegalArgumentException ex) {
        		return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
        	}
        }
        
        return ResponseEntity.notFound().build(); 		 
    }
	
	// Delete a user by id: http://localhost:8181/usuario/1
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> delete(@PathVariable("idUsuario") Integer idUsuario) {
        Optional<Usuario> oUsuario = usuarioService.findById(idUsuario);

        if (oUsuario.isPresent()) {
        	usuarioService.deleteById(idUsuario);
        	
            // If deletion is successful: HTTP 204
            return ResponseEntity.noContent().build();
        } else {
        	// If the student ID does not exist: HTTP 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
