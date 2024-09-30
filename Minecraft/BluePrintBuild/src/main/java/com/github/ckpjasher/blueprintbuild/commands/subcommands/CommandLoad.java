package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CommandLoad implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || args.length != 3) {
            return;
        }
        val blueprintName = args[1];
        val materialId = args[2];
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_LOAD.getName(materialId))) {
            nonePermission(sender);
            return;
        }
        Optional<BluePrint> bluePrint1 = MaterialLibrary.getOrCreate(materialId)
                .getBluePrintList()
                .stream()
                .filter(bluePrint -> bluePrint.getName().equals(blueprintName))
                .findFirst();
        if (bluePrint1.isPresent()) {
            player.getInventory().addItem(bluePrint1.get().build());
        } else {
            player.sendMessage("§4找不到蓝图");
        }
    }
}
