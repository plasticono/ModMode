/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.utils;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ethanfriedman on 7/15/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class ItemBuilder {
    public ItemBuilder() {
    }

    public static ItemStack build(Material material, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(ChatUtils.format(lore));
        meta.setDisplayName(ChatUtils.format(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack build(Material material, int amount, String name) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtils.format(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack build(Material material, int amount, int id, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) id);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(ChatUtils.format(lore));
        meta.setDisplayName(ChatUtils.format(name));
        item.setItemMeta(meta);
        return item;
    }
}
