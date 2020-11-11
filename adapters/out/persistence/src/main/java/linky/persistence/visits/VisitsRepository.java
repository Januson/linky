package linky.persistence.visits;

import org.springframework.data.jpa.repository.JpaRepository;

interface VisitsRepository extends JpaRepository<VisitEntity, Long> {

}
