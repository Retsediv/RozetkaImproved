import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        process();
    }

    static void process() {
        System.out.println("Start parsing...\n");

        RozetkaParser parser = new RozetkaParser();
        Category category = parser.getCategory("https://rozetka.com.ua/all-tv/c80037");
        String path = "data/";

        ArrayList<Product> products = category.getAllProducts();
        int maxReviewsCounter = -1;
        String maxReviewedProductName = "is not defined";

        System.out.println("All products are parsed\n");
        System.out.println("Start writing to the file");

        for (Product product : products) {
            System.out.println("Processing: " + product.getTitle());

            if (!product.getReviewsCount().equals("") && Integer.parseInt(product.getReviewsCount()) > maxReviewsCounter) {
                maxReviewedProductName = product.getTitle();
            }

            String filename = String.format("%s/%s.csv",
                    path, product.getTitle().replace(" ", "-").replace("/", "-"));

            FileWriter writer = null;
            try {
                writer = new FileWriter(filename, true);

                writer.write(product.getTitle());
                writer.write("\r\n\r\n\r\n");
                // Write Characteristics
                for (Map.Entry<String, String> entry : product.getFullInfo().entrySet()) {
                    writer.write("\"" + entry.getKey() + "\"");
                    writer.write(",");
                    writer.write("\"" + entry.getValue() + "\"");
                    writer.write("\r\n");
                }

                // spacing between blocks of content
                writer.write("\r\nReviews:\r\n");

                // Write Reviews
                for (ProductReview review : product.getProductReviewsPage().getAllProductReviews()) {
                    writer.write("Review:\r\n");
                    for (Map.Entry<String, String> entry : review.getDetails().entrySet()) {
                        if (!entry.getValue().equals("")) {
                            writer.write("\"" + entry.getKey() + "\"");
                            writer.write(",");
                            writer.write("\"" + entry.getValue() + "\"");
                            writer.write("\r\n");
                        }
                    }
                    writer.write("\r\n");
                }

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            System.out.println("Parsing is successfully done!");

            // research
            String filename = String.format("%s/%s.txt", path, "result");

            try {
                FileWriter writer = new FileWriter(filename, false);
                writer.write("The most reviewed product: " + maxReviewedProductName);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("The most reviewed product: " + maxReviewedProductName);

    }
}
