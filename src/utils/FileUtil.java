package utils;

import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Created by csw on 2016/1/16.
 */
public class FileUtil {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static StringBuffer readFileToString(File file) {
        //读取File
        StringBuffer fileContent = new StringBuffer(); // 文件很长的话建议使用StringBuffer
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public static void writeToFile(String fileName, String content) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {//判断文件路径是否存在
            File filepath = new File(file.getParent());
            filepath.mkdir();
        } else {//删除原来文件，新建文件
            file.delete();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.newLine();
        bw.close();
    }
}
