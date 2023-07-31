package org.wally.valantisstats.Commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.wally.valantisstats.ValantisSTATS;

public class ModifyStats implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        // /valstats add <stat> <amount>

        // Ensure a player is running the command
        if(!(sender instanceof Player)){
            ValantisSTATS.getPlugin().getLogger().info("The console cannot run this command");
            return false;
        }

        // Ensure enough arguments are provided
        if(args.length < 3){
            ValantisSTATS.getPlugin().getLogger().info("Not enough arguments provided");
            return false;
        }


        Player player = (Player) sender;

        // Ensure that a valid command has been sent
        if(!(args[0].equals("add") || args[0].equals("set"))){
            ValantisSTATS.getPlugin().getLogger().info("You need to use \"add\"");
            return false;
        }

        PersistentDataContainer c = player.getPersistentDataContainer();

        // Ensure that the entered amount can be converted to an integer.
        int amount;
        try{
            amount = Integer.parseInt(args[2]);
        }
        catch(NumberFormatException e){
            ValantisSTATS.getPlugin().getLogger().info("The amount must be an integer");
            return false;
        }

        // Perform the add stats on the player
        if(args[0].equals("add"))
            AddStats(args[1], amount, c);

        if(args[0].equals("set"))
            SetStats(args[1], amount, c);

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
            int currentamount = container.get(new NamespacedKey(ValantisSTATS.getPlugin(), key), PersistentDataType.INTEGER) + added;
            container.set(new NamespacedKey(ValantisSTATS.getPlugin(), key), PersistentDataType.INTEGER, currentamount);
        }
    }

    void SetStats(String key, int added, PersistentDataContainer container){
    }
}
