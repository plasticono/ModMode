/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.commands;

import modmode.modmode.ModMode;
import modmode.modmode.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by ethanfriedman on 7/23/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class helpOPCommand
        implements CommandExecutor {

    public static ArrayList<String> helpopCooldown = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "No Console.");
            return true;
        } else {


            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("helpop")) {
                if (args.length < 1) {
                    p.sendMessage(ChatUtils.format("&cCorrect Usage: /helpop <Message>"));
                    return true;
                } else {
                    if (helpopCooldown.contains(p.getName())) {
                        p.sendMessage(ChatUtils.format("&cYou are still on cooldown."));
                        return true;
                    } else {
                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            message.append(args[i] + " ");
                        }
                        p.sendMessage(ChatUtils.formatWithPrefix("&aYour message has been sent to all online staff members."));
                        helpopCooldown.add(p.getName());
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ModMode.getInstance(), new Runnable() {

                            @Override
                            public void run() {
                                helpopCooldown.remove(p.getName());
                            }
                        }, 20 * 60);

                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("modmode.helpop.view")) {
                                online.sendMessage(ChatUtils.formatWithPrefix("&7" + p.getName() + " needs help!"));
                                online.sendMessage(ChatColor.RED + message.toString());
                            }
                        }

                    }
                }


            }
        }
        return false;
    }
}