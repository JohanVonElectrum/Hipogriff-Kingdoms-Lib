package tk.hipogriff.kingdoms.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.hipogriff.kingdoms.HipogriffKingdoms;
import tk.hipogriff.kingdoms.utils.Logger;

import java.io.File;
import java.io.IOException;

public class AbstractFile extends YamlConfiguration {

    protected File file;
    protected String name;
    protected HipogriffKingdoms plugin;
    protected FileConfiguration config;
    protected boolean loaded;

    public AbstractFile(File file) {
        super();
        this.file = file;
        this.plugin = HipogriffKingdoms.getInstance();
        this.loaded = false;
    }

    public AbstractFile(String name) {
        this(new File(HipogriffKingdoms.getInstance().getDataFolder(), name));
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public boolean load() {
        this.loaded = false;

        try {
            if (!file.isFile()) {
                if (plugin.getResource(name) != null) {
                    plugin.saveResource(name, false);
                } else {
                    if (file.getParentFile() != null) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
            }
        } catch (IOException e) {
            Logger.file(file, Logger.FileType.UNKNOWN, Logger.TaskState.ERROR, false);
            e.printStackTrace();
            return false;
        }

        for (String section : this.getKeys(false)) {
            set(section, null);
        }
        config = loadConfiguration(file);
        try {
            load(file);
        } catch (IOException e) {
            Logger.file(file, Logger.FileType.UNKNOWN, Logger.TaskState.ERROR, false);
            e.printStackTrace();
            return false;
        } catch (InvalidConfigurationException e) {
            Logger.file(file, Logger.FileType.UNKNOWN, Logger.TaskState.ERROR, false);
            e.printStackTrace();
            return false;
        }

        this.loaded = true;
        return true;
    }

    public boolean save() {
        try {
            config.save(file);
        } catch (IOException e) {
            Logger.file(file, Logger.FileType.UNKNOWN, Logger.TaskState.ERROR, false);
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
