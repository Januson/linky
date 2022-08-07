package linky.jobs;

import linky.visits.EncodePendingVisits;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PeriodicEncodePendingVisits {

	private final EncodePendingVisits useCase;

	public PeriodicEncodePendingVisits(final EncodePendingVisits useCase) {
		this.useCase = useCase;
	}

	@Scheduled(fixedRate = 300_000)
	public void encode() {
		this.useCase.encode();
	}
}
