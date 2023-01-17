package geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceTest {
    static GeoService sut;

    @BeforeAll
    public static void initialGeoService() {
        sut = new GeoServiceImpl();
    }

    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1", "172.0.32.11", "96.44.183.149", "172.", "96."})
    public void byIpTest(String ip) {
        Location locationExpected = null;
        if (ip.equals("127.0.0.1"))
            locationExpected = new Location(null, null, null, 0);
        else if (ip.equals("172.0.32.11"))
            locationExpected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        else if (ip.equals("96.44.183.149"))
            locationExpected = new Location("New York", Country.USA, " 10th Avenue", 32);
        else if (ip.startsWith("172"))
            locationExpected = new Location("Moscow", Country.RUSSIA, null, 0);
        else if (ip.startsWith("96"))
            locationExpected = new Location("New York", Country.USA, null, 0);

        Location locationActual = sut.byIp(ip);
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
}
