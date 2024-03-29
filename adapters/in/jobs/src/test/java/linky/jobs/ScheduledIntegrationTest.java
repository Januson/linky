package linky.jobs;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduledIntegrationTest {

	@Autowired
	private PeriodicEncodePendingVisits task;

	@Autowired
	private SpyingEncodePendingVisits spy;

	@Test
	void triggersScheduledUseCase() {
		await().atMost(1, TimeUnit.SECONDS).until(() -> spy.wasRun());
	}
}
