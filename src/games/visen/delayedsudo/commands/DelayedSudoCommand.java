package games.visen.delayedsudo.commands;

import games.visen.delayedsudo.Main;
import games.visen.delayedsudo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class DelayedSudoCommand implements CommandExecutor {

    private Main plugin;

    public DelayedSudoCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("ds").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 3) {
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
            try {
                time = Integer.parseInt(timeString);
            } catch(NumberFormatException e) {
                return false;
            }
        } else if (timeString.contains("m")) {
            timeString = timeString.substring(0, timeString.length() - 1);
            try {
                time = Integer.parseInt(timeString) * 60;
            } catch(NumberFormatException e) {
                return false;
            }
        } else {
            try {
                time = Integer.parseInt(timeString);
            } catch(NumberFormatException e) {
                return false;
            }
        }
        String command = "";
        boolean asOp = false;
        for(String s : args) {
            if(!s.contains(args[0]) && !s.contains(args[1])) {
                if(!s.contains("-op")) {
                    command = command + s + " ";
                } else {
                    asOp = true;
                }
            }
        }

        LinkedList<String> argList = new LinkedList<String>(Arrays.asList(args));

        if(argList.contains("-op") || asOp) {
            if(!futureSender.isOp()) {
                String finalCommand = command;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            futureSender.setOp(true);
                            Bukkit.dispatchCommand(futureSender, finalCommand);
                            futureSender.setOp(false);
                        }, 20 * time);
            } else {
                String finalCommand1 = command;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Bukkit.dispatchCommand(futureSender, finalCommand1);
                }, 20 * time);
            }
        } else {
            String finalCommand2 = command;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.dispatchCommand(futureSender, finalCommand2);
            }, 20 * time);
        }

        Utils.message(sender, "&cRunning command in "  + time + " seconds.");
        return true;
    }
}
