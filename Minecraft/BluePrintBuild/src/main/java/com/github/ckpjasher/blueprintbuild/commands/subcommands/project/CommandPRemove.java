package com.github.ckpjasher.blueprintbuild.commands.subcommands.project;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;

public class CommandPRemove implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        String removedMaterialName = args[2];
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_PROJECT_REMOVE.getName())) {
            nonePermission(sender);
            return;
        }
        MaterialLibrary toMaterial = MaterialLibrary.getMaterialLibrary("project");
        if (toMaterial == null) {
            sender.sendMessage("§c" + "project" + "素材库不存在");
            return;
        }
        if (toMaterial.getChildMaterialLibraries().remove(MaterialLibrary.getOrCreate(removedMaterialName))) {
            sender.sendMessage("§a删除成功");
        } else {
            sender.sendMessage("§c删除失败, 请检查素材库是否存在");
        }
    }
}
