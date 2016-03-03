
package android.epsi.com.bebeer.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Ratings {

    @SerializedName("average")
    @Expose
    private Double average;
    @SerializedName("last")
    @Expose
    private List<Last> last = new ArrayList<Last>();

    /**
     * @return The average
     */
    public Double getAverage() {
        return average;
    }

    /**
     * @param average The average
     */
    public void setAverage(Double average) {
        this.average = average;
    }

    /**
     * @return The last
     */
    public List<Last> getLast() {
        return last;
    }

    /**
     * @param last The last
     */
    public void setLast(List<Last> last) {
        this.last = last;
    }

}
