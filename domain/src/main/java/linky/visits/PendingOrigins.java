package linky.visits;

import java.util.List;

public interface PendingOrigins {
    List<Origin.Pending> all();

    void update(List<Origin.Encoded> origins);
}
