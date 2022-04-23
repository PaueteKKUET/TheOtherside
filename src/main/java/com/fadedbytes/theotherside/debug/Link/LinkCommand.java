package com.fadedbytes.theotherside.debug.Link;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
                    PlayerLink.link(playerToLink, slavePlayer, playerToLink.getWorld().getName());
                    sender.sendMessage("§aPlayers linked successfully.");
                } else if (args[0].equalsIgnoreCase("unlink")) {
                    PlayerLink.unlink(playerToLink);
                    sender.sendMessage("§aPlayers unlinked successfully.");
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

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        return null;
    }
}
