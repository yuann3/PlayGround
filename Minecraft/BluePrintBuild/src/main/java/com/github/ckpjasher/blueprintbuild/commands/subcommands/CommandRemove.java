package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class CommandRemove implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || args.length != 3) {
            return;
        }
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_REMOVE.getName())) {
            nonePermission(sender);
            return;
        }
        val blueprintName = args[1];
        var materialId = args[2];
        if (materialId.equals("private")) {
            materialId = player.getName();
        } else if (materialId.equals("public")) {
            materialId = "public";
        } else if (materialId.equals("project")) {
            materialId = "project";
        }
        MaterialLibrary materialLibrary = MaterialLibrary.getOrCreate(materialId);
        AtomicBoolean flag = new AtomicBoolean(false);
        materialLibrary.getBluePrintList().removeIf(bluePrint -> {
            boolean equals = bluePrint.getName().equals(blueprintName);
            if (equals) {
                flag.set(true);
            }
            return equals;
        });
        if (flag.get()) {
            player.sendMessage("§a移除成功");
        } else {
            player.sendMessage("§c移除失败");
        }
    }
}
