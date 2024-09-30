package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import com.github.ckpjasher.blueprintbuild.util.WorldEditUtil;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandSave implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || args.length != 3) {
            return;
        }
        String name = args[1];
        String materialId = args[2];
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_SAVE.getName(materialId))) {
            nonePermission(sender);
            return;
        }
        Clipboard playerClipboard = WorldEditUtil.getPlayerClipboard(player);
        BluePrint bluePrint = new BluePrint(name, player.getName());
        bluePrint.setId(UUID.randomUUID());
        bluePrint.setName(name);
        MaterialLibrary materialLibrary;
        if (materialId.equalsIgnoreCase("private")) {
            materialLibrary = MaterialLibrary.getOrCreate(player.getName());
            if (materialLibrary.getBluePrintList().stream().anyMatch(bluePrint1 -> bluePrint1.getName().equals(name))) {
                player.sendMessage("§c已存在同名Sketch");
                return;
            }
            materialLibrary.getBluePrintList().add(bluePrint);
        } else if (materialId.equalsIgnoreCase("public")) {
            materialLibrary = MaterialLibrary.getOrCreate("public");
            if (materialLibrary.getBluePrintList().stream().anyMatch(bluePrint1 -> bluePrint1.getName().equals(name))) {
                player.sendMessage("§c已存在同名Sketch");
                return;
            }
            materialLibrary.getBluePrintList().add(bluePrint);
        } else {
            materialLibrary = MaterialLibrary.getOrCreate("project");
            MaterialLibrary create = MaterialLibrary.getChildMaterialLibrary(materialLibrary, materialId);
            if (create.getBluePrintList().stream().anyMatch(bluePrint1 -> bluePrint1.getName().equals(name))) {
                player.sendMessage("§c已存在同名Sketch");
                return;
            }
            create.getBluePrintList().add(bluePrint);
        }
        WorldEditUtil.saveClipboardToFile(bluePrint.getId().toString(), playerClipboard);
        player.sendMessage("[artiSketch]Saved!");
    }
}
