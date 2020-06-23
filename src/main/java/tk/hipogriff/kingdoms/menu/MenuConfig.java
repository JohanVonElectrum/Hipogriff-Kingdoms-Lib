package tk.hipogriff.kingdoms.menu;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import tk.hipogriff.kingdoms.config.AbstractFile;
import tk.hipogriff.kingdoms.exception.IconOutInventoryException;
import tk.hipogriff.kingdoms.lang.Text;
import tk.hipogriff.kingdoms.menu.action.MenuAction;
import tk.hipogriff.kingdoms.utils.EnumUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuConfig extends AbstractFile {

    private HashMap<MenuAction.ActionEvent, ArrayList<MenuAction>> actions;
    private HashMap<Integer, MenuIcon> icons = new HashMap<>();

    public MenuConfig(String name) {
        super("menu/" + name + ".yml");
    }

    public MenuConfig(File file) {
        super(file);
    }

    public List<MenuIcon> getIcons() {
        return new ArrayList<MenuIcon>(icons.values());
    }

    public MenuIcon getIcon(int i) {
        return icons.get(i);
    }

    public boolean load() {
        super.load();

        this.actions = new HashMap<>();

        for (MenuAction.ActionEvent event: MenuAction.ActionEvent.values()) {
            this.actions.put(event, new ArrayList<>());
        }

        loadActions(MenuAction.ActionEvent.OPEN, "open-actions");
        loadActions(MenuAction.ActionEvent.CLOSE, "close-actions");

        return loaded;
    }

    private void loadAction(MenuAction.ActionEvent event, MenuAction action) {
        if (actions.get(event) == null) actions.put(event, new ArrayList<>());
        if (action != null) actions.get(event).add(action);
    }

    private void loadActions(MenuAction.ActionEvent event, String configSection, String... args) {
        if (actions == null) actions = new HashMap<>();
        ConfigurationSection actions = getConfigurationSection(configSection);
        for (String key: actions.getKeys(false)) {
            String value = actions.getString(key);

            MenuAction.ActionType type = (MenuAction.ActionType) EnumUtils.stringToEnum(MenuAction.ActionType.class, key);

            loadAction(event, MenuAction.createAction(type, value, args));
        }
    }

    public int getRows() {
        return config.getInt("rows");
    }

    public String getTitle() {
        return Text.getLocatedFormatted(config.getString("title"), plugin.getLang());
    }

    public boolean loadIcons(InventoryMenu menu) {

        if (config == null) {
            plugin.getLogger().severe(ChatColor.RED + getTitle() + " menu config can NOT be loaded!");
            return  false;
        } else if (config.getConfigurationSection("icons") == null) {
            plugin.getLogger().severe(ChatColor.RED + getTitle() + " menu icons can NOT be loaded!");
            return false;
        }

        for (String iconName: config.getConfigurationSection("icons").getKeys(false)) {
            try {
                MenuIcon icon = new MenuIcon(menu, iconName);
                icons.put(icon.getPos(), icon);
            } catch (IconOutInventoryException e) {
                plugin.getLogger().severe(ChatColor.RED + iconName + "in" + getTitle() + " menu can NOT be loaded because: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return true;
    }

    public ArrayList<MenuAction> getActions(MenuAction.ActionEvent event) {
        return actions.get(event);
    }
}
