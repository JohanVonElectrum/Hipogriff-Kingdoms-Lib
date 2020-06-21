package tk.hipogriff.kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import tk.hipogriff.kingdoms.command.Commands;
import tk.hipogriff.kingdoms.config.PluginConfig;
import tk.hipogriff.kingdoms.lang.Lang;
import tk.hipogriff.kingdoms.menu.InventoryMenu;
import tk.hipogriff.kingdoms.menu.MenuConfig;

import java.io.File;
import java.util.HashMap;

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

    @Override
    public void onEnable() {
        loadConfig();
        loadLang();
        loadMenus();

        commands = new Commands(this);

        getLogger().info(ChatColor.GREEN + "\n\n" + this.getName() + " has been ENABLED\n\n");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "\n\n" + this.getName() + " has been DISABLED\n\n");
    }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            getLogger().info(ChatColor.AQUA + "\n\n" + getDataFolder() + " has been CREATED\n\n");
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        config = new PluginConfig();
        if (config.load()) {
            getLogger().info(ChatColor.GREEN + "config loaded");
        }
    }

    public void loadLang() {
        lang = new Lang();
        if (lang.load()) {
            getLogger().info(ChatColor.GREEN + "lang loaded with " + lang.getCode());
            getLogger().info(ChatColor.AQUA + "lang selected: " + lang.getLocated("location.lang.set"));
        }
    }

    public void loadMenus() {
        menus = new HashMap<>();
        for (File file: new File(getDataFolder(), "menu").listFiles()) {
            getLogger().info(ChatColor.AQUA + "loading file: " + file.getAbsolutePath());
            MenuConfig menu = new MenuConfig(file);
            if (menu.load()) {
                menus.put(menu.getFile().getName(), new InventoryMenu(menu));
                getLogger().info(ChatColor.AQUA + menu.getFile().getName() + " menu have been loaded!");
            } else {
                getLogger().severe(ChatColor.RED + menu.getFile().getName() + " menu can NOT be loaded!");
            }
        }
    }
}
