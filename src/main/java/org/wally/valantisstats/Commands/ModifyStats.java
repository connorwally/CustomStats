package org.wally.valantisstats.Commands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.wally.valantisstats.ValantisSTATS;

import javax.naming.Name;

public class ModifyStats implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {


        if(!(sender instanceof Player)){
            ValantisSTATS.getPlugin().getLogger().info("The console cannot run this command");
            return false;
        }

        if(args.length < 3){
            ValantisSTATS.getPlugin().getLogger().info("Not enough arguments provided");
            return false;
        }

        Player player = (Player) sender;

        if(!args[0].equals("add")){
            ValantisSTATS.getPlugin().getLogger().info("You need to use \"add\"");
            return false;
        }

        PersistentDataContainer c = player.getPersistentDataContainer();

        // Perform the add stats on the player
        AddStats(args[1], Integer.parseInt(args[2]), c);

        return true;
    }

    /**
     * Add stats to a players persistent data.
     * @param key - The key to modify
     * @param added - The amount to add to the key
     * @param container - The container to modify
     */
    void AddStats(String key, int added, PersistentDataContainer container){
        // If the persistent data does not exist, set it to the added amount.
        if(!container.has(new NamespacedKey(ValantisSTATS.getPlugin(), key))){
            container.set(new NamespacedKey(ValantisSTATS.getPlugin(), key), PersistentDataType.INTEGER , added);
        }
        // If the persistent data already exists, add onto it and reset it.
        else{
            int currentamount = container.get(new NamespacedKey(ValantisSTATS.getPlugin(), key), PersistentDataType.INTEGER);
            currentamount += added;
            container.set(new NamespacedKey(ValantisSTATS.getPlugin(), key), PersistentDataType.INTEGER, currentamount);
        }
    }
}
