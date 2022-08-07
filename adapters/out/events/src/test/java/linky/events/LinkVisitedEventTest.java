package linky.events;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import linky.links.Ip;
import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Origin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LinkVisitedEventTest {

	@Autowired
	private SpyingAddNewVisit visits;

	@Autowired
	private LinkVisitPublisher publisher;

	@Test
	void eventReachesTheEndpoint() {
		this.publisher.fire(
				new LinkVisited(new Name("test_name"), new Origin.Pending(new Ip("8.8.8.8"))));

		await().atMost(1, SECONDS).until(() -> this.visits.lastAdded() != null);
	}
}
