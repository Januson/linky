package linky.links;

import org.cactoos.text.UncheckedText;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneratingLinkIdsTest {

    @Test
    void generateSingleId() {
        final var randomId = new RandomId();

        assertThat(
            new UncheckedText(randomId).asString()
        ).hasSize(6);
    }

}
