package linky.visits;

import linky.links.Ip;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class EncodePendingVisitsTest {
    @Test
    void noPending() {
        final var encoder = new FailingEncoder();
        final var origins = new FakeOrigins();
        final var useCase = new EncodePendingVisitsUseCase(origins, encoder);

        useCase.encode();

        assertThat(encoder.encoded()).isZero();
        assertThat(origins.updated()).isZero();
    }

    @Test
    void somePendingEncodingFailed() {
        final var encoder = new FailingEncoder();
        final var origins = new FakeOrigins(createPendingOrigins());
        final var useCase = new EncodePendingVisitsUseCase(origins, encoder);

        useCase.encode();

        assertThat(origins.updated()).isZero();
    }

    @Test
    void somePendingEncodingSuccessful() {
        final var encoder = new FakeEncoder();
        final var origins = new FakeOrigins(createPendingOrigins());
        final var useCase = new EncodePendingVisitsUseCase(origins, encoder);

        useCase.encode();

        assertThat(origins.updated()).isEqualTo(3);
    }

    private List<Origin.Pending> createPendingOrigins() {
        return List.of(
            new Origin.Pending(new Ip("8.8.8.8")),
            new Origin.Pending(new Ip("8.8.8.8")),
            new Origin.Pending(new Ip("8.8.8.8"))
        );
    }

    static class FakeOrigins implements PendingOrigins {
        private int updated;
        private final List<Origin.Pending> origins;

        FakeOrigins() {
            this(Collections.emptyList());
        }

        FakeOrigins(List<Origin.Pending> origins) {
            this.origins = origins;
        }

        @Override
        public List<Origin.Pending> all() {
            return this.origins;
        }

        @Override
        public void update(List<Origin.Encoded> origins) {
            this.updated += origins.size();
        }

        public int updated() {
            return updated;
        }
    }

    static class FailingEncoder implements GeoEncoder {
        private int encoded;

        public int encoded() {
            return encoded;
        }

        @Override
        public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
            this.encoded += origins.size();
            return Collections.emptyList();
        }
    }

    static class FakeEncoder implements GeoEncoder {
        @Override
        public List<Origin.Encoded> encoded(List<Origin.Pending> origins) {
            return origins.stream()
                .map(this::toEncoded)
                .collect(Collectors.toList());
        }

        private Origin.Encoded toEncoded(final Origin origin) {
            return new Origin.Encoded(origin.ip(), new Country("Canada"));
        }
    }

}
