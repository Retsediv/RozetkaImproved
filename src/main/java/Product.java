import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Product extends PageParser {
    private HashMap<String, String> fullInfo = new HashMap<>();
    private ArrayList<ProductReview> reviews = new ArrayList<>();
    private ProductReviewPage productReviewsPage;
    private String title;
    private String image;
    private String reviewsCount;
    private String reviewsRate;

    public void setFullInfo(HashMap<String, String> fullInfo) {
        this.fullInfo = fullInfo;
    }

    public void setReviews(ArrayList<ProductReview> reviews) {
        this.reviews = reviews;
    }

    public void setProductReviewsPage(ProductReviewPage productReviewsPage) {
        this.productReviewsPage = productReviewsPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(String reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getReviewsRate() {
        return reviewsRate;
    }

    public void setReviewsRate(String reviewsRate) {
        this.reviewsRate = reviewsRate;
    }

    public Product(String link, String title, String image, String reviewsCount, String reviewsRate){
        super(link);

        this.productReviewsPage = new ProductReviewPage(link);
        this.title = title;
        this.image = image;
        this.reviewsCount = reviewsCount;
        this.reviewsRate = reviewsRate;
    }


    public HashMap<String, String> getFullInfo() {
        if(this.fullInfo.size() != 0){
            return this.fullInfo;
        }

        String url = this.getPageUrl() + "characteristics";
        Document productPage = null;

        HashMap<String, String> characteristics = new HashMap<>();

        try {
            productPage = Jsoup.connect(url).get();

            Elements characteristicsElements = productPage.select(".pp-characteristics-tab-i");

            for(Element characteristicsElement: characteristicsElements){
                characteristics.put(
                        characteristicsElement.select(".pp-characteristics-tab-i-title span").text(),
                        characteristicsElement.select(".pp-characteristics-tab-i-field a").text()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fullInfo = characteristics;

        return characteristics;
    }

    public ArrayList<ProductReview> getReviews() {
        return reviews;
    }

    public ProductReviewPage getProductReviewsPage() {
        return productReviewsPage;
    }
}
