package Main_Scene;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class film {
    /**
     * name
     */
    private final SimpleStringProperty filmName;
    /**
     * category
     */
    private final SimpleStringProperty category;
    /**
     * country
     */
    private final SimpleStringProperty country;
    /**
     * duration
     */
    private final SimpleStringProperty duration;
    /**
     * director
     */
    private final SimpleStringProperty director;

    /**
     * constructor
     *
     * @param filmName name of film
     * @param category category of film: "Action",...
     * @param country  country where the film produced
     * @param duration how long the film takes
     * @param director the director of this film
     */
    public film(String filmName, String category, String country, String duration, String director) {
        this.filmName = new SimpleStringProperty(filmName);
        this.category = new SimpleStringProperty(category);
        this.duration = new SimpleStringProperty(duration);
        this.director = new SimpleStringProperty(director);
        this.country = new SimpleStringProperty(country);
    }

    /**
     * void generator
     */
    public film() {
        this(null, null, null, null, null);
    }

    /**
     * @return film name
     */
    public String getFilmName() {

        return filmName.get();
    }

    /**
     * set film name
     *
     * @param filmName name
     */
    public void setFilmName(String filmName) {

        this.filmName.set(filmName);
    }

    /**
     * @return film name
     */
    public StringProperty filmNameProperty() {
        return filmName;
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category.get();
    }

    /**
     * @param category category
     */
    public void setCategory(String category) {
        this.category.set(category);
    }

    /**
     * @return category String property
     */
    public StringProperty categoryProperty() {
        return category;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country.get();
    }

    /**
     * @param country set country
     */
    public void setCountry(String country) {
        this.country.set(country);
    }

    /**
     * @return country property
     */
    public StringProperty countryProperty() {
        return country;
    }

    /**
     * @return duration
     */
    public String getDuration() {
        return duration.get();
    }

    /**
     * @param duration setter of duration
     */
    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    /**
     * @return duration property
     */
    public StringProperty durationProperty() {
        return duration;
    }

    /**
     * @return director
     */
    public String getDirector() {
        return director.get();
    }

    /**
     * @param director director setter
     */
    public void setDirector(String director) {
        this.director.set(director);
    }

    /**
     * @return director property
     */
    public StringProperty directorProperty() {
        return director;
    }


}

 
