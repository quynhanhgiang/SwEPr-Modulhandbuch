package de.hscoburg.modulhandbuchbackend.unittests.converters;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.converters.GenderEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.GenderEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class GenderEntityToStringConverterTest {
	
	@InjectMocks
	private GenderEntityToStringConverter genderEntityToStringConverterWithMocks;

	@Mock
	private GenderEntity mockGenderEntityNoValue;
	@Mock
	private GenderEntity mockGenderEntityWithValue;

	@Test
	public void testConvert() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			GenderEntity genderEntity;
			@EqualsAndHashCode.Exclude
			boolean expectedToThrowException;
			@EqualsAndHashCode.Exclude
			Class<? extends Exception> expectedException;
			@EqualsAndHashCode.Exclude
			String expectedExceptionMessage;
			@EqualsAndHashCode.Exclude
			String expectedString;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters(this.mockGenderEntityNoValue, false, null, null, null),
			new TestParameters(this.mockGenderEntityWithValue, false, null, null, "Value"),
			new TestParameters(null, false, null, null, null)
		);

		Mockito.when(this.mockGenderEntityWithValue.getValue()).thenReturn("Value");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.genderEntityToStringConverterWithMocks.convert(testParameters.genderEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			String receivedString = Assertions.assertDoesNotThrow(() -> this.genderEntityToStringConverterWithMocks.convert(testParameters.genderEntity));

			Assertions.assertEquals(testParameters.expectedString, receivedString, testParameters.toString());
		}
	}
}
