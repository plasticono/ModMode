/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.commands;

import modmode.modmode.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;

/**
 * Created by ethanfriedman on 7/23/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class staffChatCommand implements CommandExecutor, Listener{

    public static ArrayList<String> staffChat = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("sc") || command.getName().equalsIgnoreCase("staffchat")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(p.hasPermission("modmode.staffchat")){

                    if(staffChat.contains(p.getName())){
                        staffChat.remove(p.getName());
                        p.sendMessage(ChatUtils.formatWithPrefix("&aStaff Chat disabled"));
                    }else{
                        staffChat.add(p.getName());
                        p.sendMessage(ChatUtils.formatWithPrefix("&aStaff Chat enabled"));
                    }

                }else{
                    p.sendMessage(ChatUtils.formatWithPrefix("&cYou do not have permission to perform this command."));
                }

            }else{
                sender.sendMessage(ChatUtils.formatWithPrefix("&cYou must be a player to perform this command."));
            }
        }

        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(staffChat.contains(p.getName())){
            e.setCancelled(true);
            for(Player staff : Bukkit.getOnlinePlayers()){
                if (staff.hasPermission("modmode.staffchat")) {
                    staff.sendMessage(ChatUtils.format("&8[&b" + Bukkit.getServer().getName() + "&8] " +
                            PermissionsEx.getUser(p.getName()).getPrefix()  + p.getName() + "&7: &b" + e.getMessage()));
                }
            }
        }
    }
}
