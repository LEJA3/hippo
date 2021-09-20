package uk.nhs.digital.apispecs;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.nhs.digital.test.util.ExceptionTestUtils.wrapCheckedException;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Violation;
import org.onehippo.repository.mock.MockNode;
import uk.nhs.digital.common.validators.NegativeRegExpValidator;

import java.util.Optional;

@RunWith(DataProviderRunner.class)
public class NegativeRegExpValidatorTest {

    private ValidationContext context;
    private Violation violation;

    @Before
    public void setUp() throws Exception {
        context = mock(ValidationContext.class);
        violation = mock(Violation.class);

        given(context.createViolation()).willReturn(violation);
    }

    @Test
    public void rejectsTextContainingAtLeastOneForbiddenPattern() {
        assertTextRejected(
            "text with forbiddenWordPresentInText",
            "forbiddenWordPresentInText",
            "forbiddenWordMissingFromText"
        );
    }

    @Test
    public void acceptsTextContainingNoAtLeastOneForbiddenPattern() {
        assertTextAccepted(
            "some text including none of the forbidden words",
            "forbiddenWordMissingFromTextA",
            "forbiddenWordMissingFromTextB"
        );
    }

    @Test
    public void usesCaseInsensitiveMatching() {
        assertTextRejected(
            "text with forbiddenWordPresentInText",
            "FORBIDDENWORDPRESENTINTEXT"
        );
    }

    @Test
    @UseDataProvider("textsWithScriptTags")
    public void rejectTextWithScriptTag(final String referenceText) {
        assertTextRejected(
            referenceText,
            "\\<\\s*\\bscript\\b",
            "\\bscript\\b\\s*\\>"
        );
    }

    @DataProvider
    public static Object[][] textsWithScriptTags() {
        return new Object[][] {
            {"text with opening <script tag"},
            {"text with opening <SCRIPT tag"},
            {"text with opening <sCRiPt tag"},
            {"text with opening <script> tag"},
            {"text with opening < script > tag"},
            {"text with opening <\nscript tag"},
            {"text with opening <\n\nscript tag"},
            {"text with opening <\rscript tag"},
            {"text with opening <\fscript tag"},
            {"text with opening <\tscript tag"},

            {"text with closing script> tag"},
            {"text with closing SCRIPT> tag"},
            {"text with closing sCRiPt> tag"},
            {"text with closing </script> tag"},
            {"text with closing script > tag"},
            {"text with closing script\n> tag"},
            {"text with closing script\n\n> tag"},
            {"text with closing script\r> tag"},
            {"text with closing script\f> tag"},
            {"text with closing script\t> tag"},
        };
    }

    @Test
    @UseDataProvider("textsWithNoScriptTags")
    public void acceptsTextWithNoScriptTag(final String referenceText) {
        assertTextAccepted(
            referenceText,
            "\\<\\s*\\bscript\\b",
            "\\bscript\\b\\s*\\>"
        );
    }

    @DataProvider
    public static Object[][] textsWithNoScriptTags() {
        return new Object[][] {
            // "opening" tags
            {"text with script word but not tag"},
            {"text with script_word but not tag"},
            {"text with scriptword but not tag"},
            {"text with scriptWord but not tag"},
            {"text with <scriptword but not tag"},

            // "closing" tags
            {"text with _script> but not tag"},
            {"text withScript> but not tag"},
        };
    }

    private void assertTextAccepted(final String referenceText, final String... patterns) {

        // given
        final NegativeRegExpValidator validator = validatorConfiguredWithForbiddenPatterns(
            patterns
        );

        // when
        final Optional<Violation> violation = validator.validate(context, referenceText);

        // then
        assertTrue("Text is accepted.", !violation.isPresent());
    }

    private void assertTextRejected(final String referenceText, final String... patterns) {

        // given
        final NegativeRegExpValidator validator = validatorConfiguredWithForbiddenPatterns(
            patterns
        );

        // when
        final Optional<Violation> violation = validator.validate(context, referenceText);

        // then
        assertTrue("Text is rejected.", violation.isPresent());
    }


    private NegativeRegExpValidator validatorConfiguredWithForbiddenPatterns(final String... forbiddenPatterns) {
        final MockNode validatorConfigNode = new MockNode("mockValidatorConfigNode");

        wrapCheckedException(() -> {
            validatorConfigNode.setProperty("forbiddenPatterns", forbiddenPatterns);
        });

        return new NegativeRegExpValidator(validatorConfigNode);
    }
}
