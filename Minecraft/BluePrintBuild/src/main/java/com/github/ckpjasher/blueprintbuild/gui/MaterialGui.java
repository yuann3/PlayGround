package com.github.ckpjasher.blueprintbuild.gui;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MaterialGui implements InventoryHolder {
    private final Inventory inventory;
    private final HumanEntity player;
    private final String materialId;

    public MaterialGui(HumanEntity player, String inventoryName, String materialId) {
        this.inventory = Bukkit.createInventory(this, 54, inventoryName);
        this.player = player;
        this.materialId = materialId;
        if (materialId.isEmpty()) {
            return;
        }
        MaterialLibrary materialLibrary = MaterialLibrary.getOrCreateDeepMaterialLibrary(materialId);
        List<ItemStack> itemStacks = new ArrayList<>();
        for (MaterialLibrary child : materialLibrary.getChildMaterialLibraries()) {
            if (itemStacks.size() > 53) {
                break;
            }
            ItemStack itemStack = new ItemStack(Material.SUNFLOWER);
            String id = child.getId();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.WHITE + id);
            itemStack.setItemMeta(itemMeta);
            itemStacks.add(itemStack);
        }
        for (int i = 0; i < itemStacks.size(); i++) {
            inventory.setItem(i, itemStacks.get(i));
        }
    }

    public void openInventory() {
        player.openInventory(getInventory());
    }

    public static void onClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        Inventory clickedInventory = event.getClickedInventory();
        event.setCancelled(true);
        if (slot > clickedInventory.getSize() || slot < 0) {
            return;
        }
        InventoryView view = event.getView();
        HumanEntity viewPlayer = view.getPlayer();
        if (!(event.getClickedInventory().getHolder() instanceof MaterialGui materialGui)) {
            return;
        }
        ItemStack item = clickedInventory.getItem(slot);
        if (item == null || item.getItemMeta() == null) {
            return;
        }
        if (!LPUtil.hasPermission(viewPlayer, BpbPermission.BPB_PROJECT_ADD.getName(materialGui.materialId))) {
            viewPlayer.sendMessage(ChatColor.RED + "you have no authorizes to open this tab menu");
            return;
        }
        String id = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        MaterialLibrary materialLibrary = MaterialLibrary.getOrCreateDeepMaterialLibrary(id);
        new BluePrintInfoGui(viewPlayer, id, materialLibrary.getId()).openInventory();
    }

    public HumanEntity getPlayer() {
        return player;
    }
}
