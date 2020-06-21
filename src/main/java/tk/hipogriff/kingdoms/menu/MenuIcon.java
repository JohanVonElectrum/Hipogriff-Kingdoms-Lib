package tk.hipogriff.kingdoms.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.hipogriff.kingdoms.exception.IconOutInventoryException;
import tk.hipogriff.kingdoms.lang.Text;

import java.util.Arrays;

public class MenuIcon {

    private InventoryMenu menu;
    private Material material;
    private MenuIconMeta meta;
    private int x, y, count;

    public MenuIcon(InventoryMenu menu, String iconName) throws IconOutInventoryException {
        if (x < 0 || x >= 9 || y < 0 || y >= menu.getConfig().getRows()) throw new IconOutInventoryException("X: " + x + " Y: " + y + " is NOT a valid position.");

        String displayName = Text.getLocatedFormatted(menu.getConfig().getString("icons." + iconName + ".title") , menu.getPlugin().getLang());
        String localized = "icons." + iconName + ".title";
        String[] lore = (String[]) menu.getConfig().getStringList("icons." + iconName + ".description").toArray();
        setMaterial(menu.getConfig().getString("icons." + iconName + ".item"));
        setX(menu.getConfig().getInt("icons." + iconName + ".x") - 1);
        setY(menu.getConfig().getInt("icons." + iconName + ".y") - 1);
        setCount(menu.getConfig().getInt("icons." + iconName + ".count"));
        Boolean unbreakable = menu.getConfig().getBoolean("icons." + iconName + ".unbreakable");
        int version = menu.getConfig().getInt("icons." + iconName + ".version");

        setMeta(new MenuIconMeta(displayName, localized, Arrays.asList(lore), null, null, unbreakable, null, version, null, null, null, null, null));
    }

    public ItemStack getItemStack(int count) {
        return new ItemStack(material, count);
    }

    public String getDisplayName() {
        return meta.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        this.meta.setDisplayName(displayName);
    }

    public String[] getLore() {
        return getLore();
    }

    public void setLore(String... lore) {
        this.meta.setLore(Arrays.asList(lore));
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setMaterial(String materialID) {
        this.material = Material.getMaterial(materialID.toUpperCase());
    }

    public MenuIconMeta getMeta() {
        return meta;
    }

    public void setMeta(MenuIconMeta meta) {
        this.meta = meta;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
