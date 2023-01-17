package i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceTest {
    static LocalizationService sut;

    @BeforeAll
    public static void initialLocalizationService(){
        sut = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("getCountries")
    public void localizationServiceTest(Country countryExpected){
        String messageExpected;
        if (countryExpected == Country.RUSSIA)
            messageExpected = "Добро пожаловать";
        else
            messageExpected = "Welcome";

        String messageActual = sut.locale(countryExpected);
        Assertions.assertEquals(messageExpected, messageActual);
    }

    public static Stream<Arguments> getCountries(){
        Country[] countries = Country.values();
        return Stream.of(Arguments.of(countries));
    }
}
