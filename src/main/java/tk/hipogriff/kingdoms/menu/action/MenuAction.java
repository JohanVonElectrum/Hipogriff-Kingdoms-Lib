package tk.hipogriff.kingdoms.menu.action;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.utils.Logger;

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
        Logger.action(type, value, args, Logger.TaskState.CREATING, false);
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
