package helper;

import java.io.File;
import java.util.Date;

public class Helper {
    public static void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Directory is not created!");
            }
        }
    }

    public static String timestamp(){
        Date now = new Date();
        String timestamp = now.toString().replace(":","_" );
        return timestamp;
    }
}
