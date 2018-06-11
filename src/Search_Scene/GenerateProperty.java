package Search_Scene;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class GenerateProperty {
    public static void main(String[] args) {
        String filename = "D:\\workspace\\java2_Project\\Properties\\English_中文English_中文.properties";
        Properties p = new Properties();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            p.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties name = new Properties();
        for (Object en : p.keySet()
                ) {
            if (find(en).toString().length() > 0) {
                name.put(en, find(en));
            } else {
                System.err.println("不是啊");
            }
        }
        try (BufferedWriter sw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\workspace\\java2_Project\\Properties\\Name_Film.properties"), "UTF-8"))) {
            name.store(sw, "电影名或图片名到文件的映射");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object find(Object en) {
        String path = "D:\\workspace\\java2_Project\\filmTrailer";
        String name = "";
        File films = new File(path);
        for (File ws : Objects.requireNonNull(films.listFiles())
                ) {

            String[] q = ws.toString().split("\\\\");
            String na = q[q.length - 1];
            if (na.charAt(0) == en.toString().charAt(0)) {
                q = na.split("_");
                q = q[0].split("-");
                String onlyName = q[0];
                en = en.toString().replace("_", "");
                if (onlyName.equals(en))
                    name = na;
            }
        }
        return name;
    }
}
