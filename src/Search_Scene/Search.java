package Search_Scene;

import Main_Scene.film;

import java.io.*;
import java.util.*;

public class Search {
    private final static String PropertiesPath = new File("Properties").getAbsolutePath() + "\\";

    /**
     * Search film information by film name
     * <p>
     * Search film name to find film information. Use properties file "Film_English.properties"
     * or "Film_zn.properties" to get information. Key is film name. Value is film information.
     *
     * @param filmname The name of the film to search
     * @param language English/英文/中文/Chinese
     * @return A String  consists of  the format film information of the searched film.
     */
    private static String seachByFilmName(String filmname, String language) {
        String filename = "";
        if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英文")) {
            filename = PropertiesPath + "Film_English.properties";
        }
        if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {
            filename = PropertiesPath + "Film_中文_old.properties";
        }

        Properties p = new Properties();
        FileInputStream fis;
        InputStreamReader isr;
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            p.load(br);
            if (p.containsKey(filmname)) {
                String film = p.getProperty(filmname);
                return film;
            } else {
                if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英文")) {
                    //do nothing
                }
                if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {
                    //do nothing
                }
            }
        } catch (FileNotFoundException e1) {
            System.out.println("The file contain director->film is not found");
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding is not right");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Search film name by director name
     * <p>
     * Search a certain director name to find his/her films.Use properties file "Director.properties"
     * or "Director_zn.properties" to get information. Key is director name. Value is film name.
     *
     * @param director The director name  to search
     * @param language English/英文/中文/Chinese
     * @return A String Array consists of  the film names of the searched director.
     */
    private static String[] seachByDirector(String director, String language) {
        String filename = "";
        if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英文")) {
            filename = PropertiesPath + "Director_English.properties";
        }
        if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {
            filename = PropertiesPath + "Director_中文.properties";
        }
        String[] b = new String[0];
        Properties p = new Properties();
        FileInputStream fis;
        InputStreamReader isr;
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            p.load(br);
            if (p.containsKey(director)) {
                String[] film = p.getProperty(director).replaceAll("_", " " + "").split("\\|");
                return film;
            } else {
                if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英文")) {

                }
                if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {

                }
            }
        } catch (FileNotFoundException e1) {
            System.out.println("The file contain director->film is not found");
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding is not right");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Search film name by Actor name
     * <p>
     * Search a certain actor name to find his/her films. Or Search a film co-starred by many actors together.
     * Attention: Use " AND ",that is space+AND+space, to connect actors name.
     * For example,"Anne Hathaway","Anne Hathaway AND Bill Irwin"
     * When use chinese, use "和" to connect names.
     * Use properties file "Starring.properties" or "Starring_zn.properties" to get information.
     * Key is actor name. Value is film name.
     *
     * @param Actors   The Actor name(s) to search.
     * @param language English/英文/中文/Chinese
     * @return A String Array consists of  the film names of the searched actors.
     */
    public static String[] seachByActor(String Actors, String language) {
        String filename = "";
        if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英语")) {
            filename = PropertiesPath + "Starring_English.properties";
        }
        if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {
            filename = PropertiesPath + "Starring_中文.properties";
        }
        String[] Actor = Actors.replaceAll("和", " AND ").split(" AND ");
        String[] b = new String[0];
        Properties p = new Properties();

        FileInputStream fis;
        InputStreamReader isr;
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            p.load(br);
            if (Actor.length == 1) {
                if (p.containsKey(Actor[0])) {
                    String[] film = p.getProperty(Actor[0]).replaceAll("_", " ").split("\\|");
                    return film;
                } else {
                    if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英语")) {

                    }
                    if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {

                    }

                }
            } else if (Actor.length > 1) {
                HashSet<String> get = new HashSet<String>();
                ArrayList<String> movies = new ArrayList<String>();
                for (int i = 0; i < Actor.length; i++) {

                    if (p.containsKey(Actor[i])) {
                        for (String m : p.getProperty(Actor[i]).replaceAll("_", " ").split("\\|")) {
                            if (movies.contains(m)) {
                                get.add(m);
                            } else {
                                movies.add(m);
                            }
                        }
                    } else {
                        if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英语")) {

                        }
                        if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {

                        }
                    }
                }
                if (get.size() > 0) {
                    return get.toArray(new String[0]);
                } else {
                    if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英文")) {

                    }
                    if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {

                    }
                }

            } else {
                if (String.valueOf(language).equals("English") || String.valueOf(language).equals("英文")) {

                }
                if (String.valueOf(language).equals("Chinese") || String.valueOf(language).equals("中文")) {

                }
            }

        } catch (FileNotFoundException e1) {
            System.out.println("The file contain Actors->film is not found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding is not right");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * @param key      search key input
     * @param language used language
     * @return film instance :search result
     */
    public static film[] Search(String key, String language) {
        String Name = seachByFilmName(key, language);
        String[] doc = seachByDirector(key, language);
        String[] act = seachByActor(key, language);
        Set<String> total = new HashSet<>();
        int cursor = 0;
        if (Name.length() > 0) {
            total.add(key);
        }
        for (int i = 0; i < doc.length; i++) {
            total.add(doc[i]);
        }
        for (int i = 0; i < act.length; i++) {
            total.add(act[i]);
        }
        Set<film> filmSet = new LinkedHashSet<>();
        for (String e : total
                ) {
            filmSet.add(getInfoFromProperty(e, language));

        }

        if (filmSet.size() == 0 && language.equals("中文")) {
            String eName = seachByFilmName(key, "English");
            String[] edoc = seachByDirector(key, "English");
            String[] eact = seachByActor(key, "English");
            Set<String> etotal = new HashSet<>();
            if (Name.length() > 0) {
                etotal.add(key);
            }

            etotal.addAll(Arrays.asList(doc));
            etotal.addAll(Arrays.asList(act));
            Set<film> efilmSet = new LinkedHashSet<>();
            for (String e : total
                    ) {

                efilmSet.add(getInfoFromProperty(e, "English"));


            }
            return efilmSet.toArray(new film[0]);
        }
        return filmSet.toArray(new film[0]);
    }

    /**
     * @param name:     film name
     * @param language: used category
     * @return film instance by property file
     */
    private static film getInfoFromProperty(String name, String language) {
        String filename = PropertiesPath + "Film_" + language + ".properties";
        Properties p = new Properties();
        FileInputStream fis;
        InputStreamReader isr;
        film r = new film();
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            p.load(br);
            String[] mm = p.getProperty(name).split("\\|");
            String[] m = new String[mm.length - 1];
            for (int i = 0; i < mm.length - 1; i++) {
                m[i] = mm[i];
            }
            String[] n = new String[2 * m.length];
            for (int i = 0; i < m.length; i++) {
                String[] s = m[i].split(":");
                n[2 * (i + 1) - 2] = s[0];
                n[2 * (i + 1) - 1] = s[1].replaceAll("_", ", ");
            }
            r.setCountry(n[1]);
            r.setDirector(n[3]);
            r.setCategory(n[5]);
            r.setDuration(n[9]);
            r.setFilmName(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * @param filmName: filmName Or ImageName
     * @return the name of the trailer file
     */
    private static String findNameByName(String filmName) {
        String fileName;
        String PropertyPath = new File("Properties").getAbsolutePath() + "\\Name_Film.properties";
        Properties p = new Properties();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PropertyPath), "UTF-8"))) {
            p.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName = p.getProperty(filmName);
        return fileName;
    }

    /**
     * @param filmName name of the clicked one( must be EnglishName)
     * @return file path absolute
     */
    public static String getFilmAbsolutePath(String filmName) {

        String notNull = getEnglishName(filmName);
        filmName = (notNull != null && notNull.length() > 0) ? notNull : "Interstellar";//hun shui mo yu
        String fileName = findNameByName(filmName);
        String filePath = new File("filmTrailer").getAbsolutePath() + "\\" + fileName;
        return filePath;
    }

    /**
     * @param ChineseName
     * @return EnglishName responding
     */
    public static String getEnglishName(String ChineseName) {
        String EnglishName;
        String PropertyPath = new File("Properties").getAbsolutePath() + "\\中文_English.properties";
        Properties p = new Properties();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PropertyPath), "UTF-8"))) {
            p.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EnglishName = p.getProperty(ChineseName);
        return EnglishName;
    }

    public static String getChineseName(String EnglishName) {
        String ChineseName;
        String PropertyPath = new File("Properties").getAbsolutePath() + "\\English_中文.properties";
        Properties p = new Properties();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PropertyPath), "UTF-8"))) {
            p.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChineseName = p.getProperty(EnglishName);
        return ChineseName;
    }

}
