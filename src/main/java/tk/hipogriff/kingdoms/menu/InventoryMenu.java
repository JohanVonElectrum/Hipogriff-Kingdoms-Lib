package tk.hipogriff.kingdoms.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import tk.hipogriff.kingdoms.HipogriffKingdoms;

import java.util.HashMap;
import java.util.List;

public class InventoryMenu implements Listener {

    public enum MenuState {
        OPEN,
        CLOSE,
        REDIRECT
    }

    private HipogriffKingdoms plugin;
    private MenuConfig config;
    private Inventory inv;
    private MenuState state;

    public HipogriffKingdoms getPlugin() {
        return plugin;
    }

    public void setPlugin(HipogriffKingdoms plugin) {
        this.plugin = plugin;
    }

    public MenuConfig getConfig() {
        return config;
    }

    public void setConfig(MenuConfig config) {
        this.config = config;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public MenuState getState() {
        return state;
    }

    public void setState(MenuState state) {
        this.state = state;
    }

    public InventoryMenu(MenuConfig config) {
        this.plugin = HipogriffKingdoms.getInstance();
        this.config = config;

        load();
    }

    public boolean load() {
        inv = plugin.getServer().createInventory(null, 9 * config.getRows(), config.getTitle());
        config.loadIcons(this);
        List<MenuIcon> icons = config.getIcons();
        if (icons == null) return false;

        for (MenuIcon icon: icons) {
            inv.setItem(icon.getX() + icon.getY() * 9, icon.getItemStack(icon.getCount()));
        }

        return true;
    }

    public void display(Player player) {
        player.openInventory(inv);
    }

}
