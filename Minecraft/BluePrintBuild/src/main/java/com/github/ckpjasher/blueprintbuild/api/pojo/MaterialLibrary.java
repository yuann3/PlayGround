package com.github.ckpjasher.blueprintbuild.api.pojo;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.configuration.subconfig.DataYaml;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MaterialLibrary implements ConfigurationSerializable {
    private String id;
    private List<BluePrint> bluePrintList;
    private List<MaterialLibrary> childMaterialLibraries;

    public MaterialLibrary(String id) {
        this.id = id;
        this.bluePrintList = new ArrayList<>();
        this.childMaterialLibraries = new ArrayList<>();
        DataYaml.getMaterialLibraries().add(this);
    }

    public static MaterialLibrary getOrCreateDeepMaterialLibrary(String materialId) {
        MaterialLibrary materialLibrary = getDeepMaterialLibrary(materialId);
        if (materialLibrary != null) return materialLibrary;
        return new MaterialLibrary(materialId);
    }

    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("bluePrintList", bluePrintList);
        map.put("childMaterialLibraries", childMaterialLibraries);
        return map;
    }

    public static MaterialLibrary deserialize(Map<String, Object> map) {
        MaterialLibrary materialLibrary = new MaterialLibrary((String) map.get("id"));
        materialLibrary.setBluePrintList((List<BluePrint>) map.get("bluePrintList"));
        materialLibrary.setChildMaterialLibraries((List<MaterialLibrary>) map.get("childMaterialLibraries"));
        return materialLibrary;
    }

    public static MaterialLibrary getOrCreate(String id) {
        MaterialLibrary materialLibrary = getMaterialLibrary(id);
        if (materialLibrary != null) return materialLibrary;
        return new MaterialLibrary(id);
    }

    public static MaterialLibrary getMaterialLibrary(String id) {
        List<MaterialLibrary> materialLibraries = DataYaml.getMaterialLibraries();
        for (MaterialLibrary materialLibrary : materialLibraries) {
            if (materialLibrary.getId().equals(id)) {
                return materialLibrary;
            }
        }
        return null;
    }

    public static MaterialLibrary getDeepMaterialLibrary(String id) {
        List<MaterialLibrary> materialLibraries = DataYaml.getMaterialLibraries();
        for (MaterialLibrary materialLibrary : materialLibraries) {
            if (materialLibrary.getId().equals(id)) {
                return materialLibrary;
            }
            for (MaterialLibrary childMaterialLibrary : materialLibrary.getChildMaterialLibraries()) {
                if (childMaterialLibrary.getId().equals(id)) {
                    return childMaterialLibrary;
                }
            }
        }
        return null;
    }

    public static MaterialLibrary getChildMaterialLibrary(MaterialLibrary materialLibrary, String id) {
        for (MaterialLibrary child : materialLibrary.getChildMaterialLibraries()) {
            if (child.getId().equals(id)) {
                return child;
            }
        }
        MaterialLibrary library = new MaterialLibrary(id);
        library.getChildMaterialLibraries().add(library);
        return library;
    }

    /**
     * 发送蓝图列表
     * @param sender 接受者
     * @param materialId 素材库id
     * @param permission 是则展示需要权限的蓝图, 否则展示所有蓝图
     */
    public static void sendBlueprints(CommandSender sender, String materialId, boolean permission) {
        MaterialLibrary materialLibrary = MaterialLibrary.getOrCreate(materialId);
        sender.sendMessage("---------------");
        for (BluePrint bluePrint : materialLibrary.getBluePrintList()) {
            if (permission && !LPUtil.hasPermission(sender.getName(), BpbPermission.BPB_LOAD.getName(materialId))) continue;
            sender.sendMessage(bluePrint.getName());
        }
        sender.sendMessage("---------------");
    }
}
