package de.hscoburg.modulhandbuchbackend.unittests.converters;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.hscoburg.modulhandbuchbackend.converters.LanguageEntityToStringConverter;
import de.hscoburg.modulhandbuchbackend.model.entities.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ExtendWith(MockitoExtension.class)
public class LanguageEntityToStringConverterTest {
	
	@InjectMocks
	private LanguageEntityToStringConverter languageEntityToStringConverterWithMocks;

	@Mock
	private LanguageEntity mockLanguageEntityNoValue;
	@Mock
	private LanguageEntity mockLanguageEntityWithValue;

	@Test
	public void testConvert() {
		@AllArgsConstructor
		@EqualsAndHashCode
		@ToString
		class TestParameters {
			LanguageEntity languageEntity;
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
			new TestParameters(this.mockLanguageEntityNoValue, false, null, null, null),
			new TestParameters(this.mockLanguageEntityWithValue, false, null, null, "Value"),
			new TestParameters(null, false, null, null, null)
		);

		Mockito.when(this.mockLanguageEntityWithValue.getValue()).thenReturn("Value");

		for (TestParameters testParameters : testData) {
			if (testParameters.expectedToThrowException) {
				Exception exception = Assertions.assertThrows(testParameters.expectedException, () -> this.languageEntityToStringConverterWithMocks.convert(testParameters.languageEntity), testParameters.toString());

				Assertions.assertEquals(testParameters.expectedExceptionMessage, exception.getMessage(), testParameters.toString());

				continue;
			}

			String receivedString = Assertions.assertDoesNotThrow(() -> this.languageEntityToStringConverterWithMocks.convert(testParameters.languageEntity));

			Assertions.assertEquals(testParameters.expectedString, receivedString, testParameters.toString());
		}
	}
}
