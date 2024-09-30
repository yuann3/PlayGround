package com.github.ckpjasher.blueprintbuild.configuration;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author ckpjasher
 * @date 2023/7/1 17:53
 */
public abstract class Config {
  private final Plugin instance;
  private final File DATA_FILE;
  @Getter
  public YamlConfiguration config = new YamlConfiguration();
  private final String fileName;
  private static final List<Config> CONFIGS = new ArrayList<>();

  public Config(Plugin plugin, String fileName) {
    instance = plugin;
    this.fileName = fileName;
    DATA_FILE = new File(instance.getDataFolder() + File.separator + fileName + ".yml");
    saveDefaultConfig();
    loadYaml();
    CONFIGS.add(this);
  }

  public abstract void loadConfig();

  public void reloadConfig() {
    loadYaml();
    loadConfig();
  }

  public void loadYaml() {
    try {
      config.load(DATA_FILE);
    } catch (IOException | InvalidConfigurationException e) {
        Bukkit.getLogger().log(Level.WARNING, "Cant load File: " + DATA_FILE, e);
    }
  }

  public void saveConfig() {
    try {
      config.save(DATA_FILE);
    } catch (IOException e) {
      Bukkit.getLogger().log(Level.WARNING, "Cant save file: " + DATA_FILE, e);
    }
  }

  public static List<Config> getConfigs() {
    return CONFIGS;
  }

  public void saveDefaultConfig() {
    if (!DATA_FILE.exists()) {
      instance.saveResource(fileName + ".yml", false);
    }
  }
}
