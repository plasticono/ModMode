package modmode.modmode;

import modmode.modmode.commands.freezeCommand;
import modmode.modmode.commands.invseeCommand;
import modmode.modmode.commands.staffmodeCommand;
import modmode.modmode.commands.vanishCommand;
import modmode.modmode.listener.frozenListener;
import modmode.modmode.listener.staffmodeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;

public final class ModMode extends JavaPlugin {


    public String TEAMSPEAK = getConfig().getString("teamspeak");
    public static ArrayList<String> staff = new ArrayList<>();
    public static ArrayList<String> vanished = new ArrayList<>();
    private static ModMode instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("vanish").setExecutor(new vanishCommand());
        getCommand("v").setExecutor(new vanishCommand());
        getCommand("ss").setExecutor(new freezeCommand());
        getCommand("mod").setExecutor(new staffmodeCommand());
        getCommand("staff").setExecutor(new staffmodeCommand());
        getCommand("invsee").setExecutor(new invseeCommand());
        getCommand("staffmode").setExecutor(new staffmodeCommand());

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new frozenListener(), this);
        pm.registerEvents(new staffmodeListener(), this);


        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }

    public static ModMode getInstance() {
        return instance;
    }
}
