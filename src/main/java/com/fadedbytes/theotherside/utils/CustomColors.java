package com.fadedbytes.theotherside.utils;

import net.md_5.bungee.api.ChatColor;

/**
 * CustomColors used to colorize item names and lore by tier.
 */
public enum CustomColors {

    BASIC("#0CFADD"),
    RARE("#48D909"),
    EPIC("#F0C416"),
    LEGENDARY("#DB5314"),
    EXCLUSIVE("#FF08E6");


    private final ChatColor color;

    CustomColors(String hexValue) {
        color = ChatColor.of(hexValue);
    }


    @Override
    public String toString() {
        return color.toString();
    }
}
