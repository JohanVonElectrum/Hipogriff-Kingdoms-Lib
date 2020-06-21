package tk.hipogriff.kingdoms.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.hipogriff.kingdoms.lang.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class commandHipogriffKingdoms extends Command {
    public commandHipogriffKingdoms(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length == 0) {
            //sender.sendMessage(plugin.getLang().getLocated("command.root", "es-es"));
        } else if (args.length == 1) {
            if (args[0].equals("evaluate")) {
                if (player != null) {
                    player.sendMessage(player.getDisplayName() + ": " + plugin.getLang().getString("location.command.evaluate"));
                } else {
                    sender.sendMessage(plugin.getLang().getLocated("location.command.evaluate"));
                }
            }
        } else if (args.length == 2) {
            if (args[0].equals("menu")) {
                if (player != null) {
                    plugin.getMenu(args[1]).display(player);
                } else {
                    sender.sendMessage(Text.getLocatedFormatted("location.command.error.player-only", plugin.getLang()));
                }
            }
        }
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList(new String[] {
                    "evaluate",
                    "menu"
            });
        } else if (args.length == 2) {
            if (args[0].equals("menu")) return new ArrayList<String>(plugin.getMenus().keySet());
        }

        return null;
    }
}
