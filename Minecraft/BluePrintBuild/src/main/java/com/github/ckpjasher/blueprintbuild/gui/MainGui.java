package com.github.ckpjasher.blueprintbuild.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class MainGui implements InventoryHolder {
    private final Inventory inventory;
    private final Player player;

    public MainGui(Player player) {
        this.inventory = Bukkit.createInventory(this, 27, "YourSketch");
        this.player = player;
        ItemStack pane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = pane.getItemMeta();
        itemMeta.setDisplayName("-");
        pane.setItemMeta(itemMeta);
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 11 || i == 13 || i == 15) {
                continue;
            }
            inventory.setItem(i, pane);
        }
        ItemStack itemStack1 = new ItemStack(Material.OAK_SIGN);
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        itemMeta1.setDisplayName(ChatColor.GOLD + "公共素材库");
        itemStack1.setItemMeta(itemMeta1);
        inventory.setItem(11, itemStack1);

        ItemStack itemStack2 = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.WHITE + "我的素材库");
        itemStack2.setItemMeta(itemMeta2);
        inventory.setItem(13, itemStack2);

        ItemStack itemStack3 = new ItemStack(Material.SUNFLOWER);
        ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName(ChatColor.GREEN + "项目素材库");
        itemStack3.setItemMeta(itemMeta3);
        inventory.setItem(15, itemStack3);
    }

    public void openInventory() {
        player.openInventory(getInventory());
    }

    public static void onClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        InventoryView view = event.getView();
        Inventory clickedInventory = event.getClickedInventory();
        HumanEntity viewPlayer = view.getPlayer();
        if (slot > clickedInventory.getSize() || slot < 0) {
            return;
        }
        switch (slot) {
            case 11 -> {
//                viewPlayer.sendMessage("公共素材库");
                new BluePrintInfoGui(viewPlayer, "公共素材库", "public").openInventory();
            }
            case 13 -> {
//                viewPlayer.sendMessage("我的素材库");
                new BluePrintInfoGui(viewPlayer, "我的素材库", viewPlayer.getName()).openInventory();
            }
            case 15 -> {
//                viewPlayer.sendMessage("项目素材库");
                new MaterialGui(viewPlayer, "项目素材库", "project").openInventory();
            }
        }
        event.setCancelled(true);
    }
}
