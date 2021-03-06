
package android.epsi.com.bebeer.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Beer {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("brewery")
    @Expose
    private String brewery;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("grades")
    @Expose
    private Grades grades;
    @SerializedName("notes")
    @Expose
    private List<String> notes = new ArrayList<String>();
    @SerializedName("fermentation")
    @Expose
    private String fermentation;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("ratings")
    @Expose
    private Ratings ratings;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     *     The brewery
     */
    public String getBrewery() {
        return brewery;
    }

    /**
     *
     * @param brewery
     *     The brewery
     */
    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    /**
     *
     * @return
     *     The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     *     The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     *     The grades
     */
    public Grades getGrades() {
        return grades;
    }

    /**
     *
     * @param grades
     *     The grades
     */
    public void setGrades(Grades grades) {
        this.grades = grades;
    }

    /**
     *
     * @return
     *     The notes
     */
    public List<String> getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     *     The notes
     */
    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    /**
     *
     * @return
     *     The fermentation
     */
    public String getFermentation() {
        return fermentation;
    }

    /**
     *
     * @param fermentation
     *     The fermentation
     */
    public void setFermentation(String fermentation) {
        this.fermentation = fermentation;
    }

    /**
     *
     * @return
     *     The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     *
     * @param shortDescription
     *     The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     *
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The ratings
     */
    public Ratings getRatings() {
        return ratings;
    }

    /**
     * @param ratings The ratings
     */
    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
