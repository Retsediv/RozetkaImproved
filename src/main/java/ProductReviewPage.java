import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductReviewPage extends PaginatePageParser {

    private ArrayList<ProductReview> reviews = new ArrayList<>();

    public ProductReviewPage(String pageUrl){
        super(pageUrl + "comments/");
    }

    @Override
    public int getNumberOfPages() {
        // get number of pages with reviews
        int numberOfPages = 0;

        if(!this.doc.select(".paginator-catalog-l-i:last-child span").text().equals("")){
            numberOfPages = Integer.parseInt(this.doc.select(".paginator-catalog-l-i:last-child span").text());
        }

        return numberOfPages;
    }

    public ArrayList<ProductReview> getAllProductReviews() {
        if(this.reviews.size() == 0){
            // iter through pages and get all reviews on the page
            for (int i = 0; i < this.pagesCount; i++) {
                ArrayList<ProductReview> reviewsOnPage = this.getProductReviewsOnPage(i+1);
                this.reviews.addAll(reviewsOnPage);
            }
        }

        return this.reviews;
    }

    public ArrayList<ProductReview> getProductReviewsOnPage(int pageNumber) {
        this.setCurrentPage(pageNumber);

        Elements rawReviews = this.doc.select(".pp-review-i");
        ArrayList<ProductReview> reviewsOnPage = new ArrayList<>();

        for (Element rawReview: rawReviews){
            HashMap<String, String> reviewDetails = new HashMap<>();

            String author = rawReview.select(".pp-review-author .pp-review-author-name").text();
            String status = rawReview.select(".pp-review-author .pp-review-buyer-note").text();
            reviewDetails.put("date", rawReview.select(".pp-review-date .pp-review-date-text").text());
            reviewDetails.put("rate", rawReview.select(".g-rating-stars-i").attr("content"));
            reviewDetails.put("full_review", rawReview.select(".pp-review-text > div:first-child").text());
            reviewDetails.put("full_review", rawReview.select(".pp-review-text > div:first-child").text());
            reviewDetails.put("pros", rawReview.select(".pp-review-text > div:nth-child(2)").text());
            reviewDetails.put("cons", rawReview.select(".pp-review-text > div:nth-child(3)").text());

            reviewsOnPage.add(new ProductReview(author, status, reviewDetails));
        }

        return reviewsOnPage;
    }

    public ArrayList<ProductReview> getReviews() {
        return new ArrayList<>(reviews);
    }

}
