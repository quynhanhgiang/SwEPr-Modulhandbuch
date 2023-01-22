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

/**
 * This class is a REST controller that handles requests for uploading and downloading documents associated with a module manual.
 */
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/module-manuals/{id}")
public class ModuleManualDocumentController {
	private final ModuleManualRepository moduleManualRepository;
	private final DocumentService documentService;
	private final ServletContext servletContext;

	/**
	 * This method handles GET requests to the `/module-manuals/{id}/module-plan` endpoint where id is variable integer.
	 * It then uses the id and the file name to find the mapped file. If it finds one, it returns it as a
	 * {@link FileInfoDTO}. If it does not find one, it throws a {@link ModuleManualNotFoundException} if the module manual for the given id could not be found or an {@link IOException} wrapped in a {@link RuntimeException} if the module manual is present but there is a problem regarding the document.
	 * 
	 * @param id The id of the module manual associated with the file.
	 * @return A {@link FileInfoDTO} with informations to the file.
	 */
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

	/**
	 * This method handles GET requests to the `/module-manuals/{id}/preliminary-note` endpoint where id is variable integer.
	 * It then uses the id and the file name to find the mapped file. If it finds one, it returns it as a
	 * {@link FileInfoDTO}. If it does not find one, it throws a {@link ModuleManualNotFoundException} if the module manual for the given id could not be found or an {@link IOException} wrapped in a {@link RuntimeException} if the module manual is present but there is a problem regarding the document.
	 * 
	 * @param id The id of the module manual associated with the file.
	 * @return A {@link FileInfoDTO} with informations to the file.
	 */
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

	/**
	 * This method handles PUT requests to the `/module-manuals/{id}/module-plan` endpoint where id is a variable integer. The file in the request body replaces the module plan of a module manual.
	 * If no module manual with the given id is found, a {@link ModuleManualNotFoundException} is thrown.
	 * If the file could not be updated an {@link IOException} wrapped in a {@link RuntimeException} is thrown.
	 * 
	 * @param id The id of the module manual to replace the module plan for.
	 * @param modulePlanFile The file that is being uploaded.
	 * @return A {@link FileInfoDTO} with informations to the file.
	 */
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

	/**
	 * This method handles PUT requests to the `/module-manuals/{id}/preliminary-note` endpoint where id is a variable integer. The file in the request body replaces the preliminary note of a module manual.
	 * If no module manual with the given id is found, a {@link ModuleManualNotFoundException} is thrown.
	 * If the file could not be updated an {@link IOException} wrapped in a {@link RuntimeException} is thrown.
	 * 
	 * @param id The id of the module manual to replace the preliminary note for.
	 * @param preliminaryNoteFile The file that is being uploaded.
	 * @return A {@link FileInfoDTO} with informations to the file.
	 */
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
