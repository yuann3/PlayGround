package com.github.ckpjasher.blueprintbuild.commands;

import org.bukkit.command.CommandSender;

/**
 * 抽象类，所有子命令应使用此接口
 *
 */
public interface BaseCommand {
    /**
     * 抽象方法
     * 所有子命令 args >= 1
     */
    void execute(CommandSender sender, String[] args);

    default void nonePermission(CommandSender sender) {
        sender.sendMessage("§c§l你没有权限执行此命令");
    }
}
