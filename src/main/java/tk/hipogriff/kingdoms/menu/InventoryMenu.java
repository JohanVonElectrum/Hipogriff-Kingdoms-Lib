package tk.hipogriff.kingdoms.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.menu.action.MenuAction;

import java.util.List;

public class InventoryMenu implements Listener {

    private HipogriffKingdoms plugin;
    private MenuConfig config;
    private Inventory inv;

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

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        return true;
    }

    public void display(Player player) {
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!event.getView().getTitle().equals(config.getTitle())) return;

        Player player = (Player) event.getPlayer();

        for (MenuAction action: config.getActions(MenuAction.ActionEvent.OPEN)) {
            action.run(player);
        }
    }

    @EventHandler
    public void onInventoryClickItem(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(config.getTitle())) return;
        Player player = (Player) event.getWhoClicked();

        ClickType click = event.getClick();
        Inventory inv = event.getClickedInventory();

        MenuIcon icon = config.getIcon(event.getSlot());
        if (icon != null) {
            for (MenuAction action: icon.getActions(MenuAction.ActionEvent.CLICK)) {
                action.run(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(config.getTitle()) && event.getReason() == InventoryCloseEvent.Reason.OPEN_NEW) return;

        Player player = (Player) event.getPlayer();

        for (MenuAction action: config.getActions(MenuAction.ActionEvent.CLOSE)) {
            action.run(player);
        }
    }

}
