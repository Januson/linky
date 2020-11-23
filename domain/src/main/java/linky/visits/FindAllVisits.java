package linky.visits;

import linky.links.LinkVisited;
import linky.links.Name;

import java.util.List;

public interface FindAllVisits {
    List<LinkVisited> allOf(Name linkName);
}
