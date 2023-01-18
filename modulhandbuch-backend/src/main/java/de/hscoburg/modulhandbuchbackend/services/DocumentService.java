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

@Data
@Service
public class DocumentService {
	@Getter(value = AccessLevel.NONE)
	private final ModuloProperties moduloProperties;

	public FileInfoDTO getDocumentInfo(String link) throws IOException {
		if (link == null) {
			return new FileInfoDTO();
		}

		Path relativePath = Path.of(link).normalize();
		return this.getDocumentInfoFromPath(relativePath);
	}
	
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

	public void saveDocument(MultipartFile file, Path relativePath) throws IOException {
		Path filePath = this.getFilesDirectory().resolve(relativePath).normalize();

		Files.createDirectories(filePath.getParent());
		FileUtils.cleanDirectory(filePath.getParent().toFile());
		file.transferTo(filePath);
	}

	public UriComponentsBuilder getLinkToDocumentBuilder() {
		return ServletUriComponentsBuilder.fromCurrentContextPath();
	}

	public Path getFilesDirectory() {
		return Path.of(this.moduloProperties.getFilesDirectory()).normalize();
	}
}
