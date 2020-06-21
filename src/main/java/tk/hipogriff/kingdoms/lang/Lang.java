package tk.hipogriff.kingdoms.lang;

import org.bukkit.plugin.Plugin;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.config.AbstractFile;

public class Lang extends AbstractFile {
    private String code;

    public Lang(String code) {
        super("lang/" + code + ".yml");
        this.code = code;
    }

    public Lang() {
        this(HipogriffKingdoms.getInstance().getConfig().getString("default.lang"));
    }

    public String getLocated(String location) {
        return config.getString(location);
    }

    public String getCode() {
        return code;
    }
}
