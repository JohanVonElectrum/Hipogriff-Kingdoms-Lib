package tk.hipogriff.kingdoms.lang;

import org.bukkit.ChatColor;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.utils.Logger;

import java.util.List;

public class Text {

    public static String getLocated(String input, Lang lang) {
        if (input == null || input.equals("")) {
            HipogriffKingdoms.getInstance().getLogger().severe(ChatColor.RED + input + " can NOT be located because is null or empty!");
            return "";
        }

        String located = lang.getLocated(input);

        if (located == null) Logger.severe(input + " can NOT be located!");
        if (located == "") Logger.warning(input + " location is empty!", false);

        return located;
    }

    public static String getFormatted(String input) {
        if (input == null || input.equals("")) {
            Logger.severe(input + " can NOT be formatted because is null or empty!");
            return "";
        }

        String output = ChatColor.RESET + "";
        boolean color = false;
        for (char c: input.toCharArray()) {
            if (c != '&' && !color) output += c;
            if (color) output += ChatColor.getByChar(c);
            color = c == '&';
        }
        return output + ChatColor.RESET;
    }

    public static String getLocatedFormatted(String input, Lang lang) {
        return getFormatted(getLocated(input, lang));
    }

    public static void parseLore(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, getFormatted(lore.get(i)));
        }
    }

}
