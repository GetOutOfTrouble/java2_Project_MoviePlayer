package MVC;
public class film {
    private String filmName;//name
    private String lag;//language
    private String country;//country
    private String duration;//duration
    private String director;//director


    public String getFilmName() {
        return filmName;
    }

    public String getLag() {
        return lag;
    }

    public String getCountry() {
        return country;
    }

    public String getDuration() {
        return duration;
    }

    public String getDirector() {
        return director;
    }

    /**
     *
     * @param filmName :name of this film
     * @param lag : language of this film
     * @param country : country
     * @param duration : how long the movie takes
     * @param director : directed by
     */
    public film(String filmName, String lag, String country, String duration, String director){
        this.filmName = filmName;
        this.lag = lag;
        this.country = country;
        this.duration = duration;
        this.director = director;
    }


	 
}

 
