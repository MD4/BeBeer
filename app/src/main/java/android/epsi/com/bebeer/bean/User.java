
package android.epsi.com.bebeer.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ratings")
    @Expose
    private List<Rating> ratings = new ArrayList<>();
    @SerializedName("gravatar")
    @Expose
    private String gravatar;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * 
     * @param ratings
     *     The ratings
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * 
     * @return
     *     The gravatar
     */
    public String getGravatar() {
        return gravatar;
    }

    /**
     * 
     * @param gravatar
     *     The gravatar
     */
    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "ratings=" + ratings +
                '}';
    }
}
