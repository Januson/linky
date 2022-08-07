package linky.visits;

import java.util.List;
import linky.links.LinkVisited;
import linky.links.Name;

public interface FindAllVisits {
    List<LinkVisited> allOf(Name linkName);
}
