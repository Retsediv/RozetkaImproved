import org.jsoup.Jsoup;
import java.io.IOException;

abstract public class PaginatePageParser extends PageParser {

    protected int pagesCount;
    protected int currentPage = 0;

    public PaginatePageParser(String pageUrl){
        super(pageUrl);
        this.pagesCount = this.getNumberOfPages();
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage > this.pagesCount || currentPage <= 0){
            currentPage = 1;
        }

        try {
            String url = String.format(this.getPageUrl() + "/page=%s", currentPage);
            this.doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.currentPage = currentPage;
    }

    abstract public int getNumberOfPages();
}
