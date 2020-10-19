package games.visen.delayedsudo.commands;

import com.avaje.ebean.validation.NotNull;
import games.visen.delayedsudo.Main;
import games.visen.delayedsudo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelayedSudoCommand implements CommandExecutor {

    private Main plugin;

    public DelayedSudoCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("ds").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 4) {
            return false;
        }

        CommandSender futureSender;
        if(args[0].equals("-c")) {
            futureSender = Bukkit.getConsoleSender();
        } else {
            Player player = Bukkit.getPlayer(args[0]);
            if(player == null) {
                Utils.message(sender, "&cError: &ePlayer not found.");
            }
            futureSender = player;
        }
        String timeString = args[1];
        int time;
        if(timeString.contains("s")) {
            timeString = timeString.substring(0, timeString.length() - 1);
            time = Integer.parseInt(timeString);
        } else if (timeString.contains("m")) {
            timeString = timeString.substring(0, timeString.length() - 1);
            time = Integer.parseInt(timeString) * 60;
        } else {
            time = Integer.parseInt(timeString);
        }
        String command = "";
        for(String s : args) {
            if(!s.contains(args[0]) && !s.contains(args[1])) {
                if(!s.contains("-op")) {
                    command = command + s + " ";
                }
            }
        }

        if(args[args.length])
        return true;
    }
}
