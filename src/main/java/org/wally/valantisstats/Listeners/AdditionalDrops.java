package org.wally.valantisstats.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.wally.valantisstats.ValantisSTATS;

import static java.lang.Math.log;
import static java.lang.Math.random;

public class AdditionalDrops implements Listener {

    /**
     * Prototyping the bonus item drop shit
     * @param event - The block break event that causes the additional drop
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getBlock().getType() == Material.DIAMOND_ORE){

            event.setDropItems(false);
            int amount = 1;

            // Calculate the bonus amount to drop
            int bonus_amount = generateBonusAmount(event.getPlayer().getPersistentDataContainer());

            // Generate the item and set the amount
            ItemStack item = new ItemStack(Material.DIAMOND);
            item.setAmount(amount * bonus_amount);

            // Get the location of the event and drop the item with assigned amount
            Location l = event.getBlock().getLocation();
            l.getWorld().dropItemNaturally(l, item);
        }

    }

    int generateBonusAmount(PersistentDataContainer c){
        int luck = c.get(new NamespacedKey(ValantisSTATS.getPlugin(), "miningluck"), PersistentDataType.INTEGER);
        double bonus = calculateStatBonusMultiplier(luck);
        // Currently this calculation casts the bonus percentage i.e 1.2 - to an integer = 1
        // I want to change this code so that it is instead guaranteed a drop for a full 1.0, and any additional percentage chance.

        int guaranteed_drops = (int) bonus;

        double additional_chance;
        additional_chance = bonus - guaranteed_drops;
        double rolled_number = random();
        if(rolled_number < additional_chance)
            guaranteed_drops++;

        return guaranteed_drops;
    }
    double calculateStatBonusMultiplier(int stat){

        /*
        In terms of calculating a stat bonus, I will have to play around with the calculation.
        I will use a logarithmic scale, so as players gain stats linearly, the bonus will scale down.

        Level 100 should be hard to obtain in a given skill i.e. MiningLuck = Level 100 = 1,000 MiningLuck stat

        This calculation always provides a number greater than 1.
        By Level 100, it will grant 1.9x drops
        By Level 200, it will grant 2.2x drops
        By Level 500, it will grant 2.6x drops
        By Level 1000, it will grant 2.9x drops

        The / log(8) changes the base of the logarithm to 8, as per the logarithmic identity.
         */

        return ((log(stat + 180)) / (log(8))) - 1.5;
    }
}
