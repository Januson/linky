package linky.visits;

import java.util.List;

public interface GeoEncoder {

    List<Origin.Encoded> encoded(List<Origin.Pending> origins);

}
