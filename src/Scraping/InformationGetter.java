package Scraping;

import afester.javafx.svg.SvgLoader;
import javafx.scene.Group;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.PropertyResourceBundle;


public class InformationGetter {
    /**
     * @param SourcePath svg source path
     * @param size:      0.1 is a common size for 500*500
     * @return the image of button
     * @throws FileNotFoundException if svg file path is not correct
     */
    public static Group svgGraphic(String SourcePath, double size) throws FileNotFoundException {
        InputStream svgFile = new FileInputStream(SourcePath);

        SvgLoader loader = new SvgLoader();
        Group svgImage = loader.loadSvg(svgFile);
        // Scale the image and wrap it in a Group to make the button
        // properly scale to the size of the image
        svgImage.setScaleX(size);
        svgImage.setScaleY(size);
        Group image = new Group(svgImage);
        image.setAutoSizeChildren(true);
        return image;
    }

    public static void imageWritter(String src, String dst,String name) throws IOException {
        URL imaUrl12 =new URL(
                src);
        URLConnection con = imaUrl12.openConnection();
        con.getInputStream();
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dst+name));
        BufferedInputStream in =  new BufferedInputStream(con.getInputStream());
        byte[] arr = new byte[in.available()];
        int read;
        while ((read = in.read(arr, 0, arr.length))!=-1) {
            out.write(arr,0,read);
        }
        in.close();
        in.close();
        out.flush();
        out.close();

    }

    public static PropertyResourceBundle PropertyReader(String PropertySourceFolder,String feature,String language) {
        PropertyResourceBundle a= null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(PropertySourceFolder+feature+"_"+language+".properties"),"UTF-8"))) {
            a = new PropertyResourceBundle(in);
        }  catch (IOException e) {
            System.err.println(" property file dose not exist : language : "+language);
            System.exit(1);
        }
        return a ;
    }

    public static InputStream openImageStream(String filePath) {
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            System.err.println("can not open image input stream: file can not find");
            e.printStackTrace();
        }
        return  in;
    }

}
