package me.tqnk.mp.command;

import me.tqnk.mp.MapPuller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements Listener {
    private List<CommandModel> commands = new ArrayList<>();
    public CommandHandler(MapPuller pl) {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }
    public void add(CommandModel cmd) {
        commands.add(cmd);
    }
    public void clear() {
        commands.clear();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] cmd = event.getMessage().substring(1).split("\\s+");
        String[] args = new String[cmd.length - 1];
        System.arraycopy(cmd, 1, args, 0, cmd.length - 1);
        if (cmd.length > 0) {
            commands.stream()
                    .filter(command -> command.isAlias(cmd[0]))
                    .findFirst().ifPresent(command -> {
                command.onCommand(event.getPlayer(), args);
                event.setCancelled(true);
            });
        }
    }
}
