package com.github.ckpjasher.blueprintbuild.commands.subcommands.project;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandPAdd implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        String childMaterial = args[2];
        String toMaterialId = args[3];
        if (!LPUtil.hasPermission(sender, BpbPermission.BPB_PROJECT_ADD.getName())) {
            nonePermission(sender);
            return;
        }
        if (toMaterialId.equals("private")) {
            sender.sendMessage("§c" + toMaterialId + "素材库不允许添加子分类");
            return;
        }
        MaterialLibrary toMaterial;
        if (toMaterialId.equals("public")) {
            toMaterial = MaterialLibrary.getOrCreate("public");
        } else if (toMaterialId.equals("project")) {
            toMaterial = MaterialLibrary.getOrCreate("project");
        } else {
            toMaterial = MaterialLibrary.getMaterialLibrary(toMaterialId);
        }
        if (toMaterial == null) {
            sender.sendMessage("§c" + toMaterialId + "素材库不存在");
            return;
        }
        List<MaterialLibrary> childMaterialLibraries = toMaterial.getChildMaterialLibraries();
        for (MaterialLibrary childMaterialLibrary : childMaterialLibraries) {
            if (childMaterialLibrary.getId().equals(childMaterial)) {
                sender.sendMessage("§c" + childMaterial + "素材库已存在");
                return;
            }
        }
        childMaterialLibraries.add(MaterialLibrary.getOrCreate(childMaterial));
        sender.sendMessage("§a添加成功");
    }
}
