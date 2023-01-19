package i18n;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LocalizationServiceTest {
    @Getter
    @AllArgsConstructor
    static class GeoLocal{
        private Country country;
        private String greeting;
    }
    static LocalizationService sut;

    @BeforeAll
    public static void initialLocalizationService(){
        sut = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getSource")
    public void localizationServiceTest(GeoLocal geoLocal){
        System.out.println(geoLocal);
        String messageExpected = geoLocal.getGreeting();

        String messageActual = sut.locale(geoLocal.getCountry());
        Assertions.assertEquals(messageExpected, messageActual);
    }

    public static Stream<GeoLocal> getSource(){
         List<GeoLocal>geoLocals = new ArrayList<>();
         Arrays.stream(Country.values()).
                 forEach(x->geoLocals.add(new GeoLocal(x, x == Country.RUSSIA ? "Добро пожаловать": "Welcome")));
         return geoLocals.stream();
    }
}
