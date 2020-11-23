package linky.web.visits;

import com.fasterxml.jackson.annotation.JsonInclude;

class OriginDto {
    private final String geoEncoding;
    private final String ip;
    private final String country;

    OriginDto(String geoEncoding, String ip, String country) {
        this.geoEncoding = geoEncoding;
        this.ip = ip;
        this.country = country;
    }

    public String getGeoEncoding() {
        return geoEncoding;
    }

    public String getIp() {
        return ip;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String getCountry() {
        return country;
    }

}