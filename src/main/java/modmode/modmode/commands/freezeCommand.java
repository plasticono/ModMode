/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.commands;

import modmode.modmode.ModMode;
import modmode.modmode.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ethanfriedman on 7/16/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class freezeCommand implements CommandExecutor {

    public static ArrayList<String> frozen = new ArrayList<>();

    public static Inventory frozenInv = Bukkit.getServer().createInventory(null, 9,
            ChatColor.RED + "You are frozen!");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

        if (cmd.getName().equalsIgnoreCase("ss")) {
            if (sender.hasPermission("modmode.freeze")) {
                if (args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        if (!frozen.contains(t.getName())) {
                            ItemStack paper = new ItemStack(Material.REDSTONE_BLOCK);
                            ItemMeta paperMeta = paper.getItemMeta();
                            paperMeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "YOU ARE FROZEN!");
                            paperMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Please connect to " + ModMode.getInstance().TEAMSPEAK,
                                    ChatColor.YELLOW + "Do not log out!"));
                            paper.setItemMeta(paperMeta);
                            frozenInv.clear();
                            frozenInv.setItem(4, paper);
                            t.openInventory(frozenInv);
                            frozen.add(t.getName());
                            t.sendMessage(ChatColor.YELLOW + "You have been frozen");

                            sender.sendMessage(ChatUtils.formatWithPrefix("&bYou have frozen " + t.getName()));

                        } else {
                            t.sendMessage(ChatColor.YELLOW + "You are no longer frozen.");
                            frozen.remove(t.getName());

                            sender.sendMessage(ChatUtils.formatWithPrefix("&bYou have unfroze " + t.getName()));
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "That player is not online!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /ss (name)");
                }
            }
        }
        return false;
    }

}
