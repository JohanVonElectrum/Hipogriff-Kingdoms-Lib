package tk.hipogriff.kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.hipogriff.kingdoms.command.Commands;
import tk.hipogriff.kingdoms.config.PluginConfig;
import tk.hipogriff.kingdoms.event.Events;
import tk.hipogriff.kingdoms.lang.Lang;
import tk.hipogriff.kingdoms.menu.InventoryMenu;
import tk.hipogriff.kingdoms.menu.MenuConfig;
import tk.hipogriff.kingdoms.player.HipogriffPlayer;
import tk.hipogriff.kingdoms.utils.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public final class HipogriffKingdoms extends JavaPlugin {

    public static HipogriffKingdoms getInstance() {
        return getPlugin(HipogriffKingdoms.class);
    }

    private PluginConfig config;
    private Lang lang;
    private HashMap<String, InventoryMenu> menus;
    private Commands commands;

    public Lang getLang() {
        return lang;
    }
    public InventoryMenu getMenu(String name) {
        return menus.get(name);
    }
    public HashMap<String, InventoryMenu> getMenus() {
        return menus;
    }
    public InventoryMenu getMenuByTitle(String title) {
        for (InventoryMenu menu: menus.values()) {
            if (menu.getConfig().getTitle().equals(title)) return menu;
        }
        return null;
    }

    @Override
    public void onEnable() {
        loadConfig();
        loadLang();
        loadMenus();
        loadEvents();
        loadPlayers();

        commands = new Commands(this);

        Logger.finished("\n\n" + this.getName() + " has been ENABLED\n\n");
    }

    @Override
    public void onDisable() {
        Logger.severe("\n\n" + this.getName() + " has been DISABLED\n\n");
    }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            Logger.file(getDataFolder(), Logger.FileType.FOLDER, Logger.TaskState.CREATING, true);
            getDataFolder().mkdir();
            Logger.file(getDataFolder(), Logger.FileType.FOLDER, Logger.TaskState.CREATED, true);
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        config = new PluginConfig();
        if (config.load()) {
            Logger.finished("Config loaded.");
        }
    }

    public void loadLang() {
        lang = new Lang();
        if (lang.load()) {
            Logger.finished("lang loaded with " + lang.getCode());
            Logger.finished("lang selected: " + lang.getLocated("location.lang.set"));
        }
    }

    public void loadMenu(File file) {
        Logger.file(file, Logger.FileType.MENU, Logger.TaskState.LOADING, false);
        MenuConfig menu = new MenuConfig(file);
        if (menu.load()) {
            menus.put(menu.getFile().getName(), new InventoryMenu(menu));
            Logger.file(file, Logger.FileType.MENU, Logger.TaskState.LOADED, false);
        } else {
            Logger.file(file, Logger.FileType.MENU, Logger.TaskState.ERROR, false);
        }
    }

    public void loadMenu(String menuName) {
        File file = new File(HipogriffKingdoms.getInstance().getDataFolder(), "menu/" + menuName + ".yml");
        loadMenu(file);
    }

    public void loadMenus() {
        menus = new HashMap<>();
        File folder = new File(getDataFolder(), "menu");
        if (!folder.exists()) folder.mkdirs();
        for (File file: folder.listFiles()) {
            loadMenu(file);
        }
        List<String> defaultMenus = config.getStringList("default.menus");
        for (String menuName: defaultMenus) {
            if (!menus.containsKey(menuName + ".yml")) loadMenu(menuName);
        }
    }

    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    public void loadPlayers() {
        for (Player player: getServer().getOnlinePlayers()) {
            HipogriffPlayer hipogriffPlayer = new HipogriffPlayer(player);
        }
    }
}
