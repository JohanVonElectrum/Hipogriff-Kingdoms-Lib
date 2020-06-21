package tk.hipogriff.kingdoms.lang;

import org.bukkit.ChatColor;
import tk.hipogriff.kingdoms.HipogriffKingdoms;

public class Text {

    public static String getLocated(String input, Lang lang) {
        if (input == null || input.equals("")) {
            HipogriffKingdoms.getInstance().getLogger().severe(ChatColor.RED + input + " can NOT be located because is null or empty!");
            return "";
        }

        String located = lang.getLocated(input);

        if (located == null) HipogriffKingdoms.getInstance().getLogger().severe(ChatColor.RED + input + " can NOT be located!");
        if (located == "") HipogriffKingdoms.getInstance().getLogger().warning(ChatColor.GOLD + input + " location is empty!");

        return located;
    }

    public static String getFormatted(String input) {
        if (input == null || input.equals("")) {
            HipogriffKingdoms.getInstance().getLogger().severe(ChatColor.RED + input + " can NOT be formatted because is null or empty!");
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

}
