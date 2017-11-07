import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Category extends PaginatePageParser {
    private ArrayList<Product> products = new ArrayList<>();

    public Category(String pageUrl) {
        super(pageUrl);
    }

    /**
     * Get number of pages in the catalog by parsing html
     * (fetch relevant block and get the last pagination item)
     *
     * @return pagesCount
     * Integer
     * @throws IOException
     */
    @Override
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

    /**
     * Get all products of category on all pages
     *
     * @return
     */
    public ArrayList<Product> getAllProducts() {
        if(products.size() == 0) {
            for (int i = 0; i < 1; i++) {
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
        this.setCurrentPage(pageNumber);

        ArrayList<Product> products = new ArrayList<>();
        Elements rawItems = this.doc.select(".g-i-tile-catalog");

        for (Element item : rawItems) {
            String link = item.select(".g-i-tile-i-title a").attr("href");

            if(link.equals(""))
                continue;

            String title = item.select(".g-i-tile-i-title a").text();
            String image = item.select(".g-i-tile-i-image img").attr("src");
            String reviews_count = item.select(".g-rating > a").attr("data-count");
            String reviews_rate = item.select(".g-rating a .g-rating-stars-i").attr("style");

            products.add(new Product(link, title, image, reviews_count, reviews_rate));
        }

        return products;
    }

}
