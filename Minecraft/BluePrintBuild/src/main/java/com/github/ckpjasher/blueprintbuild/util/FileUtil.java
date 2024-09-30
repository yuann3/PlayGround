package com.github.ckpjasher.blueprintbuild.util;

import com.github.ckpjasher.blueprintbuild.BluePrintBuild;

import java.io.File;

public class FileUtil {
    private static final String plugin_dir = BluePrintBuild.getInstance().getDataFolder().getAbsolutePath();
    public static void mkdir(String name) {
        File file = new File(plugin_dir, name);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void saveFile(File file, String dirName) {
        File dir = new File(plugin_dir, dirName);
        mkdir(dirName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(String name, String dirName) {
        File file = getFile(name, dirName);
        if (file != null) {
            file.delete();
        }
    }

    public static File getFile(String name, String dirName) {
        if (name == null || dirName == null) {
            return null;
        }

        File dir = new File(plugin_dir, dirName);
        File file = new File(dir, name);

        if (!file.exists()) {
            return null;
        }

        return file;
    }

    public static File getFileOrCreate(String name, String dirName) {
        File file = getFile(name, dirName);
        if (file == null) {
            File dir = new File(plugin_dir, dirName);
            mkdir(dirName);
            file = new File(dir, name);
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
