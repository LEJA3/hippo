package uk.nhs.digital.apispecs;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.nhs.digital.apispecs.CommonmarkMarkdownConverterTest.Levels.levels;
import static uk.nhs.digital.test.util.RandomTestUtils.randomString;
import static uk.nhs.digital.test.util.TestFileUtils.contentOfFileFromClasspath;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import uk.nhs.digital.apispecs.commonmark.CommonmarkMarkdownConverter;
import uk.nhs.digital.apispecs.commonmark.MarkdownConversionException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(DataProviderRunner.class)
public class CommonmarkMarkdownConverterTest {

    private CommonmarkMarkdownConverter commonmarkMarkdownConverter;


    private static final String[] headings = new String[]{
        "nhsd-t-heading-xxl",
        "nhsd-t-heading-xl",
        "nhsd-t-heading-l",
        "nhsd-t-heading-m",
        "nhsd-t-heading-s",
        "nhsd-t-heading-xs"
    };

    @Before
    public void setUp() {
        initMocks(this);

        commonmarkMarkdownConverter = new CommonmarkMarkdownConverter();
    }

    @Test
    public void convertsMarkdownWithBackticksToHtmlWithCodeTags() {

        // given
        final String markdownWithBackticks = from("inline-code.md");
        final String expectedHtml = from("inline-code.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdownWithBackticks);

        // then
        assertThat(
            "Markdown with backticks is converted to HTML with <code> tags.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void convertsMarkdownWithGfmTablesToHtmlWithTables() {
        // GFM = GitHub Flavoured Markdown

        // given
        final String markdown = from("table.md");
        final String expectedHtml = from("html-with-table-tags.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown);

        // then
        assertThat(
            "Markdown with GFM tables converted to HTML with <table> tags.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void rendersHeadingIds_withCustomHeadingIds_autoGeneratedFromHeadingsTexts() {

        // given
        final String markdown = from("headings.md");
        final String expectedHtml = from("headings-with-ids.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown);

        // then
        assertThat(
            "Headings have id values autogenerated.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void rendersHeadingIds_withCustomHeadingIdPrefixes_whenProvided() {

        // given
        final String markdown = from("headings.md");
        final String expectedHtml = from("headings-with-prefixed-ids.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown, "customPrefix__", CommonmarkMarkdownConverter.NO_CHANGE);

        // then
        assertThat(
            "Heading id values are prefixed with provided prefix.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void rendersHeadingIds_addingSuffixes_whenDuplicateHeadingValues() {

        // given
        final String markdown = from("headings-with-duplicate-values.md");
        final String expectedHtml = from("headings-with-duplicate-values.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown, "customPrefix__", CommonmarkMarkdownConverter.NO_CHANGE);

        // then
        assertThat(
            "Heading id values are prefixed with provided prefix.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    @UseDataProvider("headingsLevels")
    public void rendersHeadings_withHeadingsHierarchyLevelsAdjustedViaParameter(
        final int targetTopHeadingLevel,
        final Levels initialHeadingLevels,
        final Levels expectedHeadingLevels
    ) {
        // given
        final String markdown = markdownWithHeadingsAt(initialHeadingLevels);

        final String expectedHtml = htmlWithHeadingsAt(expectedHeadingLevels);

        final String irrelevantHeadingIdPrefix = null;

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown, irrelevantHeadingIdPrefix, targetTopHeadingLevel);

        // then
        assertThat(
            "Headings are rendered with levels hierarchy adjusted so that the highest level is " + targetTopHeadingLevel + ".",
            actualHtml,
            is(expectedHtml)
        );
    }

    @DataProvider
    public static Object[][] headingsLevels() {
        // @formatter:off
        return new Object[][] {
            // targetTopHeadingLevel initialHeadingLevels                    expectedHeadingLevels

            // no change
            {0,                      levels(1,   2,   3,   4,   5,   6),     levels(1,   2,   3,   4,   5,   6)},
            {0,                      levels(3,   4,   5               ),     levels(3,   4,   5               )},
            {0,                      levels(2,   1,   2,   4,   5,   6),     levels(2,   1,   2,   4,   5,   6)},

            {1,                      levels(1,   2,   3,   4,   5,   6),     levels(1,   2,   3,   4,   5,   6)},
            {3,                      levels(3,   4,   3,   5,   6     ),     levels(3,   4,   3,   5,   6     )},
            {6,                      levels(6,   6                    ),     levels(6,   6                    )},

            // pushing down
            {2,                      levels(1,   2,   3,   4,   5,   6),     levels(2,   3,   4,   5,   6,   7)},
            {3,                      levels(1,   2,   3,   4,   5,   6),     levels(3,   4,   5,   6,   7,   8)},
            {4,                      levels(1,   2,   3,   4,   5,   6),     levels(4,   5,   6,   7,   8,   9)},
            {5,                      levels(1,   2,   3,   4,   5,   6),     levels(5,   6,   7,   8,   9,  10)},
            {6,                      levels(1,   2,   3,   4,   5,   6),     levels(6,   7,   8,   9,  10,  11)},
            {6,                      levels(6,   5,   4,   3,   2,   1),     levels(11,  10,  9,   8,   7,   6)},

            // pulling up
            {1,                      levels(2,   3,   4,   5,   6     ),     levels(1,   2,   3,   4,   5     )},
            {1,                      levels(4,   5,   4,   6          ),     levels(1,   2,   1,   3          )},
            {1,                      levels(3,   4,   4,   6          ),     levels(1,   2,   2,   4          )},
            {1,                      levels(6,   4,   3               ),     levels(4,   2,   1               )},
            {1,                      levels(6,   6                    ),     levels(1,   1                    )},

            {3,                      levels(4,   5,   6               ),     levels(3,   4,   5               )},
            {3,                      levels(6,   5,   6               ),     levels(4,   3,   4               )},
            {3,                      levels(6,   6                    ),     levels(3,   3                    )},
        };
        // @formatter:on
    }

    @Test
    public void throwsException_onNegativeTopHeadingLevel() {

        // given
        final String irrelevantMarkdown = randomString();
        final String irrelevantHeadingIdPrefix = randomString();
        final int illegalTopLevelHeading = -RandomUtils.nextInt();

        // when
        final ThrowingRunnable action = () ->
            commonmarkMarkdownConverter.toHtml(irrelevantMarkdown, irrelevantHeadingIdPrefix, illegalTopLevelHeading);

        // then
        final MarkdownConversionException actualException = assertThrows(
            MarkdownConversionException.class,
            action
        );

        assertThat(
            "Exception message provides failure's details.",
            actualException.getMessage(),
            is("Failed to convert Markdown " + irrelevantMarkdown)
        );

        assertThat(
            "Cause highlights argument validation error.",
            actualException.getCause().getMessage(),
            is("Argument topHeadingLevel has to be greater than or equal to zero but was " + illegalTopLevelHeading)
        );
    }

    private String htmlWithHeadingsAt(final Levels headingsLevels) {

        // for levels 1, 2, 3, returns HTML:
        // <h1 id="heading">Heading</h1>
        // <h2 id="heading">Heading</h2>
        // <h3 id="heading">Heading</h3>

        return headingsLevels.stream()
            .map(level -> format("<h{0} class=\"{1}\">Heading</h{0}>", level, cssClassForHeadingLevel(level)))
            .collect(joining("\n"));
    }

    private String cssClassForHeadingLevel(int level) {
        return level < 1
            ? headings[0]
            : level > 6 ? headings[5] : headings[level - 1];
    }

    private String markdownWithHeadingsAt(final Levels headingsLevels) {
        // for levels 1, 2, 3, returns Markdown:
        // # Heading\n
        // # Heading\n
        // # Heading

        return headingsLevels.stream()
            .map(level -> {
                final String prefix = rangeClosed(1, level).mapToObj(i -> "#").collect(joining());
                return format("{0} Heading", prefix);
            })
            .collect(joining("\n"));
    }

    private String from(final String testDataFileName) {
        return contentOfFileFromClasspath(
            "/test-data/api-specifications/CommonmarkMarkdownConverterTest/" + testDataFileName
        );
    }

    public static class Levels {
        private final List<Integer> levels;

        private Levels(final int... levels) {
            this.levels = Arrays.stream(levels).boxed().collect(toList());
        }

        public static Levels levels(int... levels) {
            return new Levels(levels);
        }

        public Stream<Integer> stream() {
            return levels.stream();
        }

        @Override public String toString() {
            return levels.stream().map(Object::toString).collect(joining(", ", "[", "]\n"));
        }
    }
}
