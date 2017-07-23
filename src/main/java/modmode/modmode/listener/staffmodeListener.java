/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.listener;

import com.sun.org.apache.xpath.internal.operations.Mod;
import modmode.modmode.ModMode;
import modmode.modmode.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by ethanfriedman on 7/16/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class staffmodeListener implements Listener {
    private Inventory staffOnline = Bukkit.getServer().createInventory(null, 27, ChatColor.GREEN + "Online Staff");

    ItemStack vanish = new ItemStack(351, 1, (short) 10);
    ItemStack unVanish = new ItemStack(351, 1, (short) 8);

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory() == staffOnline) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        e.setFormat(ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(p.getDisplayName()).getPrefix()) + e.getPlayer().getName() + ChatColor.GRAY + ": " + e.getMessage());
    }

    @EventHandler
    public void oNClick(InventoryClickEvent e) {
        if (e.getInventory().equals(staffOnline)) {
            e.setCancelled(true);
        }
        if (e.getInventory().getName().equalsIgnoreCase(ChatColor.GREEN + "Online Staff")) {
        }
    }

    @EventHandler
    public void onClik(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().equals(staffOnline)){
            SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
            p.teleport(Bukkit.getPlayer(meta.getOwner()));
    //        p.teleport(Bukkit.getPlayer((.getOwner()));
            e.setCancelled(true);
            p.closeInventory();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!ModMode.staff.contains(p.getName())) {
            return;
        }
        e.setCancelled(true);
        List<String> staff;
        switch (e.getItem().getType()) {
            case SKULL_ITEM:
                if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                    this.staffOnline.clear();
                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        if (online.hasPermission("modmode.staffmode")) {
                            PermissionUser user = PermissionsEx.getUser(online);
                            staff = new ArrayList();
                            staff.add(online.getName());

//                            ItemStack name = new ItemStack(Material.SKULL_ITEM);
//                            ItemMeta nameMeta = name.getItemMeta();
//                            nameMeta.setDisplayName(ChatUtils.format(PermissionsEx.getUser(p.getDisplayName()).getPrefix() + p.getName()));
//                            name.setItemMeta(nameMeta);
                            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                            skullMeta.setOwner(online.getName());
                            skullMeta.setDisplayName(ChatUtils.format(PermissionsEx.getUser(online.getDisplayName()).getPrefix() + online.getName()));
                            skullMeta.setLore(Arrays
                                    .asList(new String[]{ChatUtils.format("&7&l&m--------------------------"), ChatUtils.format("&aClick to teleport")}));
                            skull.setItemMeta(skullMeta);

                            this.staffOnline.addItem(new ItemStack[] { skull });
                        }
                        p.openInventory(this.staffOnline);
                    }
                }
                break;
            case WATCH:
                if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                    if (p.hasPermission("modmode.staffmode")) {
                        Random r = new Random();
                        Object players = new ArrayList();
                        List<Player> arrayOfPlayer2 = (List<Player>) Bukkit.getServer().getOnlinePlayers();
                        int sonline = arrayOfPlayer2.size();
                        for (int stafff = 0; stafff < sonline; stafff++) {
                            Player online = arrayOfPlayer2.get(stafff);
                            if (online != p) {
                                ((ArrayList) players).add(online);
                            }
                        }
                        if (((ArrayList) players).size() == 0) {
                            p.sendMessage(ChatUtils.format("&cNobody is online... damn you are a loner."));
                            return;
                        }
                        int index = r.nextInt(((ArrayList) players).size());
                        Player loc = (Player) ((ArrayList) players).get(index);
                        p.teleport(loc);
                        p.sendMessage(ChatUtils.format("&7Teleporting you to &e" + loc.getName() + "&7."));
                    } else {
                    }
                }
                break;
            case INK_SACK:
                ItemMeta vanishMeta = this.vanish.getItemMeta();
                vanishMeta.setDisplayName(ChatUtils.format("&bStatus: &7Hidden"));
                this.vanish.setItemMeta(vanishMeta);

                ItemMeta unVanishMeta = this.unVanish.getItemMeta();
                unVanishMeta.setDisplayName(ChatUtils.format("&bStatus: &aShown"));
                this.unVanish.setItemMeta(unVanishMeta);
                if (!ModMode.vanished.contains(p.getName())) {
                    p.setItemInHand(this.unVanish);
                    p.performCommand("vanish");
                } else {
                    p.setItemInHand(this.vanish);
                    p.performCommand("vanish");
                }
                break;


        }
    }
    @EventHandler
    public void onInteractwEntity(PlayerInteractEntityEvent e){
        if(e.getRightClicked() instanceof Player){
            Player t = (Player) e.getRightClicked();
            Player p = e.getPlayer();
            if(e.getPlayer().getItemInHand().getType() == Material.ICE) {
                p.performCommand("ss " + t.getName());
            }

        }
    }

    @EventHandler
    public void onItemSpawn(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if ((p.getGameMode().equals(GameMode.CREATIVE)) && (ModMode.staff.contains(p.getName())))
            e.setCancelled(true);
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if ((p.getGameMode().equals(GameMode.CREATIVE)) && (ModMode.staff.contains(p.getName()))
                && (e.getInventory().getType().equals(InventoryType.CREATIVE))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (ModMode.staff.contains(e.getPlayer().getName()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (ModMode.staff.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        if ((e.getRightClicked() instanceof Player)) {
            Player t = (Player) e.getRightClicked();

            if ((p.getItemInHand().getType() == Material.BOOK) && (ModMode.staff.contains(p.getName()))) {
                p.performCommand("invsee " + t.getName());
            }
        }
    }
}

