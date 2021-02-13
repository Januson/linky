package linky.persistence.visits;

import linky.visits.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

interface VisitsRepository extends JpaRepository<VisitEntity, Long> {

    Stream<OriginView> findAllByGeoEncoding(Origin.GeoEncoding geoEncoding);

    @Modifying
    @Query("update VisitEntity v set v.geoEncoding = :state, v.originCountry = :country where v.originAddress = :ip and v.geoEncoding = 'PENDING'")
    void updateOrigin(
        @Param(value = "state") Origin.GeoEncoding state,
        @Param(value = "country") String country,
        @Param(value = "ip") String ip);

    List<VisitEntity> findAllByDestination(String destination);
}
