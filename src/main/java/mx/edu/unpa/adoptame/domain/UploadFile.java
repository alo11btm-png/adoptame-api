package mx.edu.unpa.adoptame.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String ruta;
	private String tipo;
	private long size;
	

	
}
