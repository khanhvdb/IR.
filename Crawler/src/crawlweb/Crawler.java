package crawlweb;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.*;
import jdk.nashorn.internal.ir.ContinueNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Crawler {

  
    public static Vector VT = new Vector();

    public static void getContentfromLink() {
        int i = 0;
        int j = 1;
        for (i = 0; i < VT.size(); i++) {
            String sLink = (String) VT.get(i);
            System.out.println(i +":"+ sLink);
            Document docContent;
            try {
                docContent = Jsoup.connect(sLink).get();
                Element divContent = null;
                Element eTitle = docContent.getElementsByTag("title").first();
                String sTitle = eTitle.text();
                int iWeb = 0;
                if (sLink.indexOf("phimmoi") > 0) {
                    iWeb = 1;
                }
                if (sLink.indexOf("hdonline") > 0) {
                    iWeb = 2;
                }
                if (iWeb == 0) {
                    continue;
                }
                switch (iWeb) {
                    case 1:
                        divContent = docContent.getElementById("film-content");
                        break;
                    case 2:
                        divContent = docContent.getElementsByClass("tn-contentmt maxheightline-6").first();
                        break;
                }
                if (divContent != null) {
                    String mycontent = "Link:" + sLink + "\n" +"Title: "+sTitle+"Content:" +divContent;
                    sTitle = sTitle.replaceAll("[\\/:*?\"<>|]", "_");;
                    File file = new File("F:\\data\\" + sTitle);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    BufferedWriter bw = null;
                    FileWriter fw = new FileWriter(file);
                    bw = new BufferedWriter(fw);
                    bw.write(mycontent);
                    if (bw != null) {
                        bw.close();
                    }
                }

            } catch (Exception ex) {
                continue;
            }
        }
    }

    public void getAllLink(String s) {
        Document docContent2;
        try {
            docContent2 = Jsoup.connect(s).get();
            for (Element links : docContent2.select("a")) {
                String relHref = links.attr("href");
                String absHref = links.attr("abs:href");
                if (absHref.length() <= 3) {
                    continue;
                }
                //de qui
                if (!VT.contains(absHref)) {
                    VT.add(absHref);
                    System.out.println(absHref);
                }
                //De qui 
                // getAllLink(absHref);

            }
        } catch (Exception ex) {

        }
    }

    public void crawl() throws IOException {

        String line;
        try (
                InputStream fis = new FileInputStream("F:\\link.txt");
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);) {
            while ((line = br.readLine()) != null) {
                Document docContent;
                try {
                    docContent = Jsoup.connect(line).get();

                    for (Element links : docContent.select("a")) {
                        String relHref = links.attr("href");
                        String absHref = links.attr("abs:href");
                        if (absHref.length() <= line.length()) {
                            continue;
                        }
                        //de qui
                        if (!VT.contains(absHref)) {
                            VT.add(absHref);
                            System.out.println(absHref);
                        }
                        //De qui muc 1
                        // getAllLink(absHref);
                    }

                } catch (Exception ex) {
                    continue;
                }

            }
        } catch (Exception ex) {

        }


    }

    public static void main(String[] args) {
        //System.setProperty("file.encoding", "UTF-8");
        try {
            Crawler clawler = new Crawler();
            clawler.crawl();
            System.out.println(VT.size());
            getContentfromLink();
        } catch (Exception ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
