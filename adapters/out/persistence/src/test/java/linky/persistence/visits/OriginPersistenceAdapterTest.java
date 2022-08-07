package linky.persistence.visits;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import linky.visits.Country;
import linky.visits.Origin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({OriginPersistenceAdapter.class, VisitMapper.class})
class OriginPersistenceAdapterTest {

	@Autowired
	private OriginPersistenceAdapter adapter;

	@Test
	@Sql("PreexistingVisits.sql")
	void findAllPendingVisits() {
		List<Origin.Pending> pendingOrigins = adapter.all();

		assertThat(pendingOrigins).hasSize(2);
	}

	@Test
	@Sql("PreexistingVisits.sql")
	void updatePendingVisits() {
		List<Origin.Encoded> encodedOrigins = adapter.all().stream().map(this::toEncoded)
				.collect(Collectors.toList());

		this.adapter.update(encodedOrigins);

		assertThat(this.adapter.all()).isEmpty();
	}

	private Origin.Encoded toEncoded(final Origin origin) {
		return new Origin.Encoded(origin.ip(), new Country("United States"));
	}
}
