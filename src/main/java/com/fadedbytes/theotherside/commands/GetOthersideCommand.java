package com.fadedbytes.theotherside.commands;

import com.fadedbytes.theotherside.TheOtherside;
import com.fadedbytes.theotherside.items.OthersideItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GetOthersideCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage("§cUsage: /getotherside <item>");
                return true;
            }

            if (args.length == 1) {
                ItemStack item = OthersideItemManager.getItem(args[0]);
                if (item == null) {
                    player.sendMessage("§cItem not found.");
                } else {
                    player.getInventory().addItem(item);
                }
            }
        } else {
            sender.sendMessage("§cThis command can only be used by players.");
        }


        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> items = new ArrayList<>();
        for (String item : OthersideItemManager.getItemKeys()) {
            if (item.startsWith(args[0])) {
                items.add(item);
            }
        }

        return items;
    }
}
