package com.onarandombox.MultiverseCore.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.command.BaseCommand;

public class ModifySetCommand extends BaseCommand {
    
    public ModifySetCommand(MultiverseCore plugin) {
        super(plugin);
        this.name = "Modify a World (Set a value)";
        this.description = "Modify various aspects of worlds. See the help wiki for how to use this command properly. If you do not include a world, the current world will be used";
        this.usage = "/mvmodify" + ChatColor.GREEN + " SET {PROPERTY} {VALUE}" + ChatColor.GOLD + " [WORLD] ";
        this.minArgs = 3;
        this.maxArgs = 4;
        this.identifiers.add("mvmodify set");
        this.permission = "multiverse.world.modify";
        this.requiresOp = true;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        // We NEED a world from the command line
        Player p = null;
        if (!(sender instanceof Player)) {
            p = (Player) sender;
        }
        
        if (args.length == 3 && p == null) {
            sender.sendMessage("From the command line, WORLD is required.");
            sender.sendMessage(this.description);
            sender.sendMessage(this.usage);
            sender.sendMessage("Nothing changed.");
            return;
        }
        
        MVWorld world;
        String value = args[1];
        String property = args[2];
        
        if (args.length == 3) {
            world = this.plugin.getMVWorld(p.getWorld().getName());
        } else {
            world = this.plugin.getMVWorld(args[3]);
        }
        
        if (world == null) {
            sender.sendMessage("That world does not exist!");
            return;
        }
        
        if (!ModifyCommand.validateAction(Action.Set, property)) {
            sender.sendMessage("Sorry, you can't SET " + property);
            sender.sendMessage("Please visit our wiki for more information: URLGOESHERE FERNFERRET DON'T FORGET IT!");
            return;
        }
        if (world.setVariable(property, value)) {
            sender.sendMessage("Property " + property + " was set to " + value);
        } else {
            sender.sendMessage("There was an error setting " + property);
        }
    }
    
}
