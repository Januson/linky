package linky.geo;

import linky.visits.GeoEncoder;
import org.springframework.context.annotation.Bean;

public class GeoEncodingConfiguration {

    @Bean
    public GeoEncoder geoEncoder() {
        return new PartitioningEncoder(
            new FailSafeEncoder(
                new IpApiEncoder(),
                new AppIpApiEncoder()
            )
        );
    }

}
