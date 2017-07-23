package modmode.modmode.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethanfriedman on 7/15/17.
 */
public class ChatUtils {

    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String formatWithPrefix(String msg) {
        return ChatColor.translateAlternateColorCodes('&', "&7[&bMod Mode&7] &e" + msg);
    }

    public static List<String> format(List<String> list) {
        List<String> Format = new ArrayList<>();
        for (String String : list) {
            Format.add(ChatUtils.format(String));
        }
        return Format;
    }
}