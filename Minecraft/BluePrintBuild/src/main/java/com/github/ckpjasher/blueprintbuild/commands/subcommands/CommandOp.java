package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;

public class CommandOp implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        // §6/bpb op add <玩家ID> <素材库ID> - 设置玩家拥有对某素材库所有权限
        if (args.length != 4) {
            sender.sendMessage("§c参数不足");
            return;
        }
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_OP_ADD.getName())) {
            nonePermission(sender);
            return;
        }
        String playerName = args[2];
        String materialName = args[3];
        for (BpbPermission bpbPermission : BpbPermission.values()) {
            LPUtil.addPermission(playerName, bpbPermission.getName(materialName));
            LPUtil.addPermission(playerName, bpbPermission.getName());
        }
        sender.sendMessage("§a添加成功");
    }
}
