package com.fadedbytes.theotherside.debug.Link;

import com.fadedbytes.theotherside.world.TheOthersideWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages the /link command.
 * <br>
 * Usage: /link (link | unlink) {@literal <player>} [slavePlayer] [world]
 */
public class LinkCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        switch (args.length) {
            case 0 -> sender.sendMessage("§c/link (link | unlink) <player> [slavePlayer] [world]");
            case 1 -> {
                switch (args[0]) {
                    case "link" -> sender.sendMessage("§c/link link <player> <slavePlayer> [world]");
                    case "unlink" -> sender.sendMessage("§c/link unlink <player> [slavePlayer]");
                    default -> sender.sendMessage("§c/link (link | unlink) <player> [slavePlayer] [world]");
                }
            }
            case 2 -> {
                if (args[0].equalsIgnoreCase("unlink")) {
                    Player playerToUnlink = Bukkit.getPlayer(args[1]);
                    if (playerToUnlink == null) {
                        sender.sendMessage("§cPlayer not found.");
                    } else {
                        if (!PlayerLink.isLinked(playerToUnlink)) {
                            sender.sendMessage("§cPlayer is not linked.");
                        } else {
                            PlayerLink.unlink(playerToUnlink);
                            sender.sendMessage("§aPlayer unlinked successfully.");
                        }
                    }

                } else if (args[0].equalsIgnoreCase("link")) {
                    if (sender instanceof Player masterPlayer) {
                        Player slavePlayer = Bukkit.getPlayer(args[1]);
                        if (slavePlayer == null) {
                            sender.sendMessage("§cPlayer not found.");
                        } else {
                            PlayerLink.link(masterPlayer, slavePlayer, masterPlayer.getWorld().getName());
                            sender.sendMessage("§aPlayer linked successfully.");
                        }
                    } else {
                        sender.sendMessage("§c/link link <player> <slavePlayer> [world]");
                    }
                }
            }
            case 3 -> {
                Player playerToLink = Bukkit.getPlayer(args[1]);
                Player slavePlayer = Bukkit.getPlayer(args[2]);

                if (playerToLink == null) {
                    sender.sendMessage("§cPlayer " + args[1] + " not found.");
                    return true;
                }

                if (slavePlayer == null) {
                    sender.sendMessage("§cPlayer " + args[2] + " not found.");
                    return true;
                }

                if (args[0].equalsIgnoreCase("link")) {
                    if (TheOthersideWorld.getWorld() == null) {
                        sender.sendMessage("§cTheOthersideWorld is not loaded.");
                    } else {
                        if (playerToLink.equals(slavePlayer)) {
                            sender.sendMessage("§cYou can't link yourself.");
                            break;
                        }
                        PlayerLink.link(playerToLink, slavePlayer, TheOthersideWorld.getWorld().getName());
                        sender.sendMessage("§aPlayers linked successfully.");
                    }
                } else if (args[0].equalsIgnoreCase("unlink")) {
                    PlayerLink.unlink(playerToLink);
                    sender.sendMessage("§aPlayers unlinked successfully.");
                } else {
                    sender.sendMessage("§c/link (link | unlink) <player> <slavePlayer> [world]");
                }
            }
            case 4 -> {

                Player playerToLink = Bukkit.getPlayer(args[1]);
                Player slavePlayer = Bukkit.getPlayer(args[2]);

                if (playerToLink == null) {
                    sender.sendMessage("§cPlayer " + args[1] + " not found.");
                    return true;
                }

                if (slavePlayer == null) {
                    sender.sendMessage("§cPlayer " + args[2] + " not found.");
                    return true;
                }

                if (args[0].equalsIgnoreCase("link")) {
                    try {
                        if (playerToLink.equals(slavePlayer)) {
                            sender.sendMessage("§cYou can't link yourself.");
                            break;
                        }
                        PlayerLink.link(playerToLink, slavePlayer, args[3]);
                        sender.sendMessage("§aPlayers linked successfully.");
                    } catch (IllegalArgumentException exception) {
                        sender.sendMessage("§cWorld not found.");
                    }
                } else if (args[0].equalsIgnoreCase("unlink")) {
                    sender.sendMessage("§c/link unlink <player> <slavePlayer>");
                }
            }
            default -> sender.sendMessage("§c/link (link | unlink) <player> [slavePlayer] [world]");
        }

        return true;
    }

    private static List<String> OPTIONS = Arrays.asList("link", "unlink");
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> tabs = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                for (String option : OPTIONS) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
                        tabs.add(option);
                    }
                }
            }
            case 2 -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        tabs.add(player.getName());
                    }
                }
            }
            case 3 -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                        if (!args[1].equalsIgnoreCase(player.getName())) {
                            tabs.add(player.getName());
                        }
                    }
                }
            }
            case 4 -> {
                if (!args[0].equalsIgnoreCase("link")) break;
                for (World world : Bukkit.getWorlds()) {
                    if (world.getName().toLowerCase().startsWith(args[3].toLowerCase())) {
                        tabs.add(world.getName());
                    }
                }
            }
        }
        return tabs;
    }
}
