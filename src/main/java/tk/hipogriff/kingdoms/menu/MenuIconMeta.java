package tk.hipogriff.kingdoms.menu;

import com.destroystokyo.paper.Namespaced;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.*;

public class MenuIconMeta implements ItemMeta {

    private String displayName;
    private String localizedName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchants;
    private Set<ItemFlag> flags;
    private boolean unbreakable;
    private Multimap<Attribute, AttributeModifier> attributes;
    private int version;
    private Set<Material> destroyable;
    private Set<Material> placeable;
    private Collection<Namespaced> destroyableKeys;
    private Collection<Namespaced> placeableKeys;
    private PersistentDataContainer dataContainer;

    public MenuIconMeta(String displayName, String localizedName, List<String> lore, Map<Enchantment, Integer> enchants, Set<ItemFlag> flags, boolean unbreakable, Multimap<Attribute, AttributeModifier> attributes, int version, Set<Material> destroyable, Set<Material> placeable, Collection<Namespaced> destroyableKeys, Collection<Namespaced> placeableKeys, PersistentDataContainer dataContainer) {
        this.displayName = displayName;
        this.localizedName = localizedName;
        this.lore = lore;
        this.enchants = enchants;
        this.flags = flags;
        this.unbreakable = unbreakable;
        this.attributes = attributes;
        this.version = version;
        this.destroyable = destroyable;
        this.placeable = placeable;
        this.destroyableKeys = destroyableKeys;
        this.placeableKeys = placeableKeys;
        this.dataContainer = dataContainer;
    }

    @Override
    public boolean hasDisplayName() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public boolean hasLocalizedName() {
        return true;
    }

    @Override
    public String getLocalizedName() {
        return localizedName;
    }

    @Override
    public void setLocalizedName(String name) {
        this.localizedName = name;
    }

    @Override
    public boolean hasLore() {
        return lore != null && lore.toArray().length > 0;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    @Override
    public boolean hasCustomModelData() {
        return false;
    }

    @Override
    public int getCustomModelData() {
        return 0;
    }

    @Override
    public void setCustomModelData(Integer data) {
        throw new NotImplementedException("Icons have NOT custom model data!");
    }

    @Override
    public boolean hasEnchants() {
        return enchants != null && !enchants.isEmpty();
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        return enchants.get(ench) != null;
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        return enchants.get(ench);
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return enchants;
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        if (level > 0) enchants.put(ench, level);
        return level > 0;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        boolean have = enchants.get(ench) != null;
        if (have) enchants.remove(ench);
        return have;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        for (Enchantment enchant: enchants.keySet()) {
            if (ench.conflictsWith(enchant)) return true;
        }
        return false;
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {
        for (ItemFlag flag: itemFlags) {
            flags.add(flag);
        }
    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {
        for (ItemFlag flag: itemFlags) {
            flags.remove(flag);
        }
    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return flags;
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        return flags.contains(flag);
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable;
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    @Override
    public boolean hasAttributeModifiers() {
        return !attributes.isEmpty();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return attributes;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> output = ArrayListMultimap.create();

        for (Attribute atr: attributes.keySet()) {
            for (AttributeModifier mod: attributes.get(atr)) {
                if (mod.getSlot() == slot) output.put(atr, mod);
            }
        }

        return output;
    }

    @Override
    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute) {
        return attributes.get(attribute);
    }

    @Override
    public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        return attributes.put(attribute, modifier);
    }

    @Override
    public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
        attributes = attributeModifiers;
    }

    @Override
    public boolean removeAttributeModifier(Attribute attribute) {
        boolean have = attributes.get(attribute) != null;
        attributes.removeAll(attribute);
        return have;
    }

    @Override
    public boolean removeAttributeModifier(EquipmentSlot slot) {
        boolean have = false;

        for (Attribute atr: attributes.keySet()) {
            for (AttributeModifier mod: attributes.get(atr)) {
                if (mod.getSlot() == slot) {
                    attributes.remove(atr, mod);
                    have = true;
                }
            }
        }

        return have;
    }

    @Override
    public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        boolean have = attributes.get(attribute) != null && attributes.get(attribute).contains(modifier);
        attributes.remove(attribute, modifier);
        return have;
    }

    @Override
    public CustomItemTagContainer getCustomTagContainer() {
        return null;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public ItemMeta clone() {
        return new MenuIconMeta(displayName, localizedName, lore, enchants, flags, unbreakable, attributes, version, destroyable, placeable, destroyableKeys, placeableKeys, dataContainer);
    }

    @Override
    public Set<Material> getCanDestroy() {
        return destroyable;
    }

    @Override
    public void setCanDestroy(Set<Material> canDestroy) {
        this.destroyable = canDestroy;
    }

    @Override
    public Set<Material> getCanPlaceOn() {
        return placeable;
    }

    @Override
    public void setCanPlaceOn(Set<Material> canPlaceOn) {
        this.placeable = canPlaceOn;
    }

    @Override
    public Set<Namespaced> getDestroyableKeys() {
        return (Set<Namespaced>) destroyableKeys;
    }

    @Override
    public void setDestroyableKeys(Collection<Namespaced> canDestroy) {
        this.destroyableKeys = canDestroy;
    }

    @Override
    public Set<Namespaced> getPlaceableKeys() {
        return (Set<Namespaced>) placeableKeys;
    }

    @Override
    public void setPlaceableKeys(Collection<Namespaced> canPlaceOn) {
        this.placeableKeys = canPlaceOn;
    }

    @Override
    public boolean hasPlaceableKeys() {
        return !placeableKeys.isEmpty();
    }

    @Override
    public boolean hasDestroyableKeys() {
        return !destroyableKeys.isEmpty();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> output = new HashMap<>();

        output.put("displayName", displayName);
        output.put("localizedName", localizedName);
        output.put("lore", lore);
        output.put("enchants", enchants);
        output.put("flags", flags);
        output.put("unbreakable", unbreakable);
        output.put("attributes", attributes);
        output.put("version", version);
        output.put("destroyable", destroyable);
        output.put("placeable", placeable);
        output.put("destroyableKeys", destroyableKeys);
        output.put("placeableKeys", placeableKeys);
        output.put("dataContainer", dataContainer);

        return output;
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return dataContainer;
    }
}
