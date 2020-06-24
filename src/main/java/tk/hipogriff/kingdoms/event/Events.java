package tk.hipogriff.kingdoms.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.menu.InventoryMenu;
import tk.hipogriff.kingdoms.menu.MenuIcon;
import tk.hipogriff.kingdoms.menu.action.MenuAction;
import tk.hipogriff.kingdoms.player.HipogriffPlayer;

public class Events implements Listener {

    public Inventory getInventory(InventoryEvent event) {
        return event.getInventory();
    }

    public InventoryView getView(InventoryEvent event) {
        return event.getView();
    }

    public String getTitle(InventoryEvent event) {
        return getView(event).getTitle();
    }

    public InventoryMenu getMenu(InventoryEvent event) {
        return HipogriffKingdoms.getInstance().getMenuByTitle(getTitle(event));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        HipogriffPlayer hipogriffPlayer = new HipogriffPlayer(event.getPlayer());
        //TODO: LOAD HK CHARACTER
    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        HipogriffPlayer.unregister(event.getPlayer());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryMenu menu = getMenu(event);

        if (HipogriffPlayer.getPlayer(event.getPlayer()).getMenu() != null && HipogriffPlayer.getPlayer(event.getPlayer()).getMenu().getState() == InventoryMenu.MenuState.REDIRECT) {
            HipogriffPlayer.getPlayer(event.getPlayer()).getMenu().setState(InventoryMenu.MenuState.OPEN);
            return;
        }

        menu.setState(InventoryMenu.MenuState.OPEN);
        HipogriffPlayer.getPlayer(event.getPlayer()).setMenu(menu);

        Player player = (Player) event.getPlayer();

        for (MenuAction action: menu.getConfig().getActions(MenuAction.ActionEvent.OPEN)) {
            action.run(player);
        }
    }

    @EventHandler
    public void onInventoryClickItem(InventoryClickEvent event) {
        InventoryMenu menu = getMenu(event);

        Player player = (Player) event.getWhoClicked();

        //ClickType click = event.getClick();
        //Inventory inv = event.getClickedInventory();

        MenuIcon icon = menu.getConfig().getIcon(event.getSlot());
        if (icon != null) {
            for (MenuAction action: icon.getActions(MenuAction.ActionEvent.CLICK)) {
                action.run(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryMenu menu = HipogriffPlayer.getPlayer(event.getPlayer()).getMenu();

        if (event.getReason() == InventoryCloseEvent.Reason.OPEN_NEW) {
            menu.setState(InventoryMenu.MenuState.REDIRECT);
            return;
        } else {
            menu.setState(InventoryMenu.MenuState.CLOSE);
        }

        Player player = (Player) event.getPlayer();

        for (MenuAction action: menu.getConfig().getActions(MenuAction.ActionEvent.CLOSE)) {
            action.run(player);
        }

        HipogriffPlayer.getPlayer(event.getPlayer()).setMenu(null);
    }

}
