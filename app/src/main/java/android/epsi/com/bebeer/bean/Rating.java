
package android.epsi.com.bebeer.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class Rating {

    @SerializedName("beerId")
    @Expose
    private String beerId;
    @SerializedName("date")
    @Expose
    private DateTime date;
    @SerializedName("rate")
    @Expose
    private Integer rate;

    /**
     * @return The beerId
     */
    public String getBeerId() {
        return beerId;
    }

    /**
     * @param beerId The beerId
     */
    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    /**
     * @return The date
     */
    public DateTime getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(DateTime date) {
        this.date = date;
    }

    /**
     * @return The rate
     */
    public Integer getRate() {
        return rate;
    }

    /**
     * @param rate The rate
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "beerId='" + beerId + '\'' +
                ", date=" + date +
                ", rate=" + rate +
                '}';
    }
}
