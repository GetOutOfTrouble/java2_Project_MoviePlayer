package Search_Scene;

public class TestSERACH {
    public static void main(String[] args) {

        Search.Search("abc", "中文");
        Search.Search("North By Northwest", "English");
        Search.Search("赤壁", "中文");
        Search.getFilmAbsolutePath("赤壁");
        Search.getFilmAbsolutePath("North_By_Northwest");
        System.out.println();
    }
}
