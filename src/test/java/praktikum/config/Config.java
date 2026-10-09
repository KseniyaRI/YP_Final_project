package praktikum.config;

public class Config {

    public static final String BASE_URL = "https://qa-desk.education-services.ru";
    public static final String SIGNUP = "/api/signup";
    public static final String CREATE_LISTING = "/api/create-listing";
    public static final String LISTINGS = "/api/listings";
    public static final String PROFILE_LISTINGS = "/api/profile/listings";
    public static final String BROWSER = System.getProperty("browser", "chrome");

    private Config() {
    }
}
