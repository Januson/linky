package linky.links;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateNewShortcutTest {

    @Test
    void nullLink() {
        final Link.Unvalidated unvalidated = new Link.Unvalidated("");

        assertThatThrownBy(unvalidated::valid)
            .isInstanceOf(Link.ValidationFailed.class);
    }

    @Test
    void emptyLink() {
        final Link.Unvalidated unvalidated = new Link.Unvalidated("");

        assertThatThrownBy(unvalidated::valid)
            .isInstanceOf(Link.ValidationFailed.class);
    }


    @Test
    void validLink() {
        final Link.Unvalidated unvalidated = new Link.Unvalidated("");

        assertThatThrownBy(unvalidated::valid)
            .isInstanceOf(Link.ValidationFailed.class);
    }

}
