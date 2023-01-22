package de.hscoburg.modulhandbuchbackend.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import de.hscoburg.modulhandbuchbackend.dto.FileInfoDTO;
import de.hscoburg.modulhandbuchbackend.properties.ModuloProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * This class is a service for saving and retrieving files.
 */
@Data
@Service
public class DocumentService {
	@Getter(value = AccessLevel.NONE)
	private final ModuloProperties moduloProperties;

	/**
	 * This method returns a {@link FileInfoDTO} object containing
	 * the file name, the link to the file and the timestamp of the creation time of
	 * the file.
	 * 
	 * @param link The relative path to the file.
	 * @return A {@link FileInfoDTO} object.
	 */
	public FileInfoDTO getDocumentInfo(String link) throws IOException {
		if (link == null) {
			return new FileInfoDTO();
		}

		Path relativePath = Path.of(link).normalize();
		return this.getDocumentInfoFromPath(relativePath);
	}

	/**
	 * This method returns a {@link FileInfoDTO} object containing
	 * the file name, the link to the file and the timestamp of the creation time of
	 * the file.
	 * 
	 * @param relativePathToDocument The path to the file relative to the files
	 *                               directory.
	 * @return A {@link FileInfoDTO} object.
	 */
	public FileInfoDTO getDocumentInfoFromPath(Path relativePathToDocument) throws IOException {
		Path documentsDirectory = this.getFilesDirectory();
		Path fullPath = documentsDirectory.resolve(relativePathToDocument);

		if (!fullPath.toFile().exists()) {
			return new FileInfoDTO();
		}

		if (fullPath.toFile().isDirectory()) {
			return new FileInfoDTO();
		}

		String fileName = FilenameUtils.getName(fullPath.toString());
		String timestamp = Files.readAttributes(fullPath, BasicFileAttributes.class).creationTime().toString();

		UriComponentsBuilder linkToDocumentBuilder = this.getLinkToDocumentBuilder();
		relativePathToDocument.iterator().forEachRemaining(path -> linkToDocumentBuilder.pathSegment(path.toString()));
		URI linkToDocument = linkToDocumentBuilder.build().toUri();

		FileInfoDTO fileSavedDTO = new FileInfoDTO();
		fileSavedDTO.setFilename(fileName);
		fileSavedDTO.setLink(linkToDocument.toString());
		fileSavedDTO.setTimestamp(timestamp);
		return fileSavedDTO;
	}

	/**
	 * This method validates that the content type of the passed file is in the set
	 * of allowed content types. Otherwise throw a
	 * {@link UnsupportedMediaTypeStatusException}.
	 * 
	 * @param file                The file for validating the content type.
	 * @param allowedContentTypes A set of allowed content types.
	 */
	public void validateContentType(MultipartFile file, Set<MediaType> allowedContentTypes) {
		String contentTypeValue = file.getContentType();
		if (contentTypeValue == null) {
			throw new UnsupportedMediaTypeStatusException("The mediaType of the file could not be read.");
		}

		MediaType contentType = MediaType.valueOf(contentTypeValue);
		if (allowedContentTypes.stream().noneMatch(mediaType -> mediaType.includes(contentType))) {
			throw new UnsupportedMediaTypeStatusException(contentType, new ArrayList<>(allowedContentTypes));
		}
	}

	/**
	 * This method is used to save a file at the given path relative to the files
	 * directory.
	 * 
	 * @param file         The file to save.
	 * @param relativePath The path to the file relative to the files directory.
	 */
	public void saveDocument(MultipartFile file, Path relativePath) throws IOException {
		Path filePath = this.getFilesDirectory().resolve(relativePath).normalize();

		Files.createDirectories(filePath.getParent());
		FileUtils.cleanDirectory(filePath.getParent().toFile());
		file.transferTo(filePath);
	}

	/**
	 * It returns a UriComponentsBuilder object that is initialized with the current
	 * context path.
	 * 
	 * @return A UriComponentsBuilder.
	 */
	public UriComponentsBuilder getLinkToDocumentBuilder() {
		return ServletUriComponentsBuilder.fromCurrentContextPath();
	}

	/**
	 * This method returns the path to the directory where files are stored.
	 * 
	 * @return The path of the files directory.
	 */
	public Path getFilesDirectory() {
		return Path.of(this.moduloProperties.getFilesDirectory()).normalize();
	}
}
