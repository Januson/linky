package linky.persistence.links;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByShortcut(String shortcut);

    boolean existsByShortcut(String shortcut);
}
