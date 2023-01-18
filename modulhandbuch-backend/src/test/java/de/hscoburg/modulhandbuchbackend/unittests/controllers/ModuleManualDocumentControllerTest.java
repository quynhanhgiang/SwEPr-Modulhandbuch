package de.hscoburg.modulhandbuchbackend.unittests.controllers;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import de.hscoburg.modulhandbuchbackend.controllers.ModuleManualDocumentController;
import de.hscoburg.modulhandbuchbackend.dto.FileInfoDTO;
import de.hscoburg.modulhandbuchbackend.exceptions.ModuleManualNotFoundException;
import de.hscoburg.modulhandbuchbackend.model.entities.ModuleManualEntity;
import de.hscoburg.modulhandbuchbackend.repositories.ModuleManualRepository;
import de.hscoburg.modulhandbuchbackend.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class ModuleManualDocumentControllerTest {
	
	@Mock
	private ModuleManualRepository mockModuleManualRepository;
	@Mock
	private DocumentService mockDocumentService;
	@Mock
	private ServletContext mockServletContext;
	@InjectMocks
	private ModuleManualDocumentController moduleManualDocumentControllerWithMocks;

	@Mock
	private ModuleManualEntity moduleManualEntityWithContent;
	@Mock
	private ModuleManualEntity mockModuleManualEntity;

	private FileInfoDTO fileInfoModulePlan = new FileInfoDTO();
	private FileInfoDTO fileInfoPreliminaryNote = new FileInfoDTO();

	@Mock
	private MultipartFile pdfFile;
	@Mock
	private MultipartFile plainTextFile;
	@Mock
	private MultipartFile htmlFile;

	@BeforeEach
	public void setup() {
		this.fileInfoModulePlan.setFilename("grid.pdf");
		this.fileInfoModulePlan.setLink("http://localhost/documents/module-manuals/0/module-plan/grid.pdf");

		this.fileInfoPreliminaryNote.setFilename("test.txt");
		this.fileInfoPreliminaryNote.setLink("http://localhost/documents/module-manuals/0/preliminary-note/test.txt");
	}

	@Test
	public void testGetMappings() throws Exception {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			Function<Integer, FileInfoDTO> method;
			Integer id;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
			FileInfoDTO expectedFileInfoDTO;
		}

		Set<TestParameters> testData = Set.of(
			// oneModulePlan
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), 0, false, null, null, this.fileInfoModulePlan),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), 1, false, null, null, new FileInfoDTO()),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), 2, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.oneModulePlan(id), null, true, IllegalArgumentException.class, null, null),

			// onePreliminaryNote
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), 0, false, null, null, this.fileInfoPreliminaryNote),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), 1, false, null, null, new FileInfoDTO()),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), 2, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), -1, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id) -> this.moduleManualDocumentControllerWithMocks.onePreliminaryNote(id), null, true, IllegalArgumentException.class, null, null)
		);

		Mockito.when(this.moduleManualEntityWithContent.getModulePlanLink()).thenReturn("documents/module-manuals/0/module-plan/grid.pdf");
		Mockito.when(this.moduleManualEntityWithContent.getPreliminaryNoteLink()).thenReturn("documents/module-manuals/0/preliminary-note/test.txt");

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// module manual with id 0 is present and is linked to documents
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.moduleManualEntityWithContent));
		// module manual with id 1 is present but is not linked to documents
		Mockito.when(this.mockModuleManualRepository.findById(1)).thenReturn(Optional.of(this.mockModuleManualEntity));

		// when input is not known return empty object
		Mockito.when(this.mockDocumentService.getDocumentInfo(Mockito.any())).thenReturn(new FileInfoDTO());
		// when input to method is the specified string return pre-populated object
		Mockito.when(this.mockDocumentService.getDocumentInfo("documents/module-manuals/0/module-plan/grid.pdf")).thenReturn(this.fileInfoModulePlan);
		// when input to method is the specified string return pre-populated object
		Mockito.when(this.mockDocumentService.getDocumentInfo("documents/module-manuals/0/preliminary-note/test.txt")).thenReturn(this.fileInfoPreliminaryNote);

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> testParameters.method.apply(testParameters.id), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertEquals(testParameters.expectedFileInfoDTO, testParameters.method.apply(testParameters.id), testParameters.toString());
		}
	}

	@Test
	public void testPutMappings() throws Exception {
		// content type validation is not tested, see DocumentServiceTest
		
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			BiFunction<Integer, MultipartFile, FileInfoDTO> method;
			Integer id;
			MultipartFile file;
			boolean expectedToThrowException;
			Class<? extends Exception> expectedException;
			String expectedExceptionMessage;
			FileInfoDTO expectedFileInfoDTO;
		}

		Set<TestParameters> testData = Set.of(
			// replaceModulePlan
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 0, this.pdfFile, false, null, null, this.fileInfoModulePlan),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 0, this.plainTextFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 0, this.htmlFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 0, null, true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getOriginalFilename()\" because \"modulePlanFile\" is null", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 1, this.pdfFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 1, this.plainTextFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 1, this.htmlFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 1, null, true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getOriginalFilename()\" because \"modulePlanFile\" is null", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 2, this.pdfFile, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 2, this.plainTextFile, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 2, this.htmlFile, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), 2, null, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), -1, this.pdfFile, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), -1, this.plainTextFile, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), -1, this.htmlFile, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), -1, null, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), null, this.pdfFile, true, IllegalArgumentException.class, null, null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), null, this.plainTextFile, true, IllegalArgumentException.class, null, null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), null, this.htmlFile, true, IllegalArgumentException.class, null, null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replaceModulePlan(id, file), null, null, true, IllegalArgumentException.class, null, null),

			// replacePreliminaryNote
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 0, this.pdfFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 0, this.plainTextFile, false, null, null, this.fileInfoPreliminaryNote),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 0, this.htmlFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 0, null, true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getOriginalFilename()\" because \"preliminaryNoteFile\" is null", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 1, this.pdfFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 1, this.plainTextFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 1, this.htmlFile, false, null, null, new FileInfoDTO()),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 1, null, true, NullPointerException.class, "Cannot invoke \"org.springframework.web.multipart.MultipartFile.getOriginalFilename()\" because \"preliminaryNoteFile\" is null", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 2, this.pdfFile, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 2, this.plainTextFile, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 2, this.htmlFile, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), 2, null, true, ModuleManualNotFoundException.class, "Module manual with id 2 not found.", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), -1, this.pdfFile, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), -1, this.plainTextFile, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), -1, this.htmlFile, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), -1, null, true, ModuleManualNotFoundException.class, "Module manual with id -1 not found.", null),

			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), null, this.pdfFile, true, IllegalArgumentException.class, null, null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), null, this.plainTextFile, true, IllegalArgumentException.class, null, null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), null, this.htmlFile, true, IllegalArgumentException.class, null, null),
			new TestParameters((id, file) -> this.moduleManualDocumentControllerWithMocks. replacePreliminaryNote(id, file), null, null, true, IllegalArgumentException.class, null, null)
		);

		// initially no module manuals are present
		Mockito.when(this.mockModuleManualRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		// IllegalArgumentException if id is null
		Mockito.when(this.mockModuleManualRepository.findById(null)).thenThrow(new IllegalArgumentException());
		// module manual with id 0 is present and is linked to documents
		Mockito.when(this.mockModuleManualRepository.findById(0)).thenReturn(Optional.of(this.moduleManualEntityWithContent));
		// module manual with id 1 is present but is not linked to documents
		Mockito.when(this.mockModuleManualRepository.findById(1)).thenReturn(Optional.of(this.mockModuleManualEntity));

		// when input is not known return empty object
		Mockito.when(this.mockDocumentService.getDocumentInfoFromPath(Mockito.any())).thenReturn(new FileInfoDTO());
		// when input to method is specified string return pre-populated object
		Mockito.when(this.mockDocumentService.getDocumentInfoFromPath(Path.of("documents/module-manuals/0/module-plan/grid.pdf"))).thenReturn(this.fileInfoModulePlan);
		// when input to method is specified string return pre-populated object
		Mockito.when(this.mockDocumentService.getDocumentInfoFromPath(Path.of("documents/module-manuals/0/preliminary-note/test.txt"))).thenReturn(this.fileInfoPreliminaryNote);

		Mockito.when(this.pdfFile.getOriginalFilename()).thenReturn("grid.pdf");
		Mockito.when(this.plainTextFile.getOriginalFilename()).thenReturn("test.txt");
		Mockito.when(this.htmlFile.getOriginalFilename()).thenReturn("index.html");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> testParameters.method.apply(testParameters.id, testParameters.file), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			Assertions.assertEquals(testParameters.expectedFileInfoDTO, testParameters.method.apply(testParameters.id, testParameters.file), testParameters.toString());
		}
	}
}
