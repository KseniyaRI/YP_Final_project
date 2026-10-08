package praktikum.data;

import net.datafaker.Faker;
import praktikum.models.Ad;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AdGenerator {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);
    private static final String[] CATEGORIES = {
            "Авто", "Книги", "Садоводство", "Хобби", "Технологии"
    };
    private static final String[] CONDITIONS = {"Новый", "Б/У"};
    private static final String[] CITIES = {
            "Москва", "Санкт-Петербург", "Новосибирск",
            "Екатеринбург", "Нижний Новгород", "Казань"
    };

    private AdGenerator() {
    }

    public static Ad randomAd() {
        return new Ad(
                randomTitle(),
                pick(CATEGORIES),
                pick(CONDITIONS),
                pick(CITIES),
                FAKER.lorem().sentence(),
                FAKER.number().numberBetween(1, 10000),
                null
        );
    }

    public static String randomTitle() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return FAKER.lorem().word() + suffix;
    }

    private static String pick(String[] values) {
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}
