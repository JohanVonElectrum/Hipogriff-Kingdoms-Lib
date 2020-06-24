package tk.hipogriff.kingdoms.utils;

import org.bukkit.ChatColor;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.menu.action.MenuAction;

import java.io.File;

public class Logger {

    public enum TaskState {
        CREATING,
        CREATED,
        LOADING,
        LOADED,
        CANCELLED,
        ERROR
    }

    public enum FileType {
        UNKNOWN,
        LANG,
        MENU,
        CONFIG,
        FOLDER
    }

    private static java.util.logging.Logger LOGGER = HipogriffKingdoms.getInstance().getLogger();

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void warning(String msg, boolean spected) {
        LOGGER.warning(ChatColor.GOLD + "[" + (!spected ? "UN" : "") + "SPECTED]" + msg);
    }

    public static void severe(String msg) {
        LOGGER.severe(ChatColor.RED + msg);
    }

    public static void updated(String msg) {
        info(ChatColor.AQUA + msg);
    }

    public static void finished(String msg) {
        info(ChatColor.GREEN + msg);
    }

    public static void cancelled(String msg, boolean important, boolean wanted) {
        if (important == wanted) warning(msg, wanted);
        else if (wanted) updated(msg);
        else severe(msg);
    }

    public static void file(File file, FileType type, TaskState state, boolean important) {
        String prefix = file.isFile() ? "file" : "folder";
        String target = prefix + "[" + type + "]";
        String middle = " " + target + ": ";
        String end = middle + file.getName();
        switch (state) {
            case CREATING:
                updated("Creating" + end);
                break;
            case CREATED:
                finished("CREATED" + end);
                break;
            case LOADING:
                updated("Loading" + end);
                break;
            case LOADED:
                finished("Loaded" + end);
                break;
            case CANCELLED:
                cancelled("Cancelled" + end, important, true);
                break;
            case ERROR:
                cancelled("Cancelled" + end, important, false);
                break;
        }
    }

    public static void action(MenuAction.ActionType type, String value, String[] args, TaskState state, boolean important) {
        String prefix = "action";
        String target = prefix + "[" + type + "]";
        String middle = " " + target + ": ";
        String end = middle + value + " with " + args.length + " args";
        switch (state) {
            case CREATING:
                updated("Creating" + end);
                break;
            case CREATED:
                finished("CREATED" + end);
                break;
            case LOADING:
                updated("Loading" + end);
                break;
            case LOADED:
                finished("Loaded" + end);
                break;
            case CANCELLED:
                cancelled("Cancelled" + end, important, true);
                break;
            case ERROR:
                cancelled("Cancelled" + end, important, false);
                break;
        }
    }

}
