package tk.hipogriff.kingdoms.utils;

import org.bukkit.enchantments.Enchantment;

public class EnumUtils {
    public static <T extends Enum<T>> Enum<T> stringToEnum(Class<T> e, String key) {
        return Enum.valueOf(e, key.toUpperCase().replaceAll("\\.", "_"));
    }

    public static Enchantment getEnchantment(String key) {
        try {
            return (Enchantment) Enchantment.class.getField(key).get(Enchantment.ARROW_DAMAGE);
        } catch (IllegalArgumentException e) {
            // if the specified object is not an instance of the class or
            // interface declaring the underlying field (or a subclass or
            // implementor thereof)
            e.printStackTrace();
        } catch (SecurityException e) {
            // if a security manager, s, is present [and restricts the access to
            // the field]
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // if the underlying field is inaccessible
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // if a field with the specified name is not found
            e.printStackTrace();
        }

        return null;
    }
}
