import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Main {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-AAAA");
    public static void main(String[] args) {
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
        while (true){
            StringBuilder url = new StringBuilder("https://prnt.sc/");
            for (int i = 0; i < 6; i++) {
                url.append(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            try{
                Document document = Jsoup.connect(url.toString()).get();
                Element el = document.getElementsByClass("screenshot-image").get(0);
                downloadImage(el.attr("src"));
                System.out.print("✓");
            } catch (IOException | URISyntaxException | IndexOutOfBoundsException e){
                System.out.print("×");
            }
            try{
                Thread.sleep(2000);
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
        FileOutputStream fos = new FileOutputStream(formatter.format(now) + ".png");
        fos.write(response);
        fos.close();
    }
    public static String getPath() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    }
}