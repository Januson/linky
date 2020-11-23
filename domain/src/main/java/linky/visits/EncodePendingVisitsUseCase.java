package linky.visits;

import javax.transaction.Transactional;

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
        this.origins.update(
            this.encoder.encoded(this.origins.all())
        );
    }

}
