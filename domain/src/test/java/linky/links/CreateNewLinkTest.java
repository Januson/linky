package linky.links;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateNewLinkTest {

    @Test
    void create() {
        final var links = new InMemoryLinks();
        final var useCase = new CreateNewLinkUseCase(links);

        final var link = useCase.create(new NewLink());

        assertThat(link)
            .isNotNull();
    }

}
