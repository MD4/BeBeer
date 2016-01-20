package android.epsi.com.bebeer.bean.dto;

/**
 * Created by fx on 20/01/16.
 * Wrapper for a full params request
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

    public BeerRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getCount() {
        return count;
    }

    public BeerRequest setCount(int count) {
        this.count = count;
        return this;
    }

    public String getRequestByName() {
        return requestByName;
    }

    public BeerRequest setRequestByName(String requestByName) {
        this.requestByName = requestByName;
        return this;
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
