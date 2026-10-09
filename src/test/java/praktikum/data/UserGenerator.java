package praktikum.data;

import net.datafaker.Faker;
import praktikum.models.User;

import java.util.Locale;
import java.util.UUID;

public class UserGenerator {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    private UserGenerator() {
    }

    public static User randomUser() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        String email = "praktikum_" + suffix + "@yandex.ru";
        String password = FAKER.internet().password(8, 16);
        return new User(email, password, password);
    }
}
