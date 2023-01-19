package geo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceTest {
    @Getter
    @AllArgsConstructor
    static class GeoValue{
        private Location location;
        private String ip;
    }

    static GeoService sut;

    @BeforeAll
    public static void initialGeoService() {
        sut = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getSource")
    public void byIpTest(GeoValue geoValue) {
        Location locationExpected = geoValue.getLocation();
        Location locationActual = sut.byIp(geoValue.getIp());

        Assertions.assertEquals(locationExpected.getCountry(), locationActual.getCountry());
        Assertions.assertEquals(locationExpected.getCity(), locationActual.getCity());
        Assertions.assertEquals(locationExpected.getStreet(), locationActual.getStreet());
        Assertions.assertEquals(locationExpected.getBuiling(), locationActual.getBuiling());
    }
    @Test
    public void byCoordinatesTest() {
        final double latitudeExpected = Math.random();
        final double longitudeExpected = Math.random();
        Assertions.assertThrows(RuntimeException.class, () -> {
            Location locationActual = sut.byCoordinates(latitudeExpected, longitudeExpected);
        });
    }

    public static Stream<GeoValue>getSource(){
        return Stream.of(
                new GeoValue(new Location(null, null, null, 0),
                        "127.0.0.1"),
                new GeoValue(new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                        "172.0.32.11"),
                new GeoValue(new Location("New York", Country.USA, " 10th Avenue", 32),
                        "96.44.183.149"),
                new GeoValue(new Location("Moscow", Country.RUSSIA, null, 0),
                        "172."),
                new GeoValue(new Location("New York", Country.USA, null, 0),
                        "96.")
        );
    }
}
