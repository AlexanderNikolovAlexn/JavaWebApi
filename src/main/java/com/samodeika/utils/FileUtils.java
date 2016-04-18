package com.samodeika.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static String getFileContent(InputStream in) {
        byte[] data;
        try {
            data = new byte[in.available()];
            in.read(data);
            return new String(data);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static File writeToFile(String fileName, String data) {
        File newFile = new File(fileName);
        try {
            FileWriter fw = new FileWriter(newFile);
            fw.write(data);
            fw.close();
            return newFile;
        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
        return newFile;
    }


}
