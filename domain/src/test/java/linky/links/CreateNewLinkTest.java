package linky.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CreateNewLinkTest {

    public static final String VALID_URL = "https://www.google.com";
    public static final IsNameUsed IS_NAME_IN_USE = name -> false;

    @Test
    void createWithCustomName() {
        final var links = new FakeLinks();
        final var useCase = createUseCase(links);
        final var expectedName = "test-name";

        final var linkName = useCase.create(createNewLink(VALID_URL, expectedName));

        assertThat(linkName).hasToString(expectedName);
        assertThat(links.size()).isPositive();
    }

    @Test
    void createWithRandomName() {
        final var links = new FakeLinks();
        final var useCase = createUseCase(links);

        final var link = useCase.create(createNewLink(VALID_URL, "test-name"));

        assertThat(link).isNotNull();
        assertThat(link.toString()).hasSizeGreaterThan(0);
        assertThat(links.size()).isPositive();
    }

    @ParameterizedTest
    @MethodSource("abusiveNames")
    void abusiveNamesAreRejected(final String name) {
        final var links = new FakeLinks();
        final var useCase = createUseCase(links);
        final var newLink = createNewLink(VALID_URL, name);

        assertThatThrownBy(() -> useCase.create(newLink)).isInstanceOf(Name.NameIsAbusive.class);
        assertThat(links.size()).isZero();
    }

    private static Stream<String> abusiveNames() {
        return Stream.of("fucktionallity", "BadassHits");
    }

    @Test
    void duplicateNamesAreRejected() {
        final var links = new FakeLinks();
        final var useCase = createUseCase(links, name -> true);
        final var newLink = createNewLink(VALID_URL, "name");

        assertThatThrownBy(() -> useCase.create(newLink)).isInstanceOf(Name.NameAlreadyInUse.class);
        assertThat(links.size()).isZero();
    }

    @ParameterizedTest
    @MethodSource("malformedUrls")
    void rejectsMalformedLinks(final String url) {
        final var links = new FakeLinks();
        final var useCase = createUseCase(links);

        final var newLink = createNewLink(url, "test-name");

        assertThatThrownBy(() -> useCase.create(newLink)).isInstanceOf(Url.MalformedUrl.class);
        assertThat(links.size()).isZero();
    }

    private static Stream<String> malformedUrls() {
        return Stream.of("test-url", "https://", "https", "http//google", "www.google");
    }

    private CreateNewLinkUseCase createUseCase(final FakeLinks links) {
        return createUseCase(links, IS_NAME_IN_USE);
    }

    private CreateNewLinkUseCase createUseCase(final FakeLinks links, final IsNameUsed isNameInUse) {
        return new CreateNewLinkUseCase(links, isNameInUse);
    }

    private NewLink createNewLink(final String url, final String s) {
        return new NewLink(new Name.Unvalidated(s), new Url.Unvalidated(url));
    }

    private static class FakeLinks implements Links {

        private int count = 0;

        @Override
        public void add(final Link newLink) {
            this.count++;
        }

        @Override
        public Optional<Link> findBy(final Name linkName) {
            return Optional.empty();
        }

        @Override
        public Stream<Link> all() {
            return Stream.empty();
        }

        public long size() {
            return this.count;
        }
    }
}
