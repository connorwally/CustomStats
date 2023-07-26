package org.wally.valantisstats.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.wally.valantisstats.ValantisSTATS;

public class AdditionalDrops implements Listener {

    /**
     * Prototyping the bonus item drop shit
     * @param event - The block break event that causes the additional drop
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getBlock().getType() == Material.DIAMOND_ORE){
            ValantisSTATS.getPlugin().getLogger().info("A diamond ore block was broken");
            Location l = event.getBlock().getLocation();
            ItemStack item = new ItemStack(Material.DIAMOND);
            l.getWorld().dropItemNaturally(l, item);
        }

    }
}
