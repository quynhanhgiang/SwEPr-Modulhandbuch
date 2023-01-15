package de.hscoburg.modulhandbuchbackend.unittests.converters;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.converters.MaternityProtectionEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class MaternityProtectionEntityToStringConverterTest {
	
	@InjectMocks
	private MaternityProtectionEntityToStringConverter maternityProtectionEntityToStringConverterWithMocks;

	@Mock
	private MaternityProtectionEntity mockMaternityProtectionEntityNoValue;
	@Mock
	private MaternityProtectionEntity mockMaternityProtectionEntityWithValue;

	@Test
	public void testConvert() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			MaternityProtectionEntity maternityProtectionEntity;
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
			new TestParameters(this.mockMaternityProtectionEntityNoValue, false, null, null, null),
			new TestParameters(this.mockMaternityProtectionEntityWithValue, false, null, null, "Value"),
			new TestParameters(null, false, null, null, null)
		);

		Mockito.when(this.mockMaternityProtectionEntityWithValue.getValue()).thenReturn("Value");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.maternityProtectionEntityToStringConverterWithMocks.convert(testParameters.maternityProtectionEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			String receivedString = Assertions.assertDoesNotThrow(() -> this.maternityProtectionEntityToStringConverterWithMocks.convert(testParameters.maternityProtectionEntity));

			Assertions.assertEquals(testParameters.expectedString, receivedString, testParameters.toString());
		}
	}
}
