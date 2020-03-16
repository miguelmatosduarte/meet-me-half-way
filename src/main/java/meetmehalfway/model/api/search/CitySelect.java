package meetmehalfway.model.api.search;

public class CitySelect {

    private String id;
    private String countryName;
    private String cityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public CitySelect withid(String id){
        this.id = id;
        return this;
    }

    public CitySelect withCityName(String cityName){
        this.cityName = cityName;
        return this;
    }

    public CitySelect withCoutryName(String countryName){
        this.countryName = countryName;
        return this;
    }

}
