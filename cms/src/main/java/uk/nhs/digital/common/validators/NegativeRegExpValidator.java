package uk.nhs.digital.common.validators;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.stream.Collectors.toList;
import static uk.nhs.digital.ExceptionUtils.wrapCheckedException;

import com.google.common.collect.ImmutableList;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.ValidationContextException;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * <p>
 * Validates that validated text contains no patterns that match any of the configured ones.
 * Matching is case-insensitive.
 * </p><p>
 * Configure regular expressions using {@code forbiddenPatterns} property. Example:
 * </p>
 * <pre>
 * /hippo:configuration/hippo:modules/validation/hippo:moduleconfig/naughty-words:
 *   hipposys:className: uk.nhs.digital.common.validators.NegativeRegExpValidator
 *   forbiddenPatterns:
 *   - crumbs
 *   - pants
 *   - \bscript\b
 * </pre>
 */
public class NegativeRegExpValidator implements Validator<String> {

    public static final String FORBIDDEN_PATTERNS_PROPERTY_NAME = "forbiddenPatterns";
    public static final Function<String, Pattern> TO_CASE_INSENSITIVE_REGEX = regex -> Pattern.compile(regex, CASE_INSENSITIVE);

    private final List<Pattern> forbiddenPatterns;

    public NegativeRegExpValidator(final Node config) {
        forbiddenPatterns = forbiddenPatternsFrom(config);
    }

    @Override
    public Optional<Violation> validate(final ValidationContext context, final String text) {
        return violationIfAnyForbiddenPatternsPresentInText(context, text);
    }

    private Optional<Violation> violationIfAnyForbiddenPatternsPresentInText(final ValidationContext context, final String text) {
        return forbiddenPatterns.stream()
            .filter(forbiddenWord -> containedIn(forbiddenWord, text))
            .findAny()
            .map(forbiddenWord -> context.createViolation());
    }

    private List<Pattern> forbiddenPatternsFrom(final Node config) {
        try {
            return propertyStringValuesFrom(config, FORBIDDEN_PATTERNS_PROPERTY_NAME).stream()
                .map(TO_CASE_INSENSITIVE_REGEX)
                .collect(toList());
        } catch (final Exception e) {
            throw new ValidationContextException("Cannot read required property '" + FORBIDDEN_PATTERNS_PROPERTY_NAME + "'", e);
        }
    }

    private List<String> propertyStringValuesFrom(final Node config, final String propertyName) throws RepositoryException {
        return Optional.of(config.getProperty(propertyName))
            .map(property -> wrapCheckedException(property::getValues))
            .filter(values -> values.length > 0)
            .map(Arrays::asList)
            .orElse(ImmutableList.of())
            .stream()
            .map(value -> wrapCheckedException(value::getString))
            .collect(toList());
    }

    private boolean containedIn(final Pattern forbiddenWord, final String text) {
        return forbiddenWord.matcher(text).find();
    }
}
