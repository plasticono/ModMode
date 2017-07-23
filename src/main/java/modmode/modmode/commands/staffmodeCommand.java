/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.commands;

import modmode.modmode.ModMode;
import modmode.modmode.utils.ChatUtils;
import modmode.modmode.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ethanfriedman on 7/16/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class staffmodeCommand implements CommandExecutor {


    private static HashMap<String, ItemStack[]> armorContents = new HashMap<>();
    private static HashMap<String, ItemStack[]> inventoryContents = new HashMap<>();
    private static HashMap<String, Integer> xplevel = new HashMap<>();

    public static void saveInventory(Player paramPlayer) {
        armorContents.put(paramPlayer.getName(), paramPlayer.getInventory().getArmorContents());
        inventoryContents.put(paramPlayer.getName(), paramPlayer.getInventory().getContents());
        xplevel.put(paramPlayer.getName(), Integer.valueOf(paramPlayer.getLevel()));
    }

    public static void loadInventory(Player paramPlayer) {
        paramPlayer.getInventory().clear();
        paramPlayer.getInventory().setContents((ItemStack[]) inventoryContents.get(paramPlayer.getName()));
        paramPlayer.getInventory().setArmorContents((ItemStack[]) armorContents.get(paramPlayer.getName()));
        paramPlayer.setLevel(((Integer) xplevel.get(paramPlayer.getName())).intValue());

        inventoryContents.remove(paramPlayer.getName());
        armorContents.remove(paramPlayer.getName());
        xplevel.remove(paramPlayer.getName());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("mod") || command.getName().equalsIgnoreCase("staff") || command.getName().equalsIgnoreCase("staffmode")) {

            Player p = (Player) sender;
            if (p.hasPermission("modmode.staffmode")) {
                if (ModMode.staff.contains(p.getName())) {
                    p.getInventory().clear();
                    p.closeInventory();
                    p.getInventory().setArmorContents(null);
                    loadInventory(p);
                    p.setHealth(((Damageable) p).getMaxHealth());
                    ModMode.staff.remove(p.getName());
                    if (ModMode.vanished.contains(p.getName())) {
                        p.performCommand("vanish");
                    }
                    p.setGameMode(GameMode.SURVIVAL);
                } else {
                    ItemStack compass = ItemBuilder.build(Material.COMPASS, 1, "&eTeleport Compass",
                            Arrays.asList(new String[]{"&7Teleport through blocks."}));

                    ItemStack book = ItemBuilder.build(Material.BOOK, 1, "&eInspect Invenotry", Arrays
                            .asList(new String[]{"&7Inspect a player's inventory by right clicking on them."}));

                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwner(p.getName());
                    skullMeta.setDisplayName(ChatColor.YELLOW + "Online Staff");
                    skullMeta.setLore(Arrays
                            .asList(new String[]{ChatUtils.format("&7See the current online staff members.")}));
                    skull.setItemMeta(skullMeta);

                    ItemStack vanish = ItemBuilder.build(Material.INK_SACK, 1, 8, "&bStatus: &7Hidden",
                            Arrays.asList("Use this tool to switch between visual settings."));

                    ItemStack clock = ItemBuilder.build(Material.WATCH, 1, "&eRandom Teleport",
                            Arrays.asList(new String[]{"&7Teleport to a random player."}));

                    ItemStack freeze = ItemBuilder.build(Material.ICE, 1, "&eFreeze",
                            Arrays.asList(new String[]{"&7Freeze a player."}));

                    saveInventory(p);
                    p.getInventory().clear();
                    p.getInventory().setArmorContents(null);
                    p.getInventory().setItem(0, compass);
                    p.getInventory().setItem(1, book);
                    p.getInventory().setItem(3, freeze);
                    p.getInventory().setItem(5, clock);
                    p.getInventory().setItem(7, vanish);
                    p.getInventory().setItem(8, skull);
                    if (!ModMode.vanished.contains(p.getName())) {
                        p.performCommand("vanish");
                    }

                    p.setGameMode(GameMode.CREATIVE);
                    ModMode.staff.add(p.getName());
                }
            }else{
                sender.sendMessage(ChatUtils.formatWithPrefix("&cYou do not have permission to enter staff mode."));
            }
        }
        return false;
    }
}