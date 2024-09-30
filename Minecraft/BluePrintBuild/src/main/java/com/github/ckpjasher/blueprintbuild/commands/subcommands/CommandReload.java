package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.configuration.Config;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;

public class CommandReload implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_OP_ADD.getName())) {
            nonePermission(sender);
            return;
        }
        Config.getConfigs().forEach(Config::reloadConfig);
        sender.sendMessage("§6§l---  §a§l重载成功  §6§l---");
    }
}
