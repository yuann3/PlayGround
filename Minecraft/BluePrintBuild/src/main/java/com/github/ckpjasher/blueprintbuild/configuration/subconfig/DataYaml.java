package com.github.ckpjasher.blueprintbuild.configuration.subconfig;

import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.configuration.Config;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataYaml extends Config {
    @Getter
    private static final List<MaterialLibrary> materialLibraries = new ArrayList<>();
    @Getter
    private static DataYaml instance;

    public DataYaml(Plugin plugin) {
        super(plugin, "data");
        DataYaml.instance = this;
    }

    @Override
    public void loadConfig() {
        materialLibraries.clear();
        materialLibraries.addAll((List<MaterialLibrary>) config.get("materialLibraries"));
    }

    public void saveMaterialLibraries() {
        config.set("materialLibraries", materialLibraries);
        saveConfig();
    }

    public static BluePrint getBluePrint(UUID id) {
        for (MaterialLibrary materialLibrary : materialLibraries) {
            BluePrint bluePrint = findBluePrint(id, null, materialLibrary);
            if (bluePrint != null) return bluePrint;
        }
        return null;
    }

    public static BluePrint getBluePrint(String name) {
        for (MaterialLibrary materialLibrary : materialLibraries) {
            BluePrint bluePrint = findBluePrint(null, name, materialLibrary);
            if (bluePrint != null) return bluePrint;
        }
        return null;
    }

    public static BluePrint findBluePrint(UUID id, String name, MaterialLibrary materialLibrary) {
        for (BluePrint bluePrint : materialLibrary.getBluePrintList()) {
            if (bluePrint.getId().equals(id) || bluePrint.getName().equals(name)) {
                return bluePrint;
            }
        }
        for (MaterialLibrary child : materialLibrary.getChildMaterialLibraries()) {
            BluePrint bluePrint = findBluePrint(id, name, child);
            if (bluePrint != null) return bluePrint;
        }
        return null;
    }
}
