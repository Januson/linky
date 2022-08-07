package linky.persistence.visits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import linky.visits.Origin;

@Entity
@Table(name = "visit")
class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String destination;

	@Column(name = "geo_encoding")
	@Enumerated(EnumType.STRING)
	private Origin.GeoEncoding geoEncoding;

	@Column(name = "origin_address")
	private String originAddress;

	@Column(name = "origin_country")
	private String originCountry;

	protected VisitEntity() {}

	VisitEntity(final String destination, final String ip) {
		this.destination = destination;
		this.originAddress = ip;
		this.geoEncoding = Origin.GeoEncoding.PENDING;
		this.originCountry = null;
	}

	public String getDestination() {
		return this.destination;
	}

	public Origin.GeoEncoding getGeoEncoding() {
		return this.geoEncoding;
	}

	public String getOriginAddress() {
		return this.originAddress;
	}

	public String getOriginCountry() {
		return this.originCountry;
	}
}
