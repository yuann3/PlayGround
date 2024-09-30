package com.github.ckpjasher.blueprintbuild.configuration.subconfig;

import com.github.ckpjasher.blueprintbuild.configuration.Config;
import org.bukkit.plugin.Plugin;

public class ConfigYaml extends Config {
  public ConfigYaml(Plugin plugin) {
    super(plugin, "config");
  }

  @Override
  public void loadConfig() {

  }
}
