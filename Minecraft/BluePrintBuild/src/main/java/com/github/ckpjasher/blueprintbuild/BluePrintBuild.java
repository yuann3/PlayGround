package com.github.ckpjasher.blueprintbuild;

import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.CommandHandler;
import com.github.ckpjasher.blueprintbuild.configuration.subconfig.DataYaml;
import com.github.ckpjasher.blueprintbuild.listener.MaterialGuiListener;
import com.github.ckpjasher.blueprintbuild.listener.PlayerListener;
import com.github.ckpjasher.blueprintbuild.task.AutoSaveTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class BluePrintBuild extends JavaPlugin {
    @Getter
    private static BluePrintBuild instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("BluePrintBuild").setExecutor(CommandHandler.getInstance());
        ConfigurationSerialization.registerClass(MaterialLibrary.class);
        ConfigurationSerialization.registerClass(BluePrint.class);
        new DataYaml(instance).loadConfig();
        Arrays.asList(new PlayerListener(),new MaterialGuiListener())
                .forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AutoSaveTask(), 20 * 5, 20 * 5);
    }
}
