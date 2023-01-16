package de.hscoburg.modulhandbuchbackend.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.hscoburg.modulhandbuchbackend.dto.FileInfoDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualDocumentController {
	private final ModuleManualRepository moduleManualRepository;
	private final DocumentService documentService;
	private final ServletContext servletContext;

	@GetMapping("/module-plan")
	public FileInfoDTO oneModulePlan(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));
		
		try {
			return this.documentService.getDocumentInfo(result.getModulePlanLink());
		} catch (IOException e) {
			// TODO own exception and advice, better handling of exception
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/preliminary-note")
	public FileInfoDTO onePreliminaryNote(@PathVariable Integer id) {
		ModuleManualEntity result = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

			try {
				return this.documentService.getDocumentInfo(result.getPreliminaryNoteLink());
			} catch (IOException e) {
				// TODO own exception and advice, better handling of exception
				throw new RuntimeException(e);
			}
	}

	// TODO move to other controller
	// @PutMapping(path = "/first-page", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// FileInfoDTO replaceFirstPage(@RequestPart MultipartFile firstPage, @PathVariable Integer id) throws IOException {
	// 	Set<MediaType> allowedContentTypes = Set.of(
	// 		MediaType.valueOf("image/*")
	// 	);

	// 	this.documentService.validateContentType(firstPage, allowedContentTypes);

	// 	ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
	// 		// TODO own exception and advice
	// 		.orElseThrow(() -> new RuntimeException(String.format("Id %d for module manual not found.", id)));

	// 	Path relativePath = Path.of("documents", "module-manuals", id.toString(), "first-page", firstPage.getOriginalFilename()).normalize();
	// 	this.documentService.saveDocument(firstPage, relativePath);

	// 	moduleManual.setFirstPageLink(relativePath.toString());
	// 	this.moduleManualRepository.save(moduleManual);

	// 	return this.documentService.getDocumentInfoFromPath(relativePath);
	// }

	@PutMapping(path = "/module-plan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FileInfoDTO replaceModulePlan(@PathVariable Integer id, @RequestPart MultipartFile modulePlanFile) {
		Set<MediaType> allowedContentTypes = Set.of(
			MediaType.APPLICATION_PDF
		);

		this.documentService.validateContentType(modulePlanFile, allowedContentTypes);

		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		Path relativePath = Path.of("documents", "module-manuals", id.toString(), "module-plan", modulePlanFile.getOriginalFilename()).normalize();
		
		try {
			this.documentService.saveDocument(modulePlanFile, relativePath);

			moduleManual.setModulePlanLink(relativePath.toString());
			this.moduleManualRepository.save(moduleManual);

			return this.documentService.getDocumentInfoFromPath(relativePath);
		} catch (IOException e) {
			// TODO own exception and advice, better handling of exception
			throw new RuntimeException(e);
		}
	}

	@PutMapping(path = "/preliminary-note", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FileInfoDTO replacePreliminaryNote(@PathVariable Integer id, @RequestPart MultipartFile preliminaryNoteFile) {
		Set<MediaType> allowedContentTypes = Set.of(
			MediaType.valueOf("text/*")
		);

		this.documentService.validateContentType(preliminaryNoteFile, allowedContentTypes);

		ModuleManualEntity moduleManual = this.moduleManualRepository.findById(id)
			.orElseThrow(() -> new ModuleManualNotFoundException(id));

		Path relativePath = Path.of("documents", "module-manuals", id.toString(), "preliminary-note", preliminaryNoteFile.getOriginalFilename()).normalize();
		
		try {
			this.documentService.saveDocument(preliminaryNoteFile, relativePath);

			moduleManual.setPreliminaryNoteLink(relativePath.toString());
			this.moduleManualRepository.save(moduleManual);

			return this.documentService.getDocumentInfoFromPath(relativePath);
		} catch (IOException e) {
			// TODO own exception and advice, better handling of exception
			throw new RuntimeException(e);
		}
	}
}
