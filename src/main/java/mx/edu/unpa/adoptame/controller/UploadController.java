package mx.edu.unpa.adoptame.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mx.edu.unpa.adoptame.domain.UploadFile;

@RestController
@RequestMapping("/api")
public class UploadController {

	@PostMapping("/upload")
	public ResponseEntity<UploadFile> upload(
			@RequestParam(name = "file") MultipartFile file,
			@RequestParam(name = "carpeta", defaultValue = "mascotas") String carpeta)
			throws IllegalStateException, IOException {

		String ruta = System.getProperty("user.dir") + "/src/main/resources/static/images/" + carpeta + "/";

		File directorio = new File(ruta);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}

		String nombreOriginal = file.getOriginalFilename();
		String extension = nombreOriginal != null && nombreOriginal.contains(".")
				? nombreOriginal.substring(nombreOriginal.lastIndexOf("."))
				: ".jpg";

		String nombreFoto = "Foto-" + System.currentTimeMillis() + extension;
		File archivo = new File(ruta + nombreFoto);
		file.transferTo(archivo);

		String rutaRelativa = "images/" + carpeta + "/" + nombreFoto;
		return ResponseEntity.ok(
				new UploadFile(nombreFoto, rutaRelativa, file.getContentType(), file.getSize()));
	}
}
