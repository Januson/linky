package linky.links;

import org.cactoos.Text;
import org.cactoos.text.Sub;

import java.util.UUID;

/**
 * Represents a randomly generated id.
 *
 * Note: There is a slight possibility of generating a conflicting id. This is
 * ok for the purposes of this application and such cases should be just retried.
 */
public class RandomId implements Text {

    private final Text origin;

    public RandomId() {
        this.origin = new Sub(() -> UUID.randomUUID().toString(), 0, 6);
    }

    @Override
    public String asString() throws Exception {
        return this.origin.asString();
    }

}
