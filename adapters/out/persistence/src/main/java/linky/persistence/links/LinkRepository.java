package linky.persistence.links;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface LinkRepository extends JpaRepository<LinkEntity, Long> {
	Optional<LinkEntity> findByShortcut(String shortcut);

	boolean existsByShortcut(String shortcut);
}
