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

import de.hscoburg.modulhandbuchbackend.converters.StringToLanguageEntityConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;
import de.hscoburg.modulhandbuchbackend.repositories.LanguageRepository;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class StringToLanguageEntityConverterTest {
	
	@Mock
	private LanguageRepository mockLanguageRepository;
	@InjectMocks
	private StringToLanguageEntityConverter stringToLanguageEntityConverterWithMocks;

	@Mock
	private LanguageEntity mockLanguageEntity;

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
			LanguageEntity expectedLanguageEntity;
		}

		Set<TestParameters> testData = Set.of(
			new TestParameters("Value", false, null, null, this.mockLanguageEntity),
			new TestParameters("Wrong value", false, null, null, null),
			new TestParameters(null, false, null, null, null)
		);

		// initially no languages are present
		Mockito.when(this.mockLanguageRepository.findByValue(Mockito.any())).thenReturn(Collections.emptyList());
		// language with value Value is present
		Mockito.when(this.mockLanguageRepository.findByValue(Mockito.eq("Value"))).thenReturn(List.of(this.mockLanguageEntity));

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.stringToLanguageEntityConverterWithMocks.convert(testParameters.value), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			LanguageEntity receivedLanguageEntity = Assertions.assertDoesNotThrow(() -> this.stringToLanguageEntityConverterWithMocks.convert(testParameters.value));

			Assertions.assertEquals(testParameters.expectedLanguageEntity, receivedLanguageEntity, testParameters.toString());
		}
	}
}
