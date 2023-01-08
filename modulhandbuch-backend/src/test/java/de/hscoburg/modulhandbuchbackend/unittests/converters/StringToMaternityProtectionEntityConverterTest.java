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

import de.hscoburg.modulhandbuchbackend.converters.StringToMaternityProtectionEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.MaternityProtectionEntity;
import de.hscoburg.modulhandbuchbackend.repositories.MaternityProtectionRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToMaternityProtectionEntityConverterTest {
	
	@Mock
	private MaternityProtectionRepository mockMaternityProtectionRepository;
	@InjectMocks
	private StringToMaternityProtectionEntityConverter stringToMaternityProtectionEntityConverterWithMocks;

	@Mock
	private MaternityProtectionEntity mockMaternityProtectionEntity;

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
			MaternityProtectionEntity expectedMaternityProtectionEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockMaternityProtectionEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no maternity protections are present
		Mockito.when(this.mockMaternityProtectionRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// maternity protection with value Value is present
		Mockito.when(this.mockMaternityProtectionRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockMaternityProtectionEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToMaternityProtectionEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			MaternityProtectionEntity receivedMaternityProtectionEntity = Assertions.assertDoesNotThrow(() -> this.stringToMaternityProtectionEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedMaternityProtectionEntity, receivedMaternityProtectionEntity, testParameters.toString());
		}
	}
}
