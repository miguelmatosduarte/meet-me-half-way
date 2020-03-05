package meetmehalfway.model;

import meetmehalfway.model.api.result.Result;

public class City {
    private String cityID;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private String cityName;

    public City withName(String name) {
        this.cityName = name;
        return this;
    }

    public City withId(String id) {
        this.cityID = id;
        return this;
    }
}
