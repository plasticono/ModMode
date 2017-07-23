/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by ethanfriedman on 7/16/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class invseeCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("invsee")) {

            if (p.hasPermission("modmode.invsee")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.GRAY + "Usage: /invsee (player)");
                } else if (Bukkit.getServer().getPlayer(args[0]) != null) {
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    Inventory targetInv = targetPlayer.getInventory();
                    p.openInventory(targetInv);
                } else {
                    p.sendMessage(ChatColor.RED + "That player is not online!");
                }
            } else {
                p.sendMessage(ChatColor.WHITE + "Unknown Command. Type /help for help");
            }
            return false;
        }
        return false;
    }
}
