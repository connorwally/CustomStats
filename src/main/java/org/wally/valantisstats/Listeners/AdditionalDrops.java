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
            ValantisSTATS.getPlugin().getLogger().info(" A diamond ore block was broken");

            int bonus_amount = GenerateBonusAmount(event.getPlayer().getPersistentDataContainer());

            Location l = event.getBlock().getLocation();
            ItemStack item = new ItemStack(Material.DIAMOND);
            item.setAmount(bonus_amount);
            ValantisSTATS.getPlugin().getLogger().info(String.valueOf(bonus_amount));
            l.getWorld().dropItemNaturally(l, item);
        }

    }

    int GenerateBonusAmount(PersistentDataContainer c){
        int luck = c.get(new NamespacedKey(ValantisSTATS.getPlugin(), "miningluck"), PersistentDataType.INTEGER);
        ValantisSTATS.getPlugin().getLogger().info(String.valueOf(luck) + " The gathered luck number");
        double bonus = CalculateStatBonusMultiplier(luck);
        ValantisSTATS.getPlugin().getLogger().info(String.valueOf(bonus) + " The calculated bonus double");
        // Currently this calculation casts the bonus percentage i.e 1.2 - to an integer = 1
        // I want to change this code so that it is instead guaranteed a drop for a full 1.0, and any additional percentage chance.

        int guaranteed_drops = (int) bonus;

        ValantisSTATS.getPlugin().getLogger().info(String.valueOf(guaranteed_drops) + " The guaranteed drop");
        double additional_chance;
        additional_chance = bonus - guaranteed_drops;

        double rolled_number = random();

        if(rolled_number < additional_chance)
            guaranteed_drops++;

        return guaranteed_drops;
    }
    double CalculateStatBonusMultiplier(int stat){
        /*

          In terms of calculating a stat bonus, I will have to play around with the calculation.
          I will use a logarithmic scale, so as players gain statistics linearly, the bonus will scale down.

          Level 100 should be hard to obtain in a given skill i.e. MiningLuck = Level 100 = 1,000 MiningLuck stat
          Level 50 should grant 2 diamonds, level 100 should grant around 3 diamonds
          default = 1 1/250 = 1.004x. Level 50 = 500/250 = 2x. Level 100 = 1000/250 = 4x
         */

        double bonus;

        /*
        This calculation always provides a number greater than 1.
        By Level 100, it will grant 2.4x drops
        By Level 300, it will grant 3.5x drops
        By Level 1000, it will grant 4.7x drops

        The / log(1) changes the base of the logarithm to 1, as per the logarithmic identity.
         */
        bonus = (log(stat));


        return bonus;
    }
}
