import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class GetLink {
	public static void main(String[] args) {
		StringBuilder url = new StringBuilder("https://prnt.sc/zq21qj");
		try{
			Document document = Jsoup.connect(url.toString()).get();
			Element el = document.getElementsByClass("screenshot-image").get(0);
			System.out.println(el.attr("src"));
		} catch (IOException | IndexOutOfBoundsException ignored){}
	}
}
