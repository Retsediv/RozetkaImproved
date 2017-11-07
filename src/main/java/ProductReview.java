import java.util.HashMap;

public class ProductReview {

    private String author;
    private String status;
    private HashMap<String, String> details;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }

    public ProductReview(String author, String status, HashMap<String, String> details){
        this.author = author;
        this.status = status;
        this.details = details;
    }

}
