package praktikum.context;

import praktikum.models.Ad;
import praktikum.models.User;

public class ScenarioContext {

    private User user;
    private String token;
    private Ad ad;
    private String nextAdTitle;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Integer getAdId() {
        return ad == null ? null : ad.getId();
    }

    public void setAdId(Integer id) {
        if (ad == null) {
            ad = new Ad();
        }
        ad.setId(id);
    }

    public String getNextAdTitle() {
        return nextAdTitle;
    }

    public void setNextAdTitle(String nextAdTitle) {
        this.nextAdTitle = nextAdTitle;
    }
}
