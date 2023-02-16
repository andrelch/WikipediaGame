import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class WikipediaGame {

    ArrayList<String> listURL = new ArrayList<String>();
    private String inputURL = "https://en.wikipedia.org/wiki/Wikipedia:Wiki_Game";
    private String outputURL = "Hypertext";
    private boolean foundLink = false;

    private int branch = 0;

    public static void main(String[] args) {
        WikipediaGame wikipedia = new WikipediaGame();
        if (wikipedia.wikipedia("https://en.wikipedia.org/wiki/Wikipedia:Wiki_Game", "/wiki/Hypertext",0)) {
            System.out.println("found it");
            System.out.println(wikipedia.branch);
        } else{
            System.out.println("could not find outputURL with this depth");
        }

//        boolean found = wikipedia.wikipedia("https://en.wikipedia.org/wiki/Wikipedia:Wiki_Game", "/wiki/Hypertext",0);
//        System.out.println(found);
    }

    public boolean wikipedia(String inputURL, String outputURL, int depth){

        if(listURL.contains(outputURL)){
            //System.out.println("found it");
            //foundLink = true
            return true;
        }
        else if(depth == 1){
            return false;
        }
        else{
            try {
                System.out.println("-");
                URL url = new URL(inputURL);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream())
                );
                String line;
                while ( (line = reader.readLine()) != null ) {
                    //System.out.println(line);

                    if (line.contains("href=\"/wiki/")) {
                        int indexStart = line.indexOf("href=\"/wiki", 0) + 6;
                        int indexEnd = line.indexOf("\"", indexStart);
                        System.out.println(line.substring(indexStart,indexEnd));
                        listURL.add(line.substring(indexStart,indexEnd));
                        if (indexStart >= 0 && indexEnd >= 0) {
                            //htmlList.add(line.substring(indexStart, indexEnd));
//                            if(line.substring(indexStart, indexEnd).contains(outputURL)){
                            if (wikipedia(inputURL, outputURL,depth+1)){ //if false then shifts over to the next branch
                                //System.out.println("found link: " + outputURL);
                                //foundLink = true;
                                return true;
                            }
                            //System.out.println(line.substring(indexStart, indexEnd));
                            //System.out.println(htmlList);
                            /*else{
                                layer1.add(line.substring(indexStart, indexEnd));
                            }*/
                        }

                    }

                }
                reader.close();
            } catch(Exception ex) {
                System.out.println(ex);
            }
            return false;
        }
    }
}