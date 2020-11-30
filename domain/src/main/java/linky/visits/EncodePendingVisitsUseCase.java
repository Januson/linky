package linky.visits;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Transactional
class EncodePendingVisitsUseCase implements EncodePendingVisits {
    private final PendingOrigins origins;
    private final GeoEncoder encoder;

    EncodePendingVisitsUseCase(final PendingOrigins origins,
                               final GeoEncoder encoder) {
        this.origins = origins;
        this.encoder = encoder;
    }

    @Override
    public void encode() {
        final var pendingOrigins = this.origins.all();
        final var encodedOrigins = this.encode(pendingOrigins);
        this.update(encodedOrigins);
    }

    private List<Origin.Encoded> encode(final List<Origin.Pending> pendingOrigins) {
        if (pendingOrigins.isEmpty()) {
            return Collections.emptyList();
        }
        return this.encoder.encoded(pendingOrigins);
    }

    private void update(final List<Origin.Encoded> encodedOrigins) {
        if (encodedOrigins.isEmpty()) {
            return;
        }
        this.origins.update(encodedOrigins);
    }

}
