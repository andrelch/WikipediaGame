import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class WikipediaGame {

    ArrayList<String> listURL = new ArrayList<String>();

    private String widthCurrent1;
    private String widthCurrent2;
    private String widthCurrent3;

    private String path;
    private String inputURL = "https://en.wikipedia.org/wiki/Wikipedia:Wiki_Game";
    private String outputURL = "https://en.wikipedia.org/wiki/E-text";
    //    private String outputURL =  "/wiki/Hypertext";
    private String currentURL = "";
    private int depth = 1;
    //"/wiki/Hypertext"
    //private boolean foundLink = false;
    private int width1 = 1;
    private int width2 = 1;
    private int width3 = 1;

    public static void main(String[] args) {
        WikipediaGame wikipedia = new WikipediaGame();
        if (wikipedia.wikipedia(wikipedia.inputURL, wikipedia.outputURL, wikipedia.depth)) {
            System.out.println("found it");
            System.out.println("deep: " + wikipedia.depth);
            System.out.println("wide1: " + wikipedia.width1);
            System.out.println("wide2: " + wikipedia.width2);
            System.out.println("wide3: " + wikipedia.width3);
            System.out.println("size: " + wikipedia.listURL.size());

            System.out.println("path:" + wikipedia.widthCurrent1 + " > " + wikipedia.widthCurrent2);

        } else {
            System.out.println("could not find outputURL with this depth");
        }

//        boolean found = wikipedia.wikipedia("https://en.wikipedia.org/wiki/Wikipedia:Wiki_Game", "/wiki/Hypertext",0);
//        System.out.println(found);
    }

    public boolean wikipedia(String inputURL, String outputURL, int depth) {

        ArrayList<String> pathURL = new ArrayList<String>();

        readHTML(inputURL);

        if (listURL.contains(outputURL)) {
            System.out.println("found it");
            //foundLink = true
            return true;
        } else if (depth > 1) {
            return false;
        } else {

            for (int i = 0; i < listURL.size(); i++) {

                inputURL = "https://en.wikipedia.org" + listURL.get(i);

                readHTML(inputURL);

//              wikipedia(inputURL, outputURL, depth + 1);


                //modify to add wiki later when searching link

                if (inputURL.equals(outputURL)) { //if false then shifts over to the next branch


                    //only save path of last url
                    return true;


                } else {

                    //System.out.println(currentURL);

                    wikipedia(inputURL, outputURL, depth + 1);

                    if(depth == 1){
                        //pathURL.add(currentURL);
                        widthCurrent1 = currentURL;
                        width1++;
                    }
                    else if(depth == 2){
                        widthCurrent2 = currentURL;
                        width2++;
                    }

                    //path = pathURL.toString();

                }
            }
            return false;
        }
    }

    public void readHTML(String input) { //takes the page and takes all the links from it

        currentURL = input;

        try {
//                System.out.println("-");
            URL url = new URL(input);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {

                //System.out.println(line);

                if (line.contains("href=\"/wiki/")) {
                    int indexStart = line.indexOf("href=\"/wiki/", 0) + 6;
                    int indexEnd = line.indexOf("\"", indexStart);
                    //System.out.println(line.substring(indexStart,indexEnd));
                    //listURL.add(line.substring(indexStart,indexEnd));

                    if (indexStart >= 0 && indexEnd >= 0) {
                        if (line.substring(indexStart, indexEnd).contains("/wiki/Wikipedia:") || line.substring(indexStart, indexEnd).contains("/wiki/Help:") || line.substring(indexStart, indexEnd).contains("/wiki/File:") || line.substring(indexStart, indexEnd).contains("/wiki/Template:") || line.substring(indexStart, indexEnd).contains("/wiki/Special:") || line.substring(indexStart, indexEnd).contains("/wiki/Main_Page") || line.substring(indexStart, indexEnd).contains("/wiki/User:") || line.substring(indexStart, indexEnd).contains("/wiki/User_talk:") || line.substring(indexStart, indexEnd).contains("/wiki/Wikipedia_talk:")) {
                            //nothing happens
                        } else {
                            listURL.add(line.substring(indexStart, indexEnd));

//                            if(line.substring(indexStart, indexEnd).contains(outputURL)){
//                                System.out.println(line.substring(indexStart,indexEnd));

                            //System.out.println(listURL);

                            //System.out.println(input + outputURL);

                            if (input.equals(outputURL)) {
                                break;
                            }

                                //wikipedia(inputURL,outputURL,depth+1);
//                                deep = 0;
//                                wide = 0;
                            //System.out.println(line.substring(indexStart, indexEnd));
                            //System.out.println(htmlList);
                            /*else{
                                layer1.add(line.substring(indexStart, indexEnd));
                            }*/
//                            wide++;
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}