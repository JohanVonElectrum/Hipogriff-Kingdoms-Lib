package tk.hipogriff.kingdoms.menu.action;

import org.bukkit.entity.Player;

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
        SERVER
    }

    public abstract void run(Player player);

    public static MenuAction createAction(ActionType type, String value, String... args) {
        switch (type) {
            case SOUND:
                return new SoundAction(args[0]);
            default:
                return null;
        }
    }

}
