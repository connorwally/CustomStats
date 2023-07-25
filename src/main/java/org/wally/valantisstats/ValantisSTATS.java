package org.wally.valantisstats;

import org.bukkit.plugin.java.JavaPlugin;
import org.wally.valantisstats.Commands.ModifyStats;

import java.util.Objects;

public final class ValantisSTATS extends JavaPlugin {

    @Override
    public void onEnable() {

        Objects.requireNonNull(this.getCommand("valstats")).setExecutor(new ModifyStats());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
