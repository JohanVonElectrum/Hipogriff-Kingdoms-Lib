package tk.hipogriff.kingdoms.menu.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import tk.hipogriff.kingdoms.utils.EnumUtils;

public class CloseMenuAction extends MenuAction {

    InventoryCloseEvent.Reason reason;

    public CloseMenuAction(String reason) {
        this.reason = (InventoryCloseEvent.Reason) EnumUtils.stringToEnum(InventoryCloseEvent.Reason.class, reason);
    }

    @Override
    public void run(Player player) {
        player.closeInventory(reason);
    }
}
