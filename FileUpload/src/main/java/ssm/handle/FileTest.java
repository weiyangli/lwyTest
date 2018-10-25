package ssm.handle;

import java.io.File;

public class FileTest {
    public static void main(String[] args) throws Exception{
       /* File file  = new File("D:/mine/learn/文档01.doc");
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = null;
        writer = new FileWriter(file, true);
        writer.append("your content,your contentyour contentyour contentyour content");
        writer.flush();
        writer.close();*/
        String a = FileTest.class.getClassLoader().getResource("/").getPath();
        String classpath = FileTest.class.getClassLoader().getResource("").getPath().replaceFirst("/", "").replaceAll("java/main/target/classes/", "");
        System.out.println("文件的磁盘路径为1："+classpath);
        File file = new File(classpath+"src/main/webapp/WEB-INF/static", "books");
        System.out.println("文件的磁盘路径为3："+file.getPath());
    }

}
