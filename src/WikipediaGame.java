import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class WikipediaGame {

    private String path;
    private String inputURL = "https://en.wikipedia.org/wiki/Chris_Hemsworth";
    private String outputURL = "https://en.wikipedia.org/wiki/Logie_Awards_of_2005";
    //    private String outputURL =  "/wiki/Hypertext";
    private String currentURL = "";
    private boolean foundLink = false;
    private int depth = 0;
    //"/wiki/Hypertext"
    //private boolean foundLink = false;
    private int width0 = 0;
    private int width1 = 0;
    private int width2 = 0;
    private int width3 = 0;

    private String widthCurrent0 = inputURL;
    private String widthCurrent1;
    private String widthCurrent2;
    private String widthCurrent3;

    public static void main(String[] args) {
        WikipediaGame game = new WikipediaGame();
    }

    //wikipedia.wikipedia(inputURL,outputURL, depth);

    public WikipediaGame(){

        if (wikipedia(inputURL, outputURL, depth,new ArrayList<String>())){
//            System.out.println("found it");
//            System.out.println("deep: " + depth);
//            System.out.println("wide0: " + width0);
//            System.out.println("wide1: " + width1);
//            System.out.println("wide2: " + width2);
            //System.out.println("size: " + listURL.size());

        } else {
            //System.out.println("could not find outputURL with this depth");
        }

//        boolean found = wikipedia("https://en.wikipedia.org/wiki/Wikipedia:Wiki_Game", "/wiki/Hypertext",0);
//        System.out.println(found);
    }

    public boolean wikipedia(String inputURL, String outputURL, int depth, ArrayList<String> path) {

        //System.out.println(inputURL + "  " + outputURL);
        //inputURL = "https://en.wikipedia.org/wiki/Wordle";
        if (path.size() == 0) {
            path.add(inputURL);
        }

        if (inputURL.equals(outputURL)) {
            System.out.println("found it");
            System.out.println(depth);
            //System.out.println("path:" + widthCurrent0 + " > " + widthCurrent1 + " > " + widthCurrent2 + " > " + widthCurrent3);
            System.out.println("path: ");
            for(String s : path){
                System.out.print(s + " > ");
            }
            foundLink = true;
            return true;
        } else if (depth == 3) {
            return false;
        } else {
            ArrayList<String>listURL = readHTML(inputURL);
//            System.out.println(inputURL);
            for (int i = 0; i < listURL.size(); i++) {

                currentURL = "https://en.wikipedia.org" + listURL.get(i);
                ArrayList<String> temp = (ArrayList<String>) path.clone();
                temp.add(currentURL);
                wikipedia(currentURL, outputURL, depth + 1, temp);

                if (foundLink == true){
                    break;
                }

                //modify to add wiki later when searching link

                 //if false then shifts over to the next branch

                    //only save path of last url

                    //System.out.println(currentURL);

                    //set currenturl to something

                    if(depth == 0){
                        //pathURL.add(currentURL);
                        widthCurrent0 = currentURL;
                        width0++;
                        //System.out.println("000000000");
                    }
                    else if(depth == 1){
                        widthCurrent1 = currentURL;
                        width1++;
                        //System.out.println("111111111");
                    }
                    else if(depth == 2){
                        widthCurrent2 = currentURL;
                        width2++;
                       //System.out.println("222222222");
                    } else if (depth == 3) {
                        widthCurrent3 = currentURL;
                        width3++;
                        //System.out.println("3333333333");
                    }
                    //differentiate inputurl and currenturl
                    //System.out.println(listURL);
                    //wikipedia(inputURL, outputURL, depth + 1);


                    //path = pathURL.toString();
            }
            return false;
        }
//        System.out.println("-------");
//        return false;
    }

    public ArrayList<String> readHTML(String input) { //takes the page and takes all the links from it

        ArrayList<String> list = new ArrayList<String>();

        try {
//                System.out.println("-");
            URL url = new URL(input);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {

                //System.out.println(line);

                while(line.contains("href=\"/wiki/")) {
                    int indexStart = line.indexOf("href", 0) + 6;
                    int indexEnd = line.indexOf("\"", indexStart);

                        if (indexStart >= 0 && indexEnd >= 0) {
                            String lineCheck = line.substring(indexStart,indexEnd);
                            if (lineCheck.contains("/wiki/Wikipedia:") || lineCheck.contains("/wiki/Help:") || lineCheck.contains("/wiki/File:") || lineCheck.contains("/wiki/Template:") || lineCheck.contains("/wiki/Special:") || lineCheck.contains("/wiki/Main_Page") || lineCheck.contains("/wiki/User:") || lineCheck.contains("/wiki/User_talk:") || lineCheck.contains("/wiki/Wikipedia_talk:") || lineCheck.contains("/wiki/Category_talk:") || lineCheck.contains("/wiki/Category:") || lineCheck.contains("/wiki/Talk:") || lineCheck.contains("/wiki/Portal:") || lineCheck.contains("/wiki/Template_talk:")) {
                                //nothing happens
                            } else if (lineCheck.startsWith("/wiki/")) {
                                list.add(line.substring(indexStart, indexEnd));

//                            if(line.substring(indexStart, indexEnd).contains(outputURL)){
//                                System.out.println(line.substring(indexStart,indexEnd));

                               // System.out.println(listURL);

                                //System.out.println(input + outputURL);

                                if (input.equals(outputURL)) {
                                    break;
                                }
                            }

                        }
                    line = line.substring(indexEnd);
                }

                //System.out.println(listURL);
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return list;
//        for(String a: listURL)
//        {
//            System.out.println(a);
//        }
    }
}