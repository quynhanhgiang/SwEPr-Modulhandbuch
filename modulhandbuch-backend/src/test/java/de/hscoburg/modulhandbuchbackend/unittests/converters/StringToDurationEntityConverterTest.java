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

import de.hscoburg.modulhandbuchbackend.converters.StringToDurationEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.DurationEntity;
import de.hscoburg.modulhandbuchbackend.repositories.DurationRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToDurationEntityConverterTest {
	
	@Mock
	private DurationRepository mockDurationRepository;
	@InjectMocks
	private StringToDurationEntityConverter stringToDurationEntityConverterWithMocks;

	@Mock
	private DurationEntity mockDurationEntity;

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
			DurationEntity expectedDurationEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockDurationEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no durations are present
		Mockito.when(this.mockDurationRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// duration with value Value is present
		Mockito.when(this.mockDurationRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockDurationEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToDurationEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			DurationEntity receivedDurationEntity = Assertions.assertDoesNotThrow(() -> this.stringToDurationEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedDurationEntity, receivedDurationEntity, testParameters.toString());
		}
	}
}
