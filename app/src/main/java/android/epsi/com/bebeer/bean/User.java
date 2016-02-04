package android.epsi.com.bebeer.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    /**
     * (Required)
     */
    @SerializedName("username")
    @Expose
    private String username = "";
    /**
     * (Required)
     */
    @SerializedName("email")
    @Expose
    private String email = "";
    /**
     * (Required)
     */
    @SerializedName("password")
    @Expose
    private String password = "";

    public User() {
    }

    /**
     * (Required)
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * (Required)
     *
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * (Required)
     *
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * (Required)
     *
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * (Required)
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * (Required)
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}