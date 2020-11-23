package linky.persistence.visits;

import linky.links.LinkVisited;
import linky.links.Name;
import linky.visits.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

interface VisitsRepository extends JpaRepository<VisitEntity, Long> {

    Stream<OriginView> findAllByState(Origin.GeoEncoding state);

    @Modifying
    @Query("update VisitEntity v set v.state = :state, v.country = :country where v.ip = :ip and v.state = 'PENDING'")
    void updateOrigin(
        @Param(value = "state") Origin.GeoEncoding state,
        @Param(value = "country") String country,
        @Param(value = "ip") String ip);

    List<VisitEntity> findAllByDestination(String destination);
}
