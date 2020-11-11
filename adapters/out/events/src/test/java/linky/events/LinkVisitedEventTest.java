package linky.events;

import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LinkVisitedEventTest {

    @Autowired
    private SpyingAddNewVisit visits;
    @Autowired
    private LinkVisitPublisher publisher;

    @Test
    void eventReachesTheEndpoint() {
        this.publisher.fire(new LinkVisited(new Ip(), new Name("test_name")));

        await().atMost(1, SECONDS)
            .until(() -> this.visits.lastAdded() != null);
    }

}
