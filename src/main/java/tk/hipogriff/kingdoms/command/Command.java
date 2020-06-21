package tk.hipogriff.kingdoms.command;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import tk.hipogriff.kingdoms.HipogriffKingdoms;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class Command implements CommandExecutor, TabCompleter {

    protected HipogriffKingdoms plugin;
    private String name;

    public Command(String name) {
        this.name = name;
    }

    public Command Register(HipogriffKingdoms plugin) {
        this.plugin = plugin;

        PluginCommand cmd = plugin.getCommand(name);

        if (cmd != null) cmd.setExecutor(this);

        return this;
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        try {
            execute(sender, label, args);

        } catch (CommandException ex) {
            if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                // Use RED by default
                sender.sendMessage(ChatColor.DARK_RED + ex.getMessage());
            }
        }

        return true;
    }

    public abstract List<String> getTabComplete(CommandSender sender, String alias, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        return getTabComplete(sender, alias, args);
    }
}
