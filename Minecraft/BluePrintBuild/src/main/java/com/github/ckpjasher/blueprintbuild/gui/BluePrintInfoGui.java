package com.github.ckpjasher.blueprintbuild.gui;

import com.github.ckpjasher.blueprintbuild.api.BpbPermission;
import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import com.github.ckpjasher.blueprintbuild.api.pojo.MaterialLibrary;
import com.github.ckpjasher.blueprintbuild.util.LPUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BluePrintInfoGui implements InventoryHolder {
    private final Inventory inventory;
    private final HumanEntity player;
    private final String materialId;

    public BluePrintInfoGui(HumanEntity player, String inventoryName, String materialId) {
        this.inventory = Bukkit.createInventory(this, 54, inventoryName);
        this.player = player;
        this.materialId = materialId;
        if (materialId.isEmpty()) {
            return;
        }
        MaterialLibrary materialLibrary = MaterialLibrary.getOrCreateDeepMaterialLibrary(materialId);
        List<ItemStack> itemStacks = new ArrayList<>();
        for (BluePrint bluePrint : materialLibrary.getBluePrintList()) {
            itemStacks.add(bluePrint.build());
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
        if (slot > clickedInventory.getSize() || slot < 0) {
            return;
        }
        InventoryView view = event.getView();
        HumanEntity viewPlayer = view.getPlayer();
        if (!(event.getClickedInventory().getHolder() instanceof BluePrintInfoGui bluePrintInfoGui)) {
            return;
        }
        ItemStack item = clickedInventory.getItem(slot);
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) {
            return;
        }
        if (!LPUtil.hasPermission(viewPlayer, BpbPermission.BPB_LOAD.getName(bluePrintInfoGui.materialId))) {
            viewPlayer.sendMessage(ChatColor.RED + "你没有权限获取这个蓝图");
            return;
        }
        viewPlayer.getInventory().addItem(item);
        viewPlayer.closeInventory();
        viewPlayer.sendMessage(ChatColor.BLUE + "已将蓝图放入背包");
    }
}
