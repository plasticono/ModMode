/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.listener;

import modmode.modmode.ModMode;
import modmode.modmode.commands.freezeCommand;
import modmode.modmode.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by ethanfriedman on 7/16/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class frozenListener implements Listener {


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(freezeCommand.frozen.contains(p.getName())){
            for(Player online : Bukkit.getOnlinePlayers()){
                if(online.hasPermission("modmode.freeze")){
                    online.sendMessage(ChatColor.RED + p.getName() + " has logged out while frozen!");
                    System.out.println("HAS LOGGED OUT WHILE FROZEN ");
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory().equals(freezeCommand.frozenInv)){
            Player p = (Player) e.getWhoClicked();

            if(freezeCommand.frozen.contains(p.getName())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(freezeCommand.frozen.contains(p.getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        if(freezeCommand.frozen.contains(p.getName())) {
           // if (e.getInventory().equals(freezeCommand.frozenInv)) {
               //p.openInventory(freezeCommand.frozenInv);
            if(e.getInventory().equals(freezeCommand.frozenInv)){
                if(freezeCommand.frozen.contains(p.getName())){
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ModMode.getInstance(), new Runnable(){
                        @Override
                        public void run() {
                            p.openInventory(freezeCommand.frozenInv);
                            p.sendMessage(ChatUtils.formatWithPrefix("&cYou may not close this inventory!"));
                        }
                    }, 1);
                }
            }
            //}
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

            if(freezeCommand.frozen.contains(p.getName())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEDEBE(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

            if(freezeCommand.frozen.contains(p.getName())){
                e.setCancelled(true);

                if(e.getDamager() instanceof Player){
                    Player d = (Player) e.getDamager();
                    d.sendMessage(ChatColor.RED + "That player is frozen!");
                }
            }
        }
    }

//    @EventHandler
//    public void onMove(PlayerMoveEvent e){
//        Player p = e.getPlayer();
//        if(freezeCommand.frozen.contains(p.getName())){
//
//
//            double fx = e.getFrom().getBlockX();
//            double fy = e.getFrom().getBlockY();
//            double fz = e.getFrom().getBlockZ();
//
//            double tx = e.getTo().getBlockX();
//            double ty = e.getTo().getBlockY();
//            double tz = e.getTo().getZ();
//
//            if(fx != tx || fy != ty || fz != tz) {
//                e.setCancelled(true);
//                p.sendMessage(ChatColor.RED + "You are frozen! Join " + ModMode.getInstance().TEAMSPEAK);
//            }
//
//        }
//   }

    @EventHandler(ignoreCancelled=true, priority= EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event)
    {

        Location from = event.getFrom();
        Location to = event.getTo();
        if ((from.getBlockX() == to.getBlockX()) && (from.getBlockZ() == to.getBlockZ())) {
            return;
        }

        Player player = event.getPlayer();
        if (freezeCommand.frozen.contains(player.getName())) {
            player.openInventory(freezeCommand.frozenInv);
            event.setTo(event.getFrom());
        }
    }
}
