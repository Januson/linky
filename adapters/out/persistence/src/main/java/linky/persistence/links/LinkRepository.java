package linky.persistence.links;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByName(String name);
}
