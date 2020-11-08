package linky.links;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateNewLinkTest {

    @Test
    void createWithCustomName() {
        final var links = new FakeLinks();
        final var useCase = new CreateNewLinkUseCase(links);
        final var expectedName = "test-name";

        final var linkName = useCase.create(
            new NewLink(
                new Name.Unvalidated(expectedName),
                new Url.Unvalidated("https://www.google.com")));

        assertThat(linkName)
            .hasToString(expectedName);
    }

    @Test
    void createWithRandomName() {
        final var links = new FakeLinks();
        final var useCase = new CreateNewLinkUseCase(links);

        final var link = useCase.create(
            new NewLink(
                new Name.Unvalidated("test-name"),
                new Url.Unvalidated("https://www.google.com")));

        assertThat(link)
            .isNotNull();
        assertThat(link.toString())
            .hasSizeGreaterThan(0);
    }

    @ParameterizedTest
    @MethodSource("abusiveNames")
    void abusiveNamesAreRejected(final String name) {
        final var links = new FakeLinks();
        final var useCase = new CreateNewLinkUseCase(links);
        final var newLink = new NewLink(
            new Name.Unvalidated(name),
            new Url.Unvalidated("https://www.google.com"));

        assertThatThrownBy(() -> useCase.create(newLink))
            .isInstanceOf(Name.NameIsAbusive.class);
    }

    private static Stream<String> abusiveNames() {
        return Stream.of(
            "fucktionallity",
            "BadassHits"
        );
    }

    @ParameterizedTest
    @MethodSource("malformedUrls")
    void rejectsMalformedLinks(final String url) {
        final var links = new FakeLinks();
        final var useCase = new CreateNewLinkUseCase(links);

        final var newLink = new NewLink(
            new Name.Unvalidated("test-name"),
            new Url.Unvalidated(url));

        assertThatThrownBy(() -> useCase.create(newLink))
            .isInstanceOf(Url.MalformedUrl.class);
    }

    private static Stream<String> malformedUrls() {
        return Stream.of(
            "test-url",
            "https://",
            "https",
            "http//google",
            "www.google"
        );
    }

    private static class FakeLinks implements Links {
        @Override
        public void add(final Link newLink) {}

        @Override
        public Optional<Link> findBy(final Name linkName) {
            return Optional.empty();
        }

        @Override
        public Stream<Link> all() {
            return Stream.empty();
        }
    }

}
