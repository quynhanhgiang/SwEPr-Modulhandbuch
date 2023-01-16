package de.hscoburg.modulhandbuchbackend.unittests.services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.hscoburg.modulhandbuchbackend.dto.FileInfoDTO;
import de.hscoburg.modulhandbuchbackend.properties.ModuloProperties;
import de.hscoburg.modulhandbuchbackend.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

	@Mock
	private ModuloProperties mockModuloProperties;
	
	@Spy
	@InjectMocks
	private DocumentService documentServiceWithMocks;

	private FileInfoDTO fileInfoModulePlan = new FileInfoDTO();
	private FileInfoDTO fileInfoPreliminaryNote = new FileInfoDTO();

	private Path pathBase;
	private Path existingDirectories;

	@Mock
	private MultipartFile pdfFile;
	@Mock
	private MultipartFile plainTextFile;
	@Mock
	private MultipartFile htmlFile;
	@Mock
	private MultipartFile svgFile;

	@BeforeEach
	public void setup() {
		this.fileInfoModulePlan.setFilename("grid.pdf");
		this.fileInfoModulePlan.setLink("http://localhost/documents/module-manuals/0/module-plan/grid.pdf");

		this.fileInfoPreliminaryNote.setFilename("test.txt");
		this.fileInfoPreliminaryNote.setLink("http://localhost/documents/module-manuals/0/preliminary-note/test.txt");

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

		// fetch creation time
		String timestampModulePlan = Assertions.assertDoesNotThrow(() -> Files.readAttributes(this.pathBase.resolve(Path.of("documents", "module-manuals", "0", "module-plan", "grid.pdf")), BasicFileAttributes.class).creationTime().toString());
		String timestampPreliminaryNote = Assertions.assertDoesNotThrow(() -> Files.readAttributes(this.pathBase.resolve(Path.of("documents", "module-manuals", "0", "preliminary-note", "test.txt")), BasicFileAttributes.class).creationTime().toString());

		this.fileInfoModulePlan.setTimestamp(timestampModulePlan);
		this.fileInfoPreliminaryNote.setTimestamp(timestampPreliminaryNote);
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
	public void testGetDocumentInfo() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			String link;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
			FileInfoDTO expectedFileInfoDTO;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("documents/module-manuals/0/module-plan/grid.pdf", false, null, null, this.fileInfoModulePlan),
			new TestParameters("documents/module-manuals/0/module-plan/test.txt", false, null, null, new FileInfoDTO()),
			new TestParameters("documents/module-manuals/0/module-plan/index.html", false, null, null, new FileInfoDTO()),

			new TestParameters("documents/module-manuals/0/preliminary-note/grid.pdf", false, null, null, new FileInfoDTO()),
			new TestParameters("documents/module-manuals/0/preliminary-note/test.txt", false, null, null, this.fileInfoPreliminaryNote),
			new TestParameters("documents/module-manuals/0/preliminary-note/index.html", false, null, null, new FileInfoDTO()),

			new TestParameters("", false, null, null, new FileInfoDTO()),
			new TestParameters(null, false, null, null, new FileInfoDTO())
		);

		// when input is not known return empty object
		Assertions.assertDoesNotThrow(() -> Mockito.doReturn(new FileInfoDTO()).when(this.documentServiceWithMocks).getDocumentInfoFromPath(Mockito.any()));
		// when input to method is specified path return pre-populated object
		Assertions.assertDoesNotThrow(() -> Mockito.doReturn(this.fileInfoModulePlan).when(this.documentServiceWithMocks).getDocumentInfoFromPath(Path.of("documents/module-manuals/0/module-plan/grid.pdf")));
		// when input to method is specified path return pre-populated object
		Assertions.assertDoesNotThrow(() -> Mockito.doReturn(this.fileInfoPreliminaryNote).when(this.documentServiceWithMocks).getDocumentInfoFromPath(Path.of("documents/module-manuals/0/preliminary-note/test.txt")));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.documentServiceWithMocks.getDocumentInfo(testParameters.link), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			FileInfoDTO receivedFileInfoDTO = Assertions.assertDoesNotThrow(() -> this.documentServiceWithMocks.getDocumentInfo(testParameters.link));

			Assertions.assertEquals(testParameters.expectedFileInfoDTO, receivedFileInfoDTO, testParameters.toString());
		}
	}

	@Test
	public void testGetDocumentInfoFromPath() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			Path path;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
			FileInfoDTO expectedFileInfoDTO;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters(Path.of("documents", "module-manuals", "0", "module-plan", "grid.pdf"), false, null, null, this.fileInfoModulePlan),
			new TestParameters(Path.of("documents", "module-manuals", "0", "module-plan", "test.txt"), false, null, null, new FileInfoDTO()),
			new TestParameters(Path.of("documents", "module-manuals", "0", "module-plan", "index.html"), false, null, null, new FileInfoDTO()),

			new TestParameters(Path.of("documents", "module-manuals", "0", "preliminary-note", "grid.pdf"), false, null, null, new FileInfoDTO()),
			new TestParameters(Path.of("documents", "module-manuals", "0", "preliminary-note", "test.txt"), false, null, null, this.fileInfoPreliminaryNote),
			new TestParameters(Path.of("documents", "module-manuals", "0", "preliminary-note", "index.html"), false, null, null, new FileInfoDTO()),

			new TestParameters(Path.of(""), false, null, null, new FileInfoDTO()),
			new TestParameters(null, true, NullPointerException.class, null, null)
		);

		Mockito.doReturn(this.pathBase).when(this.documentServiceWithMocks).getFilesDirectory();
		
		Mockito.doReturn(ServletUriComponentsBuilder.fromHttpUrl("http://localhost")).when(this.documentServiceWithMocks).getLinkToDocumentBuilder();

		for (TestParameters testParameters : testData) {
			// needed otherwise the path would not been reset because it is a mock
			this.documentServiceWithMocks.getLinkToDocumentBuilder().replacePath(null);

			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.documentServiceWithMocks.getDocumentInfoFromPath(testParameters.path), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			FileInfoDTO receivedFileInfoDTO = Assertions.assertDoesNotThrow(() -> this.documentServiceWithMocks.getDocumentInfoFromPath(testParameters.path), testParameters.toString());

			Assertions.assertEquals(testParameters.expectedFileInfoDTO, receivedFileInfoDTO, testParameters.toString());
		}
	}

	@Test
	public void testValidateContentType() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			MultipartFile file;
			Set<MediaType> allowedContentTypes;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
		}

		Set<TestParameters> testData = Set.of(
			// file pdfFile
			new TestParameters(this.pdfFile, Set.of(MediaType.APPLICATION_PDF), false, null, null),
			new TestParameters(this.pdfFile, Set.of(MediaType.TEXT_PLAIN), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/pdf' not supported\""),
			new TestParameters(this.pdfFile, Set.of(MediaType.TEXT_HTML), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/pdf' not supported\""),
			new TestParameters(this.pdfFile, Set.of(MediaType.valueOf("image/svg+xml")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/pdf' not supported\""),
			new TestParameters(this.pdfFile, Set.of(MediaType.valueOf("application/*")), false, null, null),
			new TestParameters(this.pdfFile, Set.of(MediaType.valueOf("text/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/pdf' not supported\""),
			new TestParameters(this.pdfFile, Set.of(MediaType.valueOf("image/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/pdf' not supported\""),
			new TestParameters(this.pdfFile, Set.of(MediaType.APPLICATION_PDF, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.valueOf("image/svg+xml")), false, null, null),
			new TestParameters(this.pdfFile, Set.of(MediaType.valueOf("*/*")), false, null, null),
			new TestParameters(this.pdfFile, Set.of(), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/pdf' not supported\""),
			new TestParameters(this.pdfFile, new HashSet<>(Arrays.asList((MediaType) null)), true, NullPointerException.class, "Cannot invoke \"org.springframework.http.MediaType.includes(org.springframework.http.MediaType)\" because \"mediaType\" is null"),
			new TestParameters(this.pdfFile, null, true, NullPointerException.class, "Cannot invoke \"java.util.Set.stream()\" because \"allowedContentTypes\" is null"),

			// file plainTextFile
			new TestParameters(this.plainTextFile, Set.of(MediaType.APPLICATION_PDF), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/plain' not supported\""),
			new TestParameters(this.plainTextFile, Set.of(MediaType.TEXT_PLAIN), false, null, null),
			new TestParameters(this.plainTextFile, Set.of(MediaType.TEXT_HTML), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/plain' not supported\""),
			new TestParameters(this.plainTextFile, Set.of(MediaType.valueOf("image/svg+xml")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/plain' not supported\""),
			new TestParameters(this.plainTextFile, Set.of(MediaType.valueOf("application/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/plain' not supported\""),
			new TestParameters(this.plainTextFile, Set.of(MediaType.valueOf("text/*")), false, null, null),
			new TestParameters(this.plainTextFile, Set.of(MediaType.valueOf("image/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/plain' not supported\""),
			new TestParameters(this.plainTextFile, Set.of(MediaType.APPLICATION_PDF, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.valueOf("image/svg+xml")), false, null, null),
			new TestParameters(this.plainTextFile, Set.of(MediaType.valueOf("*/*")), false, null, null),
			new TestParameters(this.plainTextFile, Set.of(), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/plain' not supported\""),
			new TestParameters(this.plainTextFile, new HashSet<>(Arrays.asList((MediaType) null)), true, NullPointerException.class, "Cannot invoke \"org.springframework.http.MediaType.includes(org.springframework.http.MediaType)\" because \"mediaType\" is null"),
			new TestParameters(this.plainTextFile, null, true, NullPointerException.class, "Cannot invoke \"java.util.Set.stream()\" because \"allowedContentTypes\" is null"),

			// file htmlFile
			new TestParameters(this.htmlFile, Set.of(MediaType.APPLICATION_PDF), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/html' not supported\""),
			new TestParameters(this.htmlFile, Set.of(MediaType.TEXT_PLAIN), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/html' not supported\""),
			new TestParameters(this.htmlFile, Set.of(MediaType.TEXT_HTML), false, null, null),
			new TestParameters(this.htmlFile, Set.of(MediaType.valueOf("image/svg+xml")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/html' not supported\""),
			new TestParameters(this.htmlFile, Set.of(MediaType.valueOf("application/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/html' not supported\""),
			new TestParameters(this.htmlFile, Set.of(MediaType.valueOf("text/*")), false, null, null),
			new TestParameters(this.htmlFile, Set.of(MediaType.valueOf("image/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/html' not supported\""),
			new TestParameters(this.htmlFile, Set.of(MediaType.APPLICATION_PDF, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.valueOf("image/svg+xml")), false, null, null),
			new TestParameters(this.htmlFile, Set.of(MediaType.valueOf("*/*")), false, null, null),
			new TestParameters(this.htmlFile, Set.of(), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'text/html' not supported\""),
			new TestParameters(this.htmlFile, new HashSet<>(Arrays.asList((MediaType) null)), true, NullPointerException.class, "Cannot invoke \"org.springframework.http.MediaType.includes(org.springframework.http.MediaType)\" because \"mediaType\" is null"),
			new TestParameters(this.htmlFile, null, true, NullPointerException.class, "Cannot invoke \"java.util.Set.stream()\" because \"allowedContentTypes\" is null"),

			// file svgFile
			new TestParameters(this.svgFile, Set.of(MediaType.APPLICATION_PDF), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'image/svg+xml' not supported\""),
			new TestParameters(this.svgFile, Set.of(MediaType.TEXT_PLAIN), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'image/svg+xml' not supported\""),
			new TestParameters(this.svgFile, Set.of(MediaType.TEXT_HTML), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'image/svg+xml' not supported\""),
			new TestParameters(this.svgFile, Set.of(MediaType.valueOf("image/svg+xml")), false, null, null),
			new TestParameters(this.svgFile, Set.of(MediaType.valueOf("application/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'image/svg+xml' not supported\""),
			new TestParameters(this.svgFile, Set.of(MediaType.valueOf("text/*")), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'image/svg+xml' not supported\""),
			new TestParameters(this.svgFile, Set.of(MediaType.valueOf("image/*")), false, null, null),
			new TestParameters(this.svgFile, Set.of(MediaType.APPLICATION_PDF, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.valueOf("image/svg+xml")), false, null, null),
			new TestParameters(this.svgFile, Set.of(MediaType.valueOf("*/*")), false, null, null),
			new TestParameters(this.svgFile, Set.of(), true, RuntimeException.class, "415 UNSUPPORTED_MEDIA_TYPE \"Content type 'image/svg+xml' not supported\""),
			new TestParameters(this.svgFile, new HashSet<>(Arrays.asList((MediaType) null)), true, NullPointerException.class, "Cannot invoke \"org.springframework.http.MediaType.includes(org.springframework.http.MediaType)\" because \"mediaType\" is null"),
			new TestParameters(this.svgFile, null, true, NullPointerException.class, "Cannot invoke \"java.util.Set.stream()\" because \"allowedContentTypes\" is null"),

			// file null
			new TestParameters(null, Set.of(MediaType.APPLICATION_PDF), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.TEXT_PLAIN), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.TEXT_HTML), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.valueOf("image/svg+xml")), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.valueOf("application/*")), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.valueOf("text/*")), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.valueOf("image/*")), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.APPLICATION_PDF, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.valueOf("image/svg+xml")), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(MediaType.valueOf("*/*")), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, Set.of(), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, new HashSet<>(Arrays.asList((MediaType) null)), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null"),
			new TestParameters(null, null, true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"file\" is null")
		);

		Mockito.when(this.pdfFile.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
		Mockito.when(this.plainTextFile.getContentType()).thenReturn(MediaType.TEXT_PLAIN_VALUE);
		Mockito.when(this.htmlFile.getContentType()).thenReturn(MediaType.TEXT_HTML_VALUE);
		Mockito.when(this.svgFile.getContentType()).thenReturn("image/svg+xml");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.documentServiceWithMocks.validateContentType(testParameters.file, testParameters.allowedContentTypes), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertDoesNotThrow(() -> this.documentServiceWithMocks.validateContentType(testParameters.file, testParameters.allowedContentTypes), testParameters.toString());
		}
	}

	@Test
	public void testSaveDocument() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			MultipartFile file;
			Path relativePath;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters(this.pdfFile, Path.of("documents", "module-manuals", "100", "module-plan", "grid.pdf"), false, null, null),
			new TestParameters(this.plainTextFile, Path.of("documents", "module-manuals", "100", "preliminary-note", "test.txt"), false, null, null),
			new TestParameters(this.pdfFile, null, true, NullPointerException.class, null),
			new TestParameters(null, Path.of("documents", "module-manuals", "100", "module-plan", "grid.pdf"), true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.transferTo(java.nio.file.Path)\" because \"file\" is null"),
			new TestParameters(null, null, true, NullPointerException.class, null)
		);

		Mockito.doReturn(this.pathBase).when(this.documentServiceWithMocks).getFilesDirectory();

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.documentServiceWithMocks.saveDocument(testParameters.file, testParameters.relativePath), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertDoesNotThrow(() -> this.documentServiceWithMocks.saveDocument(testParameters.file, testParameters.relativePath), testParameters.toString());

			Path absolutePathToParentDirectory = this.pathBase.resolve(testParameters.relativePath).getParent();

			// test if parent directory exists
			boolean parentDirectoryExists = Assertions.assertDoesNotThrow(() -> absolutePathToParentDirectory.toFile().exists(), testParameters.toString());
			Assertions.assertTrue(parentDirectoryExists, testParameters.toString());

			// test if directory is empty because actual saving is implemented by MultipartFile which is mocked
			boolean parentDirectoryIsEmpty = Assertions.assertDoesNotThrow(() -> FileUtils.isEmptyDirectory(absolutePathToParentDirectory.toFile()), testParameters.toString());
			Assertions.assertTrue(parentDirectoryIsEmpty, testParameters.toString());
		}
	}
}
