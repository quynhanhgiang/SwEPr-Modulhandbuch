package de.hscoburg.modulhandbuchbackend.unittests.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import de.hscoburg.modulhandbuchbackend.controllers.DocumentsController;
import de.hscoburg.modulhandbuchbackend.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class DocumentsControllerTest {

	@Mock
	private DocumentService mockDocumentService;

	@InjectMocks
	private DocumentsController documentsControllerWithMocks;

	private Path pathBase;
	private Path existingDirectories;

	@BeforeEach
	public void setup() {
		// set path base
		try (InputStream input = this.getClass().getResourceAsStream("/configs/application-test.properties")) {
			Properties prop = new Properties();
			
			prop.load(input);

			this.pathBase = Path.of(prop.getProperty("modulo.files-directory")).toAbsolutePath();
		} catch (Throwable t) {
			t.printStackTrace();
		}

		Assertions.assertNotNull(this.pathBase);

		// save existing directories of pathBase
		Path pathToTestForExistence = this.pathBase.getRoot();

		for (Iterator<Path> pathBaseIterator = this.pathBase.iterator(); pathBaseIterator.hasNext();) {
			Path nextDirectory = pathBaseIterator.next();
			if (!pathToTestForExistence.resolve(nextDirectory).toFile().exists()) {
				break;
			}

			pathToTestForExistence = pathToTestForExistence.resolve(nextDirectory);
		}
		this.existingDirectories = pathToTestForExistence;

		// create nonexistend directories
		Assertions.assertDoesNotThrow(() -> Files.createDirectories(this.pathBase));

		// check if pathBase is empty directory otherwise unintentional file deletion could happen
		boolean pathBaseEmpty = Assertions.assertDoesNotThrow(() -> FileUtils.isEmptyDirectory(this.pathBase.toFile()));
		Assertions.assertTrue(pathBaseEmpty, this.pathBase.toString());

		// copy documents to correct directory
		Assertions.assertDoesNotThrow(() -> FileUtils.copyDirectory(Path.of(this.getClass().getResource("/documents").toURI()).toFile(), this.pathBase.resolve("documents").toFile()));
	}

	@AfterEach
	public void finish() {
		// delete previously not created directories of pathBase
		Path directoriesToDelete = this.existingDirectories.relativize(this.pathBase);
		if (directoriesToDelete.compareTo(Path.of("")) != 0) {
			Path rootDirectoryToDelete = directoriesToDelete.iterator().next();
			Path rootDirectoryToDeleteAbs = this.existingDirectories.resolve(rootDirectoryToDelete);
			Assertions.assertDoesNotThrow(() -> FileUtils.deleteDirectory(rootDirectoryToDeleteAbs.toFile()), String.format("Previous existing directories: %s%sCreated directories: %s", this.existingDirectories.toString(), System.lineSeparator(), directoriesToDelete));
		} else {
			Assertions.assertDoesNotThrow(() -> FileUtils.cleanDirectory(this.existingDirectories.toFile()), String.format("Could not delete contents of: %s", this.existingDirectories.toString()));
		}
	}

	@Test
	public void testDownloadDocument() throws Exception {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			Integer id;
			String document;
			String fileName;
			Path pathToFile;
			@EqualsAndHashCode.Exclude
			HttpStatus expectedHttpStatus;
			@EqualsAndHashCode.Exclude
			MediaType expectedContentType;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
		}

		Set<TestParameters> testData = Set.of(
			// id 0
			new TestParameters(0, "module-plan", "test.txt", Path.of("documents/module-manuals/0/module-plan/test.txt"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(0, "module-plan", "grid.pdf", Path.of("documents/module-manuals/0/module-plan/grid.pdf"), HttpStatus.OK, MediaType.APPLICATION_PDF, false, null, null),
			new TestParameters(0, "module-plan", "Logistic-curve.svg", Path.of("documents/module-manuals/0/module-plan/Logistic-curve.svg"), HttpStatus.OK, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(0, "module-plan", "something.json", Path.of("documents/module-manuals/0/module-plan/something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(0, "module-plan", "unknown.file-extension", Path.of("documents/module-manuals/0/module-plan/octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(0, "module-plan", "", Path.of("documents/module-manuals/0/module-plan/"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(0, "module-plan", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(0, "preliminary-note", "test.txt", Path.of("documents/module-manuals/0/preliminary-note/test.txt"), HttpStatus.OK, MediaType.TEXT_PLAIN, false, null, null),
			new TestParameters(0, "preliminary-note", "grid.pdf", Path.of("documents/module-manuals/0/preliminary-note/grid.pdf"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(0, "preliminary-note", "Logistic-curve.svg", Path.of("documents/module-manuals/0/preliminary-note/Logistic-curve.svg"), HttpStatus.OK, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(0, "preliminary-note", "something.json", Path.of("documents/module-manuals/0/preliminary-note/something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(0, "preliminary-note", "unknown.file-extension", Path.of("documents/module-manuals/0/preliminary-note/octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(0, "preliminary-note", "", Path.of("documents/module-manuals/0/preliminary-note/"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(0, "preliminary-note", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			
			new TestParameters(0, "not-available", "test.txt", Path.of("documents/module-manuals/0/not-available/test.txt"), HttpStatus.NOT_FOUND, MediaType.TEXT_PLAIN, false, null, null),
			new TestParameters(0, "not-available", "grid.pdf", Path.of("documents/module-manuals/0/not-available/grid.pdf"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_PDF, false, null, null),
			new TestParameters(0, "not-available", "Logistic-curve.svg", Path.of("documents/module-manuals/0/not-available/Logistic-curve.svg"), HttpStatus.NOT_FOUND, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(0, "not-available", "something.json", Path.of("documents/module-manuals/0/not-available/something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(0, "not-available", "unknown.file-extension", Path.of("documents/module-manuals/0/not-available/octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(0, "not-available", "", Path.of("documents/module-manuals/0/not-available/"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(0, "not-available", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(0, "", "test.txt", Path.of("documents/module-manuals/0//test.txt"), HttpStatus.NOT_FOUND, MediaType.TEXT_PLAIN, false, null, null),
			new TestParameters(0, "", "grid.pdf", Path.of("documents/module-manuals/0//grid.pdf"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_PDF, false, null, null),
			new TestParameters(0, "", "Logistic-curve.svg", Path.of("documents/module-manuals/0//Logistic-curve.svg"), HttpStatus.NOT_FOUND, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(0, "", "something.json", Path.of("documents/module-manuals/0//something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(0, "", "unknown.file-extension", Path.of("documents/module-manuals/0//octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(0, "", "", Path.of("documents/module-manuals/0//"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(0, "", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(0, null, "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(0, null, "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(0, null, "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(0, null, "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(0, null, "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(0, null, "", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(0, null, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			// id 1
			new TestParameters(1, "module-plan", "test.txt", Path.of("documents/module-manuals/1/module-plan/test.txt"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(1, "module-plan", "grid.pdf", Path.of("documents/module-manuals/1/module-plan/grid.pdf"), HttpStatus.OK, MediaType.APPLICATION_PDF, false, null, null),
			new TestParameters(1, "module-plan", "Logistic-curve.svg", Path.of("documents/module-manuals/1/module-plan/Logistic-curve.svg"), HttpStatus.OK, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(1, "module-plan", "something.json", Path.of("documents/module-manuals/1/module-plan/something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(1, "module-plan", "unknown.file-extension", Path.of("documents/module-manuals/1/module-plan/octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(1, "module-plan", "", Path.of("documents/module-manuals/1/module-plan/"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(1, "module-plan", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(1, "preliminary-note", "test.txt", Path.of("documents/module-manuals/1/preliminary-note/test.txt"), HttpStatus.OK, MediaType.TEXT_PLAIN, false, null, null),
			new TestParameters(1, "preliminary-note", "grid.pdf", Path.of("documents/module-manuals/1/preliminary-note/grid.pdf"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(1, "preliminary-note", "Logistic-curve.svg", Path.of("documents/module-manuals/1/preliminary-note/Logistic-curve.svg"), HttpStatus.OK, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(1, "preliminary-note", "something.json", Path.of("documents/module-manuals/1/preliminary-note/something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(1, "preliminary-note", "unknown.file-extension", Path.of("documents/module-manuals/1/preliminary-note/octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(1, "preliminary-note", "", Path.of("documents/module-manuals/1/preliminary-note/"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(1, "preliminary-note", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			
			new TestParameters(1, "not-available", "test.txt", Path.of("documents/module-manuals/1/not-available/test.txt"), HttpStatus.NOT_FOUND, MediaType.TEXT_PLAIN, false, null, null),
			new TestParameters(1, "not-available", "grid.pdf", Path.of("documents/module-manuals/1/not-available/grid.pdf"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_PDF, false, null, null),
			new TestParameters(1, "not-available", "Logistic-curve.svg", Path.of("documents/module-manuals/1/not-available/Logistic-curve.svg"), HttpStatus.NOT_FOUND, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(1, "not-available", "something.json", Path.of("documents/module-manuals/1/not-available/something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(1, "not-available", "unknown.file-extension", Path.of("documents/module-manuals/1/not-available/octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(1, "not-available", "", Path.of("documents/module-manuals/1/not-available/"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(1, "not-available", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(1, "", "test.txt", Path.of("documents/module-manuals/1//test.txt"), HttpStatus.NOT_FOUND, MediaType.TEXT_PLAIN, false, null, null),
			new TestParameters(1, "", "grid.pdf", Path.of("documents/module-manuals/1//grid.pdf"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_PDF, false, null, null),
			new TestParameters(1, "", "Logistic-curve.svg", Path.of("documents/module-manuals/1//Logistic-curve.svg"), HttpStatus.NOT_FOUND, MediaType.valueOf("image/svg+xml"), false, null, null),
			new TestParameters(1, "", "something.json", Path.of("documents/module-manuals/1//something.json"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON, false, null, null),
			new TestParameters(1, "", "unknown.file-extension", Path.of("documents/module-manuals/1//octed.abcd"), HttpStatus.NOT_FOUND, MediaType.APPLICATION_OCTET_STREAM, false, null, null),
			new TestParameters(1, "", "", Path.of("documents/module-manuals/1//"), HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(1, "", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(1, null, "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(1, null, "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(1, null, "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(1, null, "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(1, null, "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(1, null, "", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(1, null, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			// id 2
			new TestParameters(2, "module-plan", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "module-plan", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "module-plan", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "module-plan", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "module-plan", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "module-plan", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "module-plan", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(2, "preliminary-note", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "preliminary-note", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "preliminary-note", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "preliminary-note", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "preliminary-note", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "preliminary-note", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "preliminary-note", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			
			new TestParameters(2, "not-available", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "not-available", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "not-available", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "not-available", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "not-available", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "not-available", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "not-available", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(2, "", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(2, "", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(2, null, "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(2, null, "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(2, null, "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(2, null, "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(2, null, "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(2, null, "", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(2, null, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			// id -1
			new TestParameters(-1, "module-plan", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "module-plan", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "module-plan", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "module-plan", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "module-plan", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "module-plan", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "module-plan", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(-1, "preliminary-note", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "preliminary-note", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "preliminary-note", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "preliminary-note", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "preliminary-note", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "preliminary-note", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "preliminary-note", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			
			new TestParameters(-1, "not-available", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "not-available", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "not-available", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "not-available", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "not-available", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "not-available", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "not-available", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(-1, "", "test.txt", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "", "grid.pdf", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "", "Logistic-curve.svg", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "", "something.json", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "", "unknown.file-extension", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "", "", null, HttpStatus.NOT_FOUND, null, false, null, null),
			new TestParameters(-1, "", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			new TestParameters(-1, null, "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(-1, null, "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(-1, null, "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(-1, null, "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(-1, null, "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(-1, null, "", null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),
			new TestParameters(-1, null, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"String.isEmpty()\" because \"segment\" is null"),

			// id null
			new TestParameters(null, "module-plan", "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "module-plan", "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "module-plan", "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "module-plan", "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "module-plan", "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "module-plan", "", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "module-plan", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),

			new TestParameters(null, "preliminary-note", "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "preliminary-note", "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "preliminary-note", "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "preliminary-note", "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "preliminary-note", "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "preliminary-note", "", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "preliminary-note", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			
			new TestParameters(null, "not-available", "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "not-available", "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "not-available", "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "not-available", "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "not-available", "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "not-available", "", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "not-available", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),

			new TestParameters(null, "", "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "", "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "", "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "", "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "", "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "", "", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, "", null, null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),

			new TestParameters(null, null, "test.txt", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, null, "grid.pdf", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, null, "Logistic-curve.svg", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, null, "something.json", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, null, "unknown.file-extension", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, null, "", null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null"),
			new TestParameters(null, null, null, null, null, null, true, NullPointerException.class, "Cannot invoke \"java.lang.Integer.toString()\" because \"id\" is null")
		);

		Mockito.when(this.mockDocumentService.getFilesDirectory()).thenReturn(this.pathBase);

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.documentsControllerWithMocks.downloadDocument(testParameters.id, testParameters.document, testParameters.fileName), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());
				
				continue;
			}

			ResponseEntity<Resource> response = this.documentsControllerWithMocks.downloadDocument(testParameters.id, testParameters.document, testParameters.fileName);

			Assertions.assertEquals(testParameters.expectedHttpStatus, response.getStatusCode(), testParameters.toString());
			
			if (response.hasBody()) {
				Assertions.assertEquals(testParameters.expectedContentType, response.getHeaders().getContentType(), testParameters.toString());
				
				Path path = this.pathBase.resolve(testParameters.pathToFile);
				Resource resource = new UrlResource(path.toUri());
				Assertions.assertEquals(resource, response.getBody());
			}
		}
	}
}
