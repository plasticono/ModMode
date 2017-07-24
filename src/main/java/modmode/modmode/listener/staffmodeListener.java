/*
 * Copyright (c) 2017. This class was created by and for the use of plasticono. Do not use any direct code from this class without direct permission from plasticono.
 */

package modmode.modmode.listener;

import modmode.modmode.ModMode;
import modmode.modmode.utils.ChatUtils;
import modmode.modmode.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
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
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by ethanfriedman on 7/16/17.
 * Please Subscribe to plasticono @ www.youtube.com/channel/plasticonopvp
 * Do not use this code without direct permission.
 */
public class staffmodeListener implements Listener {

    private Inventory staffOnline = Bukkit.getServer().createInventory(null, 27, ChatColor.GREEN + "Online Staff");
    private Inventory timezonePicker = Bukkit.getServer().createInventory(null, 18, ChatColor.GRAY + "Chose your Timezone");

    private static ArrayList<String> nameWaiting = new ArrayList<>();
    private static ArrayList<String> ageWaiting = new ArrayList<>();

    ItemStack vanish = new ItemStack(351, 1, (short) 10);
    ItemStack unVanish = new ItemStack(351, 1, (short) 8);

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory() == staffOnline) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
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
    public void onJoin(PlayerJoinEvent e){
        if(!ModMode.getInstance().getConfig().contains(e.getPlayer().getName())){
            FileConfiguration config = ModMode.getInstance().getConfig();
            Player p = e.getPlayer();
            config.set(p.getName() + ".name", "Not Set");
            config.set(p.getName() + ".age", "Not Set");
            config.set(p.getName() + ".timezone", "Not Set");

            ModMode.getInstance().saveConfig();

            if(!ModMode.staff.contains(p.getName())){
                p.performCommand("mod");
            }

            for(Player online : Bukkit.getOnlinePlayers()){
                if(online.hasPermission("modmode.staffmode")){
                    online.sendMessage(ChatUtils.format("&b" + p.getName() + " has connected to the server."));
                }
            }
        }
    }

    @EventHandler
    public void onSelfClick(InventoryClickEvent e){
        if(e.getInventory().getName().equalsIgnoreCase(e.getWhoClicked().getName())){
                Player p = (Player) e.getWhoClicked();
                switch(e.getCurrentItem().getType()){
                    case PAPER:
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(ChatColor.GREEN + "     Please enter your name in chat.");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");

                        nameWaiting.add(p.getName());

                        break;
                    case BOOK:
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(ChatColor.GREEN + "     Please enter your age in chat.");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");
                        p.sendMessage(" ");

                        ageWaiting.add(p.getName());
                        break;
                    case WATCH:
                        e.setCancelled(true);
                        timezonePicker.clear();
                        ItemStack GMT = ItemBuilder.build(Material.PAPER, 1, ChatColor.GRAY + "GMT");
                        ItemStack EST = ItemBuilder.build(Material.PAPER, 1, ChatColor.GRAY + "EST");
                        ItemStack CST = ItemBuilder.build(Material.PAPER, 1, ChatColor.GRAY + "CST");
                        ItemStack PST = ItemBuilder.build(Material.PAPER, 1, ChatColor.GRAY + "PST");
                        ItemStack UTC = ItemBuilder.build(Material.PAPER, 1, ChatColor.GRAY + "UTC");
                        ItemStack AEST = ItemBuilder.build(Material.PAPER, 1, ChatColor.GRAY + "AEST");

                        timezonePicker.addItem(GMT);
                        timezonePicker.addItem(EST);
                        timezonePicker.addItem(CST);
                        timezonePicker.addItem(PST);
                        timezonePicker.addItem(UTC);
                        timezonePicker.addItem(AEST);
                        p.closeInventory();
                        p.openInventory(timezonePicker);
                    default:
                        e.setCancelled(true);
                        return;
                }
        }else{
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTimeZone(InventoryClickEvent e){
        if(e.getInventory().equals(timezonePicker)){
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            switch(e.getSlot()){
                case 0:
                    //GMT
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + TimeZone.getTimeZone("GMT").getDisplayName()));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", TimeZone.getTimeZone("GMT").getDisplayName());
                    ModMode.getInstance().saveConfig();
                    break;
                case 1:
                    //EST
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + TimeZone.getTimeZone("EST").getDisplayName()));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", TimeZone.getTimeZone("EST").getDisplayName());
                    ModMode.getInstance().saveConfig();
                    break;
                case 2:
                    //CST
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + TimeZone.getTimeZone("CST").getDisplayName()));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", TimeZone.getTimeZone("CST").getDisplayName());
                    ModMode.getInstance().saveConfig();
                    break;
                case 3:
                    //pst
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + TimeZone.getTimeZone("PST").getDisplayName()));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", TimeZone.getTimeZone("PST").getDisplayName());
                    ModMode.getInstance().saveConfig();
                    break;
                case 4:
                    //UTC
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + TimeZone.getTimeZone("UTC").getDisplayName()));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", TimeZone.getTimeZone("UTC").getDisplayName());
                    ModMode.getInstance().saveConfig();
                    break;
                case 5:
                    //aest
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + TimeZone.getTimeZone("MST").getDisplayName()));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", TimeZone.getTimeZone("MST").getDisplayName());
                    ModMode.getInstance().saveConfig();
                    break;
                default:
                    p.closeInventory();
                    p.sendMessage(ChatUtils.format("&cTimezone set to " + "Not set"));

                    ModMode.getInstance().getConfig().set(p.getName() + ".timezone", "Not set");
                    break;
            }
        }
    }

    @EventHandler
    public void onChate(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(nameWaiting.contains(p.getName())){
            e.setCancelled(true);
            if(e.getMessage().contains(" ")){
                p.sendMessage(ChatColor.RED + "Your name can't have spaces.");
            }else {
                if(e.getMessage().length() > 16){
                    p.sendMessage(ChatUtils.format("&cYour name can't be longer 16 characters."));
                }else {
                    p.sendMessage(ChatUtils.format("&bYou have set your name to " + e.getMessage()));
                    ModMode.getInstance().getConfig().set(p.getName() + ".name", e.getMessage());
                    ModMode.getInstance().saveConfig();
                    nameWaiting.remove(p.getName());
                }
            }
        }else if(ageWaiting.contains(p.getName())){
            e.setCancelled(true);
            if(e.getMessage().contains(" ")){
                p.sendMessage(ChatColor.RED + "Your age can't have spaces");
            }else{
                String text = e.getMessage();
                String number;

                if (Pattern.matches("[a-zA-Z]+", text) == false) {
                    number = text;
                    int age = Integer.parseInt(number);
                    p.sendMessage(ChatColor.AQUA + "You have set your age to " + age);
                    ModMode.getInstance().getConfig().set(p.getName() + ".age", age);
                    ModMode.getInstance().saveConfig();
                    ageWaiting.remove(p.getName());
                }else{
                    p.sendMessage(ChatUtils.format("&cYour age may not contain letters."));
                }
            }
        }
    }

    @EventHandler
    public void onClik(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().equals(staffOnline)) {
            if (e.getClick() == ClickType.LEFT) {
                SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
                p.teleport(Bukkit.getPlayer(meta.getOwner()));
                //        p.teleport(Bukkit.getPlayer((.getOwner()));
                e.setCancelled(true);
                p.closeInventory();
            } else if (e.getClick() == ClickType.RIGHT) {
                SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
                String inspectName = meta.getOwner();
                Inventory info = Bukkit.getServer().createInventory(null, 54, String.valueOf(Bukkit.getPlayer(meta.getOwner()).getName()));
                ItemStack name = ItemBuilder.build(Material.PAPER, 1,
                      ChatColor.GRAY  + "Name: "+ ModMode.getInstance().getConfig().getString(inspectName + ".name"));

                ItemStack age = ItemBuilder.build(Material.BOOK, 1, ChatColor.GRAY + "Age: " + ModMode.getInstance().getConfig().getInt(inspectName + ".age"));
                ItemStack timezone;
                if(ModMode.getInstance().getConfig().getString(inspectName + ".timezone").equalsIgnoreCase("Not Set")){
                    timezone = ItemBuilder.build(Material.WATCH,
                            1, ChatColor.GRAY + "Timezone: Not Set");
                }else {
                    TimeZone ts = TimeZone.getTimeZone(ModMode.getInstance().getConfig().getString(inspectName + ".timezone"));
                    timezone = ItemBuilder.build(Material.WATCH,
                            1, ChatColor.GRAY + "Timezone: " + ts.getDisplayName());
                }

               ItemStack ip = ItemBuilder.build(Material.HOPPER, 1, ChatUtils.format(
                       "&7IP: " + Bukkit.getServer().getPlayer(inspectName).getAddress().getAddress().getAddress().toString()));

                info.clear();
                if(p.hasPermission("modmode.viewIP")){
                    info.setItem(40, ip);
                }else{
                    info.setItem(40, new ItemStack(Material.AIR));
                }
                info.setItem(20, name);
                info.setItem(22, age);
                info.setItem(24, timezone);
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(info);
            }
        }
    }
    //Age
    //Name
    //Timezone
    //IP Address
    //...

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
                                    .asList(new String[]{ChatUtils.format("&7&l&m--------------------------"), ChatUtils.format("&aLeft Click to teleport"), ChatUtils.format("&cRight Click for more information")}));
                            skull.setItemMeta(skullMeta);

                            this.staffOnline.addItem(new ItemStack[]{skull});
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

            default:
                return;
        }
    }

    @EventHandler
    public void onInteractwEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player t = (Player) e.getRightClicked();
            Player p = e.getPlayer();
            if (e.getPlayer().getItemInHand().getType() == Material.ICE) {
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

