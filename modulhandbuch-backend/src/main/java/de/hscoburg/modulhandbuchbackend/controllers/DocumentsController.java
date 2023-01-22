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

/**
 * This class is a REST controller that handles requests sent to the `/documents` endpoint.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentsController {
	private final DocumentService documentService;

	/**
	 * This method handles GET requests to the `/documents/module-manuals/{id}/{document}/{fileName}` endpoint where id is a variable integer and document and fileName are variable Strings.
	 * It then uses the given arguments to construct a path where the requested file should be stored. If it does not find one, it will return a 404 status code. Otherwise the file is returned as a downloadable resource.
	 * 
	 * @param id The id of the module manual.
	 * @param document The name of the document (e.g. "module-plan" or "preliminary-note").
	 * @param fileName The name of the file to be downloaded.
	 * @return A ResponseEntity object with the requested file or with a status code of 404 if the file could not be found.
	 */
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
