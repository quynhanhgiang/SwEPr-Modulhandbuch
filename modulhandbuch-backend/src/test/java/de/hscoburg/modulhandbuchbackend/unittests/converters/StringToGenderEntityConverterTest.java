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

import de.hscoburg.modulhandbuchbackend.converters.StringToGenderEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;
import de.hscoburg.modulhandbuchbackend.repositories.GenderRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToGenderEntityConverterTest {
	
	@Mock
	private GenderRepository mockGenderRepository;
	@InjectMocks
	private StringToGenderEntityConverter stringToGenderEntityConverterWithMocks;

	@Mock
	private GenderEntity mockGenderEntity;

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
			GenderEntity expectedGenderEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockGenderEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no genders are present
		Mockito.when(this.mockGenderRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// gender with value Value is present
		Mockito.when(this.mockGenderRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockGenderEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToGenderEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			GenderEntity receivedGenderEntity = Assertions.assertDoesNotThrow(() -> this.stringToGenderEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedGenderEntity, receivedGenderEntity, testParameters.toString());
		}
	}
}
