package com.fadedbytes.theotherside.debug.Link;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a link between a player and a debug session. The movement of the master player will be mimicked in the slave session in the specified world.
 */
public class PlayerLink {

    /**
     * Stores the links between players and debug sessions.
     */
    static List<PlayerLink> LINKED_PLAYERS = new ArrayList<>();

    /**
     * Links two players together. If any of the players are already linked, they will be unlinked first.
     * @param masterPlayer The player to link.
     * @param slavePlayer The debug player to link.
     * @param worldName The world the slave player will be linked to.
     */
    public static void link(Player masterPlayer, Player slavePlayer, String worldName) {
        unlink(masterPlayer);
        unlink(slavePlayer);

        LINKED_PLAYERS.add(new PlayerLink(masterPlayer, slavePlayer, worldName));
    }

    /**
     * Unlinks two players. The player linked to the given player will be unlinked as well.
     * @param player The player to unlink.
     */
    public static void unlink(Player player) {
        ArrayList<PlayerLink> toRemove = new ArrayList<>();
        for (PlayerLink link : LINKED_PLAYERS) {
            if (link.getMaster().equals(player) || link.getSlave().equals(player)) {
                toRemove.add(link);
            }
        }

        for (PlayerLink link : toRemove) {
            LINKED_PLAYERS.remove(link);
        }
    }

    /**
     * Gets the debug player linked to the given player.
     * @param player The player to get the debug player for.
     * @return The player linked to the given player.
     */
    public static Player getLinkedPlayer(Player player) {
        for (PlayerLink link : LINKED_PLAYERS) {
            if (link.getMaster().equals(player)) {
                return link.getSlave();
            }
            if (link.getSlave().equals(player)) {
                return link.getMaster();
            }
        }
        return null;
    }

    /**
     * Gets whether the given player is linked.
     * @param player The player to check.
     * @return Whether the given player is linked.
     */
    public static boolean isLinked(Player player) {
        for (PlayerLink link : LINKED_PLAYERS) {
            if (link.getMaster().equals(player) || link.getSlave().equals(player)) {
                return true;
            }
        }
        return false;
    }

    final Player master;
    final Player slave;
    final World world;

    /**
     * Creates a link between the given players. The movement of the master player will be mimicked in the slave session.
     * @param master The player that will have its movement mimicked.
     * @param slave The player that will mimic the movement of the master player.
     * @param worldName The world the slave player will be linked to.
     */
    private PlayerLink(Player master, Player slave, String worldName) {
        this.master = master;
        this.slave = slave;
        this.world = Bukkit.getWorld(worldName);

        if (this.world == null) {
            throw new IllegalArgumentException("World " + worldName + " does not exist.");
        }

        this.slave.setGameMode(GameMode.SPECTATOR);

        LINKED_PLAYERS.add(this);
    }

    /**
     * Updates the position of the slave player to match the master player.
     */
    void update() {
        if (master.isOnline() && slave.isOnline()) {
            Location masterLocation = master.getLocation().clone();
            masterLocation.setWorld(this.world);

            this.slave.setFlying(true);
            this.slave.teleport(masterLocation);

            if (!slave.getLocation().getBlock().isPassable()) {
                this.slave.playNote(this.slave.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.A));
            }
            if (!master.getLocation().getBlock().isPassable()) {
                this.master.playNote(this.master.getLocation(), Instrument.PIANO, Note.natural(0, Note.Tone.G));
            }
        }
    }

    /**
     * Returns the master player.
     * @return The master player.
     */
    Player getMaster() {
        return master;
    }

    /**
     * Returns the slave player.
     * @return The slave player.
     */
    private Player getSlave() {
        return slave;
    }

    /**
     * Returns the target world
     * @return The target world.
     */
    private World getWorld() {
        return world;
    }
}
