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

    @Test
    void generatedIdsAreDifferent() {
        final var randomId = new RandomId();

        final var first = new UncheckedText(randomId).asString();
        final var second = new UncheckedText(randomId).asString();

        assertThat(first).isNotEqualTo(second);
    }

}
