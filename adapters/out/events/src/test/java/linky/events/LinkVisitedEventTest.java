package linky.events;

import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.AddNewVisit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LinkVisitedEventTest {

    @Autowired
    private AddNewVisit visits;
    @Autowired
    private LinkVisitPublisher publisher;

    @Test
    void visitedLinkResultsInANewVisit() {
        this.publisher.fire(new LinkVisited(new Ip(), new Name("test_name")));

        verify(this.visits).add(any(LinkVisited.class));
    }

}
