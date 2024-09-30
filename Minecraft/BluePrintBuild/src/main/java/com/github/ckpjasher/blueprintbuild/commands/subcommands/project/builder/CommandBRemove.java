package com.github.ckpjasher.blueprintbuild.commands.subcommands.project.builder;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;

public class CommandBRemove implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        //§6/bpb project builder add <玩家ID> <素材库ID> - 设置哪些玩家可以访问哪些素材库, 并给予权限
        //                    §6/bpb project builder remove <玩家ID> <素材库ID> - 移除玩家对素材库的访问权限
        String playerName = args[3];
        String materialId = args[4];
        String permission = BpbPermission.BPB_PROJECT_BUILDER_REMOVE.getName(materialId);
        if (LPUtil.hasPermission(playerName, permission)) {
            LPUtil.removePermission(playerName, permission);
            sender.sendMessage("§a移除成功");
        } else {
            sender.sendMessage("§c移除失败, 请检查玩家是否有权限");
        }
    }
}
