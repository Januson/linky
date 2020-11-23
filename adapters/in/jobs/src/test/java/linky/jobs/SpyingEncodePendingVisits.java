package linky.jobs;

import linky.visits.EncodePendingVisits;

public class SpyingEncodePendingVisits implements EncodePendingVisits {

    private boolean wasRun = false;

    @Override
    public void encode() {
        this.wasRun = true;
    }

    public boolean wasRun() {
        return wasRun;
    }

}
