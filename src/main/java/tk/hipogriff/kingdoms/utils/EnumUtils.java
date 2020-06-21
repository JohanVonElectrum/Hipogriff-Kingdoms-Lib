package tk.hipogriff.kingdoms.utils;

public class EnumUtils {
    public static <T extends Enum<T>> Enum<T> stringToEnum(Class<T> e, String key) {
        return Enum.valueOf(e, key.toUpperCase().replaceAll("\\.", "_"));
    }
}
