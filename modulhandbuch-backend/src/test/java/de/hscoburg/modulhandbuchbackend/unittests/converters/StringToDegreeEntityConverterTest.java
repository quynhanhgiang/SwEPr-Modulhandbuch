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

import de.hscoburg.modulhandbuchbackend.converters.StringToDegreeEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.DegreeEntity;
import de.hscoburg.modulhandbuchbackend.repositories.DegreeRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToDegreeEntityConverterTest {
	
	@Mock
	private DegreeRepository mockDegreeRepository;
	@InjectMocks
	private StringToDegreeEntityConverter stringToDegreeEntityConverterWithMocks;

	@Mock
	private DegreeEntity mockDegreeEntity;

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
			DegreeEntity expectedDegreeEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockDegreeEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no degrees are present
		Mockito.when(this.mockDegreeRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// degree with value Value is present
		Mockito.when(this.mockDegreeRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockDegreeEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToDegreeEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			DegreeEntity receivedDegreeEntity = Assertions.assertDoesNotThrow(() -> this.stringToDegreeEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedDegreeEntity, receivedDegreeEntity, testParameters.toString());
		}
	}
}
