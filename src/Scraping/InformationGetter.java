package Scraping;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.PropertyResourceBundle;


public class InformationGetter {
    public static void main(String[] args) {
        try {
            imageWritter("https://upload.wikimedia.org/wikipedia/en/thumb/a/a5/Alexander_Nevsky_Poster.jpg/220px-Alexander_Nevsky_Poster.jpg"
            ,"C:\\Users\\Administrator\\Desktop\\searchformdefault\\img.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void imageWritter(String src, String dst) throws IOException {
        URL imaUrl = new URL(
                src);
        URL imaUrl12 = imaUrl;
        URLConnection con = imaUrl12.openConnection();
        con.getInputStream();
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dst));
        InputStream in =  con.getInputStream();
        byte[] arr = new byte[in.available()];
        int read =0;
        while ((read = in.read(arr, 0, arr.length))!=-1) {
            out.write(arr,0,read);
        }
        in.close();
        in.close();
        out.flush();
        out.close();

    }

    public static PropertyResourceBundle PropertyReader(String PropertySourceFolder,String language) {

        File f = new File(PropertySourceFolder);
        File target = null;
        for (File en :        f.listFiles()
             ) {
           String la =en.toString();
            if (la.substring(la.length() - 2).equals(language)) {
                target=en;
            }
        }
        PropertyResourceBundle a= null;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(target))) {
             a = new PropertyResourceBundle(in);
        }
        catch (FileNotFoundException e) {
            System.err.println("movie property file dose not exist");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println( e.getCause().toString());
        }
        return a ;
    }
}
