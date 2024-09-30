package com.github.ckpjasher.blueprintbuild.listener;

import com.github.ckpjasher.blueprintbuild.BluePrintBuild;
import com.github.ckpjasher.blueprintbuild.api.pojo.BluePrint;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerListener implements Listener {
    private static final List<UUID> cache = new ArrayList<>();
    @EventHandler
    public void onBlueBuild(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack handItem = inventory.getItemInMainHand();
        Block clickedBlock = event.getClickedBlock();
        UUID playerUniqueId = player.getUniqueId();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                cache.contains(playerUniqueId) ||
                handItem.getType() == Material.AIR ||
                handItem.getItemMeta() == null ||
                handItem.getItemMeta().getLore() == null ||
                clickedBlock == null ||
                event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        BluePrint bluePrint = BluePrint.getBluePrint(handItem);
        if (bluePrint == null) {
            return;
        }
        cache.add(playerUniqueId);
        Bukkit.getScheduler().runTaskLater(BluePrintBuild.getInstance(), () -> {cache.remove(playerUniqueId);}, 5);
        bluePrint.buildToLocation(player, clickedBlock.getLocation().add(0,1,0));
    }
}
