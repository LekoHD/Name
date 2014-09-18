package com.lekohd.blockparty.system;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

    public static Map<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();

    /**
     * Checks to see if the ConfigManager knows about fileName
     *
     * @param fileName file to check
     * @return true if file is loaded, false if not
     */
    public static boolean isFileLoaded(String fileName) {
        return configs.containsKey(fileName);
    }

    /**
     * Loads a files configuration into Memory
     *
     * @param plugin Plugin to load file from if fileName does not exist in
     * Plugins folder
     * @param fileName File to load
     */
    public static void load(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                plugin.saveResource(fileName, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!isFileLoaded(fileName)) {
            configs.put(fileName, YamlConfiguration.loadConfiguration(file));
        }
    }

    /**
     * Gets the FileConfiguration for a specified file
     *
     * @param fileName File to load data from
     * @return File Configuration
     */
    public static FileConfiguration get(String fileName) {
        if (isFileLoaded(fileName)) {
            return configs.get(fileName);
        }
        return null;
    }

    /**
     * Updates the FileConfiguration at the given path. If path already exists
     * this will return false.
     *
     * @param fileName File to update
     * @param path Path to update
     * @param value Data to set at path
     * @return True if successful, otherwise false
     */
    public static boolean update(String fileName, String path, Object value) {
        if (isFileLoaded(fileName)) {
            if (!configs.get(fileName).contains(path)) {
                configs.get(fileName).set(path, value);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets data at any given path. If path already exists it will be over
     * written.
     *
     * @param fileName File to update
     * @param path Path to set
     * @param value Data to set at path
     */
    public static void set(String fileName, String path, Object value) {
        if (isFileLoaded(fileName)) {
            configs.get(fileName).set(path, value);
        }
    }

    /**
     * Create YAML Comments at the given path
     *
     * @param fileName File to add comments to
     * @param path Path to add comments too
     * @param comments Comments to add
     * 
     * @deprecated Not Tested/Experimental
     */
    public void addComment(String fileName, String path, String... comments) {
        if (isFileLoaded(fileName)) {
            for (String comment : comments) {
                if (!configs.get(fileName).contains(path)) {
                    configs.get(fileName).set("_COMMENT_" + comments.length, " " + comment);
                }
            }
        }
    }

    /**
     * Removes a path from the FileConfiguration.
     *
     * @param fileName File to update
     * @param path Path to remove
     */
    public static void remove(String fileName, String path) {
        if (isFileLoaded(fileName)) {
            configs.get(fileName).set(path, null);
        }
    }

    /**
     * Checks if a file has a path.
     *
     * @param fileName File to check
     * @param path Path to check
     * @return True if path exists, otherwise false.
     */
    public static boolean contains(String fileName, String path) {
        if (isFileLoaded(fileName)) {
            return configs.get(fileName).contains(path);
        }
        return false;
    }

    /**
     * Reload the config from the given Plugin.
     *
     * @param plugin Plugin to get the File from
     * @param fileName File to reload
     */
    public static void reload(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (isFileLoaded(fileName)) {
            try {
                configs.get(fileName).load(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save the config for the given plugin
     *
     * @param plugin Plugin dir to save to the file to
     * @param fileName File to save
     */
    public static void save(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (isFileLoaded(fileName)) {
            try {
                configs.get(fileName).save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}