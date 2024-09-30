package com.github.ckpjasher.blueprintbuild.commands.subcommands;

import com.github.ckpjasher.blueprintbuild.commands.BaseCommand;
import com.github.ckpjasher.blueprintbuild.util.WorldEditUtil;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.session.SessionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CommandUndo implements BaseCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }
        UUID playerUniqueId = player.getUniqueId();
        Map<UUID, EditSession> recordOperation = WorldEditUtil.getRecordOperation();
        if (!recordOperation.containsKey(playerUniqueId)) {
            sender.sendMessage("§4没有可撤回的操作");
            return;
        }
        SessionManager manager = WorldEdit.getInstance().getSessionManager();
        BukkitPlayer actor = BukkitAdapter.adapt(player);
        LocalSession localSession = manager.get(actor);
        EditSession editSession = recordOperation.get(playerUniqueId);
        localSession.remember(editSession);
        localSession.save();
        BlockBag blockBag = localSession.getBlockBag(actor);
        EditSession undone = localSession.undo(blockBag, actor);
        if (undone != null) {
            WorldEdit.getInstance().flushBlockBag(actor, undone);
        }
        recordOperation.remove(playerUniqueId);
        player.sendMessage("§a撤回成功");
    }
}
