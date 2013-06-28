package net.daboross.bukkitdev.fastsoup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class FastSoupBukkitMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new FastSoupListener(), this);
    }
}
