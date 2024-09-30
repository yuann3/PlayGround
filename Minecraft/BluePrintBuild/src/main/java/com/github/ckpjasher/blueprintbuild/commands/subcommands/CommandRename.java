package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.configuration.subconfig.DataYaml;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRename implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || args.length != 4) {
            return;
        }
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_RENAME.getName())) {
            nonePermission(sender);
            return;
        }
        val blueprintName = args[1];
        val blueprintNewName = args[2];
        val materialId = args[3];
        MaterialLibrary materialLibrary = MaterialLibrary.getOrCreate(materialId);
        BluePrint bluePrint = DataYaml.findBluePrint(null, blueprintName, materialLibrary);
        if (bluePrint == null) {
            player.sendMessage("§4未找到对应的建筑蓝图");
            return;
        }
        bluePrint.setName(blueprintNewName);
        player.sendMessage("§a重命名成功");
    }
}
