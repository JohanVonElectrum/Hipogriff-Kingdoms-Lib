package tk.hipogriff.kingdoms.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.hipogriff.kingdoms.exception.IconOutInventoryException;
import tk.hipogriff.kingdoms.lang.Text;
import tk.hipogriff.kingdoms.menu.action.MenuAction;
import tk.hipogriff.kingdoms.utils.EnumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MenuIcon {

    private InventoryMenu menu;
    private Material material;
    private ItemMeta meta;
    private int x, y, count;
    private HashMap<MenuAction.ActionEvent, ArrayList<MenuAction>> actions;
    private String path;

    public MenuIcon(InventoryMenu menu, String iconName) throws IconOutInventoryException {
        menu.getPlugin().getLogger().info(ChatColor.AQUA + iconName + " menu icon is loading...");
        if (x < 0 || x >= 9 || y < 0 || y >= menu.getConfig().getRows()) throw new IconOutInventoryException("X: " + x + " Y: " + y + " is NOT a valid position.");

        this.menu = menu;
        this.path = "icons." + iconName;
        String displayName = Text.getLocatedFormatted(menu.getConfig().getString(path + ".title") , menu.getPlugin().getLang());
        String lorePointer = menu.getConfig().getString(path + ".description");
        List<String> lore = menu.getPlugin().getLang().getStringList(lorePointer);
        setMaterial(menu.getConfig().getString(path + ".item"));
        setX(menu.getConfig().getInt(path + ".x") - 1);
        setY(menu.getConfig().getInt(path + ".y") - 1);
        setCount(menu.getConfig().getInt(path + ".count"));
        Boolean unbreakable = menu.getConfig().getBoolean(path + ".unbreakable");
        int version = menu.getConfig().getInt(path + ".version");

        Text.parseLore(lore);

        meta = getItemStack(count).getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);

        List<String> flags = menu.getConfig().getStringList(path + ".flags");
        if (flags != null && !flags.isEmpty()) {
            for (String flagID: flags) {
                ItemFlag flag = (ItemFlag) EnumUtils.stringToEnum(ItemFlag.class, flagID);
                meta.addItemFlags(flag);
            }
        }

        ConfigurationSection enchSect = menu.getConfig().getConfigurationSection(path + ".enchants");
        if (enchSect != null && enchSect.getKeys(false) != null && !enchSect.getKeys(false).isEmpty()) {
            for (String enchKey: enchSect.getKeys(false)) {
                Enchantment enchant = EnumUtils.getEnchantment(enchKey);
                int level = enchSect.getInt(enchKey);
                meta.addEnchant(enchant, level, true);
            }
        }

        loadActions(MenuAction.ActionEvent.CLICK, "click-actions");
    }

    public ItemStack getItemStack(int count) {
        ItemStack itemStack = new ItemStack(material, count > 0 ? count : 1);
        itemStack.setItemMeta(meta);
        return itemStack;
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

    public ItemMeta getMeta() {
        return meta;
    }

    public void setMeta(ItemMeta meta) {
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

    public int getPos() {
        return x + y * 9;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<MenuAction> getActions(MenuAction.ActionEvent event) {
        return actions.get(event);
    }

    private void loadAction(MenuAction.ActionEvent event, MenuAction action) {
        if (actions.get(event) == null) actions.put(event, new ArrayList<>());
        if (action != null) actions.get(event).add(action);
    }

    private void loadActions(MenuAction.ActionEvent event, String configSection, String... args) {
        if (actions == null) actions = new HashMap<>();
        ConfigurationSection actions = menu.getConfig().getConfigurationSection(path + "." + configSection);
        if (actions != null && actions.getKeys(false) != null && !actions.getKeys(false).isEmpty()) {
            for (String key: actions.getKeys(false)) {
                String value = actions.getString(key);

                MenuAction.ActionType type = (MenuAction.ActionType) EnumUtils.stringToEnum(MenuAction.ActionType.class, key);

                loadAction(event, MenuAction.createAction(type, value, args));
            }
        }
    }
}
