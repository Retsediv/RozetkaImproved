import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

abstract public class PageParser {

    /**
     * Raw grabber html page
     */
    protected Document doc;

    /**
     * Link to the specific page
     */
    private String pageUrl;

    /**
     * Init class without setting the pageUrl
     *
     */
    public PageParser(){

    }

    /**
     * Init class and set categoryUrl
     *
     * @param pageUrl: link to web page
     */
    public PageParser(String pageUrl){
        this.setPageUrl(pageUrl);
    }

    /**
     * Get pageUrl
     *
     * @return pageUrl
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Set pageUrl and create instance of html parser(grabber)
     * @param pageUrl
     */
    public void setPageUrl(String pageUrl) {
        try {
            this.doc = Jsoup.connect(pageUrl).get();
            this.pageUrl = pageUrl;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


}
