package com.github.ckpjasher.blueprintbuild.api.pojo;

import com.github.ckpjasher.blueprintbuild.configuration.subconfig.DataYaml;
import com.github.ckpjasher.blueprintbuild.util.WorldEditUtil;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class BluePrint implements ConfigurationSerializable {
    private UUID id;
    private String name;
    private String owner;

    public BluePrint(String name, String owner) {
        this.name = name;
        this.id = UUID.randomUUID();
        owner = owner == null ? "" : owner;
        this.owner = owner;
    }

    public static BluePrint getBluePrint(UUID id) {
        return DataYaml.getBluePrint(id);
    }

    public static BluePrint getBluePrint(String name) {
        for (MaterialLibrary materialLibrary : DataYaml.getMaterialLibraries()) {
            for (BluePrint bluePrint : materialLibrary.getBluePrintList()) {
                if (bluePrint.getName().equals(name)) {
                    return bluePrint;
                }
            }
        }
        return null;
    }

    public static BluePrint getBluePrint(ItemStack item) {
        if (item == null || item.getType() != Material.PAPER) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        List<String> lore = itemMeta.getLore();
        if (lore == null || lore.size() < 2) {
            return null;
        }
        if (!(lore.get(0).startsWith(ChatColor.GREEN + "名称: ") && lore.get(1).startsWith(ChatColor.GRAY + "编号: "))) {
            return null;
        }
        String id = lore.get(1).substring(lore.get(1).indexOf(":") + 2);
        return getBluePrint(UUID.fromString(id));
    }

    public void buildToLocation(Player player, Location location) {
        if (id == null || location == null) {
            throw new IllegalArgumentException("找不到蓝图或者建造位置");
        }
        Clipboard clipboard = WorldEditUtil.loadClipboardFromFile(id.toString());
        if (clipboard == null) {
            throw new IllegalArgumentException("找不到蓝图" + name + "的文件");
        }
        WorldEditUtil.generateClipboard(player, location, clipboard);
    }

    public static boolean noneBluePrint(ItemStack itemStack) {
        return getBluePrint(itemStack) == null;
    }

    public ItemStack build() {
        ItemStack blueprint = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = blueprint.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "[ 建筑蓝图 ]");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "名称: " + ChatColor.WHITE + name);
        lore.add(ChatColor.GRAY + "编号: " + id.toString());
        lore.add(ChatColor.WHITE + "作者: " + owner);
        itemMeta.setLore(lore);
        blueprint.setItemMeta(itemMeta);
        return blueprint;
    }

    @NonNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("id", id.toString());
        return map;
    }

    public static BluePrint deserialize(Map<String, Object> map) {
        BluePrint bluePrint = new BluePrint((String) map.get("name"), (String) map.get("owner"));
        bluePrint.setId(UUID.fromString((String) map.get("id")));
        return bluePrint;
    }
}
