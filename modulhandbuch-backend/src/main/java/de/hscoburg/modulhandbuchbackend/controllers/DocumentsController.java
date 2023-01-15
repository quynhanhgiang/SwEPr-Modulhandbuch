package de.hscoburg.modulhandbuchbackend.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hscoburg.modulhandbuchbackend.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentsController {
	private final DocumentService documentService;
	
	@GetMapping("/module-manuals/{id}/{document}/{fileName}")
	public ResponseEntity<Resource> downloadDocument(@PathVariable Integer id, @PathVariable String document, @PathVariable String fileName) throws MalformedURLException, IOException {
		Path path = this.documentService.getFilesDirectory().resolve(Path.of("documents", "module-manuals", id.toString(), document, fileName));

		Resource resource = new UrlResource(path.toUri());
		if (resource == null || !resource.isReadable()) {
			return ResponseEntity.notFound().build();
		}

		MediaType mimeType = MediaType.parseMediaType(URLConnection.guessContentTypeFromName(resource.getFilename()));

		return ResponseEntity.ok()
			// download file
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
			// embed file in page
			// .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
			.contentLength(resource.contentLength())
			.contentType(mimeType)
			.body(resource);
	}
}
