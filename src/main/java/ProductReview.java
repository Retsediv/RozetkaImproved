import java.util.HashMap;

public class ProductReview {

    private HashMap<String, String> details;

    public ProductReview(HashMap<String, String> details){
        this.details = details;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }

}
