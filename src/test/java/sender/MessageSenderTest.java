package sender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTest {

    @Getter
    @AllArgsConstructor
    static class LocationByIp{
        private Location location;
        private String ip;
        private String message;
    }

    @ParameterizedTest
    @MethodSource("getLocation")
    public void senderTest(LocationByIp locationByIp){
        String expectedMessage = locationByIp.getMessage();
        GeoService geoServiceMockito = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoServiceMockito.byIp(Mockito.any())).thenReturn(locationByIp.getLocation());
        LocalizationService localizationService = new LocalizationServiceImpl();

        MessageSender messageSender = new MessageSenderImpl(geoServiceMockito, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, locationByIp.getIp());

        String localMessage = messageSender.send(headers);
        Assertions.assertEquals(expectedMessage, localMessage);
    }

    public static Stream<LocationByIp> getLocation(){
        return Stream.of(
                new LocationByIp(new Location("Moscow", Country.RUSSIA, null, 0),
                        "172.0.32.11", "Добро пожаловать"),
                new LocationByIp(new Location("New York", Country.USA, null, 0),
                        "96.44.183.149", "Welcome")
        );
    }
}