package de.hscoburg.modulhandbuchbackend.unittests.converters;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.converters.DurationEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class DurationEntityToStringConverterTest {
	
	@InjectMocks
	private DurationEntityToStringConverter durationEntityToStringConverterWithMocks;

	@Mock
	private DurationEntity mockDurationEntityNoValue;
	@Mock
	private DurationEntity mockDurationEntityWithValue;

	@Test
	public void testConvert() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			DurationEntity durationEntity;
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
			new TestParameters(this.mockDurationEntityNoValue, false, null, null, null),
			new TestParameters(this.mockDurationEntityWithValue, false, null, null, "Value"),
			new TestParameters(null, false, null, null, null)
		);

		Mockito.when(this.mockDurationEntityWithValue.getValue()).thenReturn("Value");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.durationEntityToStringConverterWithMocks.convert(testParameters.durationEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			String receivedString = Assertions.assertDoesNotThrow(() -> this.durationEntityToStringConverterWithMocks.convert(testParameters.durationEntity));

			Assertions.assertEquals(testParameters.expectedString, receivedString, testParameters.toString());
		}
	}
}
