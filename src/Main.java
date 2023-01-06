import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    // Creating a date-time formatter
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-AA");
    // Creating a variable for code at the end of link
    static StringBuilder code = new StringBuilder();
    // Creating just a simple final link
    static final String url = "https://prnt.sc/";
    // Creating a final variable of all digits and numbers
    static final String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
    // Creating a Random type object
    static final Random r = new Random();
    // List of links which are contains useful photos
    static final List<String> banned = List.of(
            "https://i.imgur.com/819oVqT.jpg",
            "https://i.imgur.com/iXKeBIy.png",
            "https://i.imgur.com/HPMommG.jpg",
            "https://i.imgur.com/5PS14nh.jpg"
    );

    public static void main(String[] args) {
        // Main code executes in infinite loop
        while (true){
            // Recreate variable for code
            code = new StringBuilder();
            
            // Generating 6 random chars
            for (int i = 0; i < 6; i++) {
                code.append(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            
            // Some code throws exceptions, so it needs to be caught
            try{
                
                // Creating an object with type Document to get exact element from page
                Document document = Jsoup.connect(url + code.toString()).get();
                // Get img element from HTML page
                Element el = document.getElementsByClass("screenshot-image").get(0);
                
                // Download image from link, if it's not in list of banned image links
                if (!banned.contains(el.attr("src")))
                    downloadImage(el.attr("src"));
                else throw (new IOException());
                
                // If everything is fine, log it
                System.out.print("✓");
                // Wait some time between downloads to avoid blocking IP
                Thread.sleep(2000);
                
            } catch (URISyntaxException | IndexOutOfBoundsException e){
                // If something wrong, log it
                System.out.print("×");
                try{
                    Thread.sleep(500);
                } catch (InterruptedException ignored){}
                
            } catch (IOException e){
                // If caught banned image, make B output
                System.out.print("B");
            } catch (InterruptedException ignored){}
            
            try{
                Thread.sleep(500);
            } catch (InterruptedException ignored){}
        }
    }
    public static void downloadImage(String urlAddress) throws IOException, URISyntaxException {
        URL url = new URL(urlAddress);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf))){
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        LocalDateTime now = LocalDateTime.now();
        FileOutputStream fos = new FileOutputStream(formatter.format(now) + "_" + code + ".png");
        fos.write(response);
        fos.close();
    }
}