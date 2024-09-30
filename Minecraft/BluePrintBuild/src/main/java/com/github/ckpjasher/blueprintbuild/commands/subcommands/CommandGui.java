package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.gui.MainGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGui implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || args.length != 1) {
            return;
        }
        new MainGui(player).openInventory();
    }
}
