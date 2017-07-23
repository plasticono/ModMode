/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.commands;

import modmode.modmode.ModMode;
import modmode.modmode.utils.ChatUtils;
import modmode.modmode.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by ethanfriedman on 7/15/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class vanishCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("vanish") || command.getName().equalsIgnoreCase("v")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("modmode.vanish")) {
                    if (ModMode.vanished.contains(p.getName())) {
                        //hidden
                        ModMode.vanished.remove(p.getName());
                        if (ModMode.staff.contains(p.getName())) {
                            p.getInventory().setItem(7, ItemBuilder.build(Material.INK_SACK, 1, 10, "&bStatus: &aShown", Arrays.asList("Use this tool to switch visual settings.")));
                        }
                        p.sendMessage(ChatUtils.formatWithPrefix("&eYou are now &ashown &eto all players!"));
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            online.showPlayer(p);
                        }
                        for (String staff : ModMode.staff) {
                            p.hidePlayer(Bukkit.getServer().getPlayer(staff));
                        }
                    } else {
                        //shown
                        ModMode.vanished.add(p.getName());
                        if (ModMode.staff.contains(p.getName())) {
                            p.getInventory().setItem(7, ItemBuilder.build(Material.INK_SACK, 1, 8, "&bStatus: &7Hidden",
                                    Arrays.asList("Use this tool to switch between visual settings.")));
                        }


                        p.sendMessage(ChatUtils.formatWithPrefix("&eYou are now &chidden &eto all players!"));
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if(!online.hasPermission("modmode.vanish")) {
                                online.hidePlayer(p);

                            }
                        }
                        for (String staff : ModMode.staff) {
                            p.showPlayer(Bukkit.getServer().getPlayer(staff));
                        }
                    }
                }else{
                    p.sendMessage(ChatUtils.formatWithPrefix("&cYou do not have permission to perform this command."));
                }


            } else {
                sender.sendMessage(ChatUtils.formatWithPrefix("You must be a player to perform this command!"));
            }
        }

        return false;
    }
}

