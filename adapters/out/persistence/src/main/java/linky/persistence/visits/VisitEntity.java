package linky.persistence.visits;

import linky.visits.Origin;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "visit")
class VisitEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String destination;
    @Column
    @Enumerated(EnumType.STRING)
    private Origin.GeoEncoding state;
    @Column
    private String ip;
    @Column
    private String country;

    protected VisitEntity() {}

    VisitEntity(final String destination, final String ip) {
        this.destination = destination;
        this.ip = ip;
        this.state = Origin.GeoEncoding.PENDING;
        this.country = null;
    }

    public String getDestination() {
        return destination;
    }

    public Origin.GeoEncoding getState() {
        return state;
    }

    public String getIp() {
        return ip;
    }

    public String getCountry() {
        return country;
    }

}
