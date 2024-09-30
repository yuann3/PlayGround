package com.github.ckpjasher.blueprintbuild.util;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class LPUtil {
  public static boolean hasPermission(User user, String permission) {
    return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
  }

  public static boolean hasPermission(Player player, String permission) {
    if (player.isOp()) {
      return true;
    }
    if (isAdmin(player.getUniqueId())) {
      return true;
    }
    return hasPermission(
        Objects.requireNonNull(
            LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())),
        permission);
  }

  public static boolean hasPermission(String playerName, String permission) {
    return hasPermission(Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(playerName)), permission);
  }

  public static void removePermission(Player player, String permission) {
    if (hasPermission(player, permission)) {
      LuckPermsProvider.get().getUserManager().modifyUser(player.getUniqueId(), user -> {
        user.data().remove(Node.builder(permission).build());
      });
    }
  }

  public static void removePermission(String playerName, String permission) {
      removePermission(Bukkit.getPlayerExact(playerName), permission);
  }


  public static boolean isAdmin(UUID who) {
    try {
      User user = LuckPermsProvider.get().getUserManager().loadUser(who).get();

      Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
      return inheritedGroups.stream().anyMatch(g -> g.getName().equals("admin"));
    } catch (InterruptedException | ExecutionException e) {
      return false;
    }
  }

  public static void addPermission(Player player, String permission) {
    LuckPermsProvider.get().getUserManager().modifyUser(player.getUniqueId(), user -> {
      user.data().add(Node.builder(permission).build());
    });
  }

  public static void addPermission(String playerName, String permission) {
    addPermission(Bukkit.getPlayerExact(playerName), permission);
  }

  public static boolean hasPermission(CommandSender sender, String permission) {
    if (sender instanceof ConsoleCommandSender) {
      return true;
    }
    return hasPermission((Player) sender, permission);
  }
}
