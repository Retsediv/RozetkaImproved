import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Product extends PageParser {

    private HashMap<String, String> shortInfo;
    private HashMap<String, String> fullInfo = new HashMap<>();
    private ArrayList<ProductReview> reviews = new ArrayList<>();
    private ProductReviewPage productReviewsPage;

    public Product(HashMap<String, String> shortInfo, String link){
        super(link);
        this.shortInfo = shortInfo;
        this.productReviewsPage = new ProductReviewPage(link);
    }

    public HashMap<String, String> getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(HashMap<String, String> shortInfo) {
        this.shortInfo = shortInfo;
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
