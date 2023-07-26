package org.wally.valantisstats;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.wally.valantisstats.Commands.ModifyStats;
import org.wally.valantisstats.Listeners.AdditionalDrops;

import java.util.Objects;

public final class ValantisSTATS extends JavaPlugin{

    private static ValantisSTATS plugin;
    @Override
    public void onEnable() {

        plugin = this;
        Objects.requireNonNull(this.getCommand("valstats")).setExecutor(new ModifyStats());

        getServer().getPluginManager().registerEvents(new AdditionalDrops(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin(){
        return plugin;
    }
}
