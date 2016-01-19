package android.epsi.com.bebeer.beans;

/**
 * TODO: delete this and use real bean
 */
public class Beer {
    private String name;
    private String comment;

    public Beer() {
    }

    public Beer(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
