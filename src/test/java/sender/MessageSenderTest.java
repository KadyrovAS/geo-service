package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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
    @ParameterizedTest
    @ValueSource(strings = {"172.0.32.11", "96.44.183.149"})
//    @MethodSource("getIp")
    public void sender(String ip){
        String expectedMessage = getMessage(ip);
        GeoService geoServiceMockito = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoServiceMockito.byIp(ip)).thenReturn(getLocation(ip));
        LocalizationService localizationService = new LocalizationServiceImpl();

        MessageSender messageSender = new MessageSenderImpl(geoServiceMockito, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String localMessage = messageSender.send(headers);
        Assertions.assertEquals(expectedMessage, localMessage);
    }

    public Location getLocation(String ip){
        if (ip.startsWith("172"))
            return new Location("Moscow", Country.RUSSIA, null, 0);
        else if(ip.startsWith("96"))
            return new Location("New York", Country.USA, null, 0);
        else return null;
    }

    public String getMessage(String ip){
        if (ip.startsWith("172"))
            return "Добро пожаловать";
        else if(ip.startsWith("96"))
            return "Welcome";
        else return null;
    }

    public static Stream<Arguments>getIp(){
        return Stream.of(Arguments.of("172.0.32.11", "96.44.183.149"));
    }
}
