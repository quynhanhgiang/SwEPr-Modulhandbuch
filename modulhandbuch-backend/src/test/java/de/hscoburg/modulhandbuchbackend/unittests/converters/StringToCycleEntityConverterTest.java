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

import de.hscoburg.modulhandbuchbackend.converters.StringToCycleEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.CycleEntity;
import de.hscoburg.modulhandbuchbackend.repositories.CycleRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToCycleEntityConverterTest {
	
	@Mock
	private CycleRepository mockCycleRepository;
	@InjectMocks
	private StringToCycleEntityConverter stringToCycleEntityConverterWithMocks;

	@Mock
	private CycleEntity mockCycleEntity;

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
			CycleEntity expectedCycleEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockCycleEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no cycles are present
		Mockito.when(this.mockCycleRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// cycle with value Value is present
		Mockito.when(this.mockCycleRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockCycleEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToCycleEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			CycleEntity receivedCycleEntity = Assertions.assertDoesNotThrow(() -> this.stringToCycleEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedCycleEntity, receivedCycleEntity, testParameters.toString());
		}
	}
}
