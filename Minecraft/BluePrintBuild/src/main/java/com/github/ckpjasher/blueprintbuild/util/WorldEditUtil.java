package com.github.ckpjasher.blueprintbuild.util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class WorldEditUtil {
    @Getter
    private static final Map<UUID, EditSession> recordOperation = new HashMap<>();
    public static Clipboard getPlayerClipboard(Player player) {
        LocalSession localSession = WorldEditPlugin.getInstance().getSession(player);
        World world = localSession.getSelectionWorld();
        Region r = localSession.getRegionSelector(world).getRegion();
        CuboidRegion region = new CuboidRegion(r.getMinimumPoint(), r.getMaximumPoint());
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                world, region, clipboard, region.getMinimumPoint()
        );
        Operations.complete(forwardExtentCopy);
        return clipboard;
    }

    public static void generateClipboard(Player player, Location location, Clipboard clipboard) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                    .build();
            Operations.complete(operation);
            recordOperation.put(player.getUniqueId(), editSession);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "[artiSketch] Building paste Error!", e);
        }
    }

    public static Clipboard loadClipboardFromFile(String uuidName) {
        File file = FileUtil.getFile(uuidName, "clipboard");
        if (file == null) {
            return null;
        }
        Clipboard clipboard;
        try (ClipboardReader reader = ClipboardFormats.findByFile(file).getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "[artiSketch]Read Sketch" + uuidName + "Fail", e);
            return null;
        }
        return clipboard;
    }


    public static void saveClipboardToFile(String uuidName, Clipboard clipboard) {
        File file = FileUtil.getFileOrCreate(uuidName, "clipboard");
        try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
            writer.write(clipboard);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "[artiSketch] Save file" + uuidName + "Faild", e);
        }
    }
}
