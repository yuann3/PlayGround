package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.commands.subcommands.project.CommandPAdd;
import com.github.ckpjasher.blueprintbuild.commands.subcommands.project.CommandPRemove;
import com.github.ckpjasher.blueprintbuild.commands.subcommands.project.builder.CommandBAdd;
import com.github.ckpjasher.blueprintbuild.commands.subcommands.project.builder.CommandBRemove;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandProject implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }
        /**
         *   §6/bpb project list - 显示项目素材库的所有子分类素材库
         *                     §6/bpb project add <素材库显示名称> <素材库ID> - 添加子分类素材库
         *                     §6/bpb project remove <素材库ID> - 移除子分类素材库
         *                     §6/bpb project builder add <玩家ID> <素材库ID> - 设置哪些玩家可以访问哪些素材库, 并给予权限
         *                     §6/bpb project builder remove <玩家ID> <素材库ID> - 移除玩家对素材库的访问权限
         */
        String command = args[1];
        switch (command) {
            case "add" -> {
                if (!LPUtil.hasPermission(sender, BpbPermission.BPB_PROJECT_ADD.getName())) {
                    nonePermission(sender);
                    return;
                }
                new CommandPAdd().execute(sender, args);
            }
            case "remove" -> {
                if (!LPUtil.hasPermission(sender, BpbPermission.BPB_PROJECT_REMOVE.getName())) {
                    nonePermission(sender);
                    return;
                }
                new CommandPRemove().execute(sender, args);
            }
            case "builder" -> {
                if (args.length != 5) {
                    return;
                }
                if (args[2].equals("add")) {
                    if (!LPUtil.hasPermission(sender, BpbPermission.BPB_PROJECT_BUILDER_ADD.getName())) {
                        nonePermission(sender);
                        return;
                    }
                    new CommandBAdd().execute(sender, args);
                } else if (args[2].equals("remove")){
                    if (!LPUtil.hasPermission(sender, BpbPermission.BPB_PROJECT_BUILDER_REMOVE.getName())) {
                        nonePermission(sender);
                        return;
                    }
                    new CommandBRemove().execute(sender, args);
                }
            }
            default -> {
                sender.sendMessage("§c未知命令");
            }
        }
    }

}
