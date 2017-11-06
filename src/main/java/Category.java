import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Category extends PageParser {
    ArrayList<Product> products = new ArrayList<>();

    private int pagesCount;
    private int currentPage;

    public Category(String pageUrl) {
        super(pageUrl);
        this.pagesCount = this.getNumberOfPages();
    }

    /**
     * Get number of pages in the catalog by parsing html
     * (fetch relevant block and get the last pagination item)
     *
     * @return pagesCount
     * Integer
     * @throws IOException
     */
    public int getNumberOfPages() {
        // get all pagination elements
        Elements paginationElement = this.doc.select(".paginator-catalog .paginator-catalog-l-i span");

        // fetch a value of the last element
        int pagesCount = Integer.parseInt(paginationElement.last().text());

        return pagesCount;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage > this.pagesCount || currentPage < 0){
            currentPage = 0;
        }

        String url = String.format(this.getPageUrl() + "/page=%s", currentPage);
        Document page = Jsoup.parse(url);

        this.currentPage = currentPage;
    }

    /**
     * Get all products of category on all pages
     *
     * @return
     */
    public ArrayList<Product> getAllProducts() {
        if(products.size() == 0) {
            for (int i = 0; i < pagesCount; i++) {
                products.addAll(this.getProductsOnPage(i + 1));
            }
        }

        return new ArrayList<>(products);
    }


    /**
     * Parse single page of products, get all of them here and parse data
     *
     * @param pageNumber Number of page category we want to parse
     * @return Array of HashMaps
     */
    public ArrayList<Product> getProductsOnPage(int pageNumber) {
        if(this.currentPage != pageNumber) {
            this.setCurrentPage(pageNumber);
        }

        ArrayList<Product> products = new ArrayList<>();
        Elements rawItems = this.doc.select(".g-i-tile-catalog");

        for (Element item : rawItems) {
            HashMap<String, String> shortDescription = new HashMap<>();

            shortDescription.put("title", item.select(".g-i-tile-i-title a").text());
            shortDescription.put("link", item.select(".g-i-tile-i-title a").attr("href"));
            shortDescription.put("image", item.select(".g-i-tile-i-image img").attr("src"));
            shortDescription.put("reviews_count", item.select(".g-rating > a").attr("data-count"));
            shortDescription.put("reviews_rate", item.select(".g-rating a .g-rating-stars-i").attr("style"));

            products.add(new Product(shortDescription, shortDescription.get("link")));
        }

        return products;
    }

}
