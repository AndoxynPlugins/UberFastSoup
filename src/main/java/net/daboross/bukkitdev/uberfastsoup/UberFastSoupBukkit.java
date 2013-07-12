package net.daboross.bukkitdev.uberfastsoup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class UberFastSoupBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new UberFastSoupListener(), this);
    }
}
