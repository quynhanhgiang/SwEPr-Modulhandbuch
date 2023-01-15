package de.hscoburg.modulhandbuchbackend.unittests.converters;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.converters.CycleEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class CycleEntityToStringConverterTest {
	
	@InjectMocks
	private CycleEntityToStringConverter cycleEntityToStringConverterWithMocks;

	@Mock
	private CycleEntity mockCycleEntityNoValue;
	@Mock
	private CycleEntity mockCycleEntityWithValue;

	@Test
	public void testConvert() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			CycleEntity cycleEntity;
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
			new TestParameters(this.mockCycleEntityNoValue, false, null, null, null),
			new TestParameters(this.mockCycleEntityWithValue, false, null, null, "Value"),
			new TestParameters(null, false, null, null, null)
		);

		Mockito.when(this.mockCycleEntityWithValue.getValue()).thenReturn("Value");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.cycleEntityToStringConverterWithMocks.convert(testParameters.cycleEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			String receivedString = Assertions.assertDoesNotThrow(() -> this.cycleEntityToStringConverterWithMocks.convert(testParameters.cycleEntity));

			Assertions.assertEquals(testParameters.expectedString, receivedString, testParameters.toString());
		}
	}
}
