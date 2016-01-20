package android.epsi.com.bebeer.bean;

/**
 * Created by fx on 20/01/16.
 */
public class BeerRequest {

    private int offset = 0;
    private int count = 20;
    private String requestByName = "";

    public BeerRequest() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRequestByName() {
        return requestByName;
    }

    public void setRequestByName(String requestByName) {
        this.requestByName = requestByName;
    }

    @Override
    public String toString() {
        return "BeerRequest{" +
                "offset=" + offset +
                ", count=" + count +
                ", requestByName='" + requestByName + '\'' +
                '}';
    }
}
