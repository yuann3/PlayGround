package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandList implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) ||
                args.length > 3) {
            return;
        }
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_LIST.getName())) {
            nonePermission(sender);
            return;
        }
        var materialId = "";
        var permission = false;
        if (args.length == 3 && args[1].equals("my")) {
            materialId = args[2];
            permission = true;
        } else if (args.length == 2){
            materialId = args[1];
        } else {
            materialId = player.getName();
        }
        if (materialId.isEmpty()) {
            sender.sendMessage("§c检查是否输入了素材库ID");
            return;
        }
        MaterialLibrary.sendBlueprints(player, materialId, permission);
    }
}
