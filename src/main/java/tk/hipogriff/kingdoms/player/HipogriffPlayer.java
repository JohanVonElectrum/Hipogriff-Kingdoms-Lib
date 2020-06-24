package tk.hipogriff.kingdoms.player;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import tk.hipogriff.kingdoms.menu.InventoryMenu;

import java.util.HashMap;
import java.util.UUID;

public class HipogriffPlayer {

    private static HashMap<UUID, HipogriffPlayer> players = new HashMap<>();

    private String displayName;
    private InventoryMenu menu;

    public HipogriffPlayer(String displayName) {
        this.displayName = displayName;
    }

    public HipogriffPlayer(Player player) {
        this.displayName = player.getDisplayName();
        register(player);
    }

    public static HipogriffPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static HipogriffPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static HipogriffPlayer getPlayer(HumanEntity player) {
        return getPlayer(player.getUniqueId());
    }

    public boolean register(Player player) {
        if (players == null) return false;
        boolean prev = players.containsKey(player.getUniqueId());
        players.put(player.getUniqueId(), this);
        return prev;
    }

    public static boolean unregister(Player player) {
        if (players == null || !players.containsKey(player.getUniqueId())) return false;
        players.remove(player.getUniqueId());
        return true;
    }

    public InventoryMenu getMenu() {
        return menu;
    }

    public void setMenu(InventoryMenu menu) {
        this.menu = menu;
    }
}
