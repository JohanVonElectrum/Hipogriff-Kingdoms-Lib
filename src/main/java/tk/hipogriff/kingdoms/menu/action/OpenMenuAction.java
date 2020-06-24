package tk.hipogriff.kingdoms.menu.action;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.menu.InventoryMenu;
import tk.hipogriff.kingdoms.utils.Logger;

public class OpenMenuAction extends MenuAction {

    private String menuName;
    private InventoryMenu menu;
    private CloseMenuAction close;

    public OpenMenuAction(String menuName) {
        this.menuName = menuName + ".yml";
        this.menu = HipogriffKingdoms.getInstance().getMenu(menuName + ".yml");
        this.close = new CloseMenuAction("OPEN_NEW");
    }

    @Override
    public void run(Player player) {
        if (menu != null) {
            close.run(player);
            menu.display(player);
        }
        else Logger.severe("Menu " + menuName + " do NOT exists.");
    }
}
