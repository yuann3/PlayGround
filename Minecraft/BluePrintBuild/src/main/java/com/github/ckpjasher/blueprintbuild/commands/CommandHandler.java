package com.github.ckpjasher.blueprintbuild.commands;

import com.github.ckpjasher.blueprintbuild.commands.subcommands.*;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 指令管理
 *
 * @author: Dragon
 * @data: 2021/7/24 - 11:48
 */
public final class CommandHandler implements CommandExecutor, TabCompleter {
    private static final HashMap<String, BaseCommand> commands = new HashMap<>();
    private static final CommandExecutor INSTANCE = new CommandHandler();

    private CommandHandler() {
        commands.put("reload", new CommandReload());
        commands.put("save", new CommandSave());
        commands.put("load", new CommandLoad());
        commands.put("remove", new CommandRemove());
        commands.put("rename", new CommandRename());
        commands.put("list", new CommandList());
        commands.put("gui", new CommandGui());
        commands.put("project", new CommandProject());
        commands.put("op", new CommandOp());
        commands.put("undo", new CommandUndo());
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender,@NonNull  Command command,@NonNull  String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("""
                    §6/bpb gui - 打开管理界面
                    §6/bpb undo - 撤回上一次操作
                    §6/bpb save <建筑蓝图名称> <素材库ID> - 保存蓝图
                    §6/bpb load <建筑名称> <素材库ID> - 获取对应素材库的建筑蓝图
                    §6/bpb remove <建筑名称> <素材库ID> - 移出指定素材库的建筑蓝图
                    §6/bpb rename <建筑名称> <建筑新名称> <素材库ID> - 重命名对应的建筑蓝图
                    §6/bpb list <素材库ID> - 列出指定素材库的建筑蓝图
                    §6/bpb project list - 显示项目素材库的所有子分类素材库
                    §6/bpb project add <素材库显示名称> <素材库ID> - 添加子分类素材库
                    §6/bpb project remove <素材库ID> - 移除子分类素材库
                    §6/bpb project builder add <玩家ID> <素材库ID> - 设置哪些玩家可以访问哪些素材库, 并给予权限
                    §6/bpb project builder remove <玩家ID> <素材库ID> - 移除玩家对素材库的访问权限
                    §6/bpb op add <玩家ID> <素材库ID> - 设置玩家拥有对某素材库所有权限
                    """);
        } else if (commands.containsKey(args[0])) {
            commands.get(args[0]).execute(sender, args);
        } else {
            sender.sendMessage("§4未知命令");
        }
        return true;
    }


    public static CommandExecutor getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        if (!(sender instanceof Player) || args.length > 2) {
            return null;
        }
        return new ArrayList<>(commands.keySet());
    }
}
