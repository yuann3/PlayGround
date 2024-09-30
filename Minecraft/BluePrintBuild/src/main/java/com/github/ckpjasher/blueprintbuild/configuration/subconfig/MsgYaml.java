package com.github.ckpjasher.blueprintbuild.configuration.subconfig;

import com.github.ckpjasher.blueprintbuild.configuration.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * @author ckpjasher
 * @date 2022/4/15 0:56
 */
public final class MsgYaml extends Config {
  private static YamlConfiguration config;

  public MsgYaml(Plugin plugin) {
    super(plugin, "message");
    config = getConfig();
  }

  @Override
  public void loadConfig() {

  }

  public static String getMsg(String path, Object... args) {
    String message = ChatColor.translateAlternateColorCodes('&', config.getString(path, ""));
    for (int i = 0; i < args.length; i++) {
      message = message.replaceAll(String.format("\\{%d\\}", i), String.valueOf(args[i]));
    }
    return message;
  }

}
