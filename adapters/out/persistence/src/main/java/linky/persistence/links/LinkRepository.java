package linky.persistence.links;

import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<LinkEntity, Long> {
}
