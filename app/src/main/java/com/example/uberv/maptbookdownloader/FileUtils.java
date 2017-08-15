package com.example.uberv.maptbookdownloader;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    public static final String LOG_TAG = FileUtils.class.getSimpleName();

    public static final String BOOKS_FOLDER_NAME = "Mapt Books";

    public static File createBookFile(String fileName) {
        // Get the directory for the user's public pictures directory.
        File rootPath = new File(Environment.getExternalStorageDirectory(), BOOKS_FOLDER_NAME);
        if (!rootPath.exists()) {
            if (!rootPath.mkdirs()) {
                Log.d(LOG_TAG, "Could not create file " + rootPath.toString());
                return null;
            }
        }

        // Create a file reference
        return new File(rootPath, fileName);
    }

    public static boolean writeToFile(File file, String data) {
        //Create a new file and write some data
        try {
            FileOutputStream mOutput = new FileOutputStream(file, false);
            mOutput.write(data.getBytes());
            mOutput.flush();
            //With external files, it is often good to wait for the write
            mOutput.getFD().sync();
            mOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
