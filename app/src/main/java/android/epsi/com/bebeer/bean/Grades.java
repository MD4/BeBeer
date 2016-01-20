package android.epsi.com.bebeer.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grades {

    @SerializedName("taste")
    @Expose
    private Double taste;
    @SerializedName("thirsty")
    @Expose
    private Double thirsty;
    @SerializedName("bitterness")
    @Expose
    private Integer bitterness;

    /**
     * @return The taste
     */
    public Double getTaste() {
        return taste;
    }

    /**
     * @param taste The taste
     */
    public void setTaste(Double taste) {
        this.taste = taste;
    }

    /**
     * @return The thirsty
     */
    public Double getThirsty() {
        return thirsty;
    }

    /**
     * @param thirsty The thirsty
     */
    public void setThirsty(Double thirsty) {
        this.thirsty = thirsty;
    }

    /**
     * @return The bitterness
     */
    public Integer getBitterness() {
        return bitterness;
    }

    /**
     * @param bitterness The bitterness
     */
    public void setBitterness(Integer bitterness) {
        this.bitterness = bitterness;
    }

}
