package com.github.ckpjasher.blueprintbuild.listener;

import com.github.ckpjasher.blueprintbuild.gui.BluePrintInfoGui;
import com.github.ckpjasher.blueprintbuild.gui.MainGui;
import com.github.ckpjasher.blueprintbuild.gui.MaterialGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MaterialGuiListener implements Listener{
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if ((event.getClickedInventory().getHolder() instanceof MainGui mainGui)) {
            MainGui.onClick(event);
        } else if (event.getClickedInventory().getHolder() instanceof BluePrintInfoGui bluePrintInfoGui) {
            BluePrintInfoGui.onClick(event);
        } else if (event.getClickedInventory().getHolder() instanceof MaterialGui materialGui) {
            MaterialGui.onClick(event);
        }
    }
}