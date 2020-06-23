package tk.hipogriff.kingdoms.menu.action;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.hipogriff.kingdoms.HipogriffKingdoms;

public abstract class MenuAction {

    public enum ActionEvent {
        OPEN,
        CLICK,
        CLOSE
    }

    public enum ActionType {
        FEEDBACK,
        MESSAGE,
        BROADCAST,
        COMMAND,
        SOUND,
        MENU,
        SERVER,
        CLOSE
    }

    public abstract void run(Player player);

    public static MenuAction createAction(ActionType type, String value, String... args) {
        HipogriffKingdoms.getInstance().getLogger().info(ChatColor.AQUA + "Creating action: type[" + type.toString() + "] Value[" + value + "]");
        switch (type) {
            case SOUND:
                return new SoundAction(value);
            case MENU:
                return new OpenMenuAction(value);
            case CLOSE:
                return new CloseMenuAction(value);
            default:
                return null;
        }
    }

}
