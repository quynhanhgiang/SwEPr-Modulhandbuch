package de.hscoburg.modulhandbuchbackend.unittests.converters;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.converters.StringToAdmissionRequirementEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.AdmissionRequirementEntity;
import de.hscoburg.modulhandbuchbackend.repositories.AdmissionRequirementRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToAdmissionRequirementEntityConverterTest {
	
	@Mock
	private AdmissionRequirementRepository mockAdmissionRequirementRepository;
	@InjectMocks
	private StringToAdmissionRequirementEntityConverter stringToAdmissionRequirementEntityConverterWithMocks;

	@Mock
	private AdmissionRequirementEntity mockAdmissionRequirementEntity;

	@Test
	public void testConvert() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			String value;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			AdmissionRequirementEntity expectedAdmissionRequirementEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockAdmissionRequirementEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no admission requirements are present
		Mockito.when(this.mockAdmissionRequirementRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// admission requirement with value Value is present
		Mockito.when(this.mockAdmissionRequirementRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockAdmissionRequirementEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToAdmissionRequirementEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			AdmissionRequirementEntity receivedAdmissionRequirementEntity = Assertions.assertDoesNotThrow(() -> this.stringToAdmissionRequirementEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedAdmissionRequirementEntity, receivedAdmissionRequirementEntity, testParameters.toString());
		}
	}
}
