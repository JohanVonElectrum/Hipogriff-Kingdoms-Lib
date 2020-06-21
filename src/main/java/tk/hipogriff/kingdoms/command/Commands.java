package tk.hipogriff.kingdoms.command;

import tk.hipogriff.kingdoms.HipogriffKingdoms;

public class Commands {

    private HipogriffKingdoms plugin;

    private Command hipogriffKingdoms = new commandHipogriffKingdoms("hipogriffkingdoms");

    public Commands(HipogriffKingdoms plugin) {
        this.plugin = plugin;

        register();
    }

    private void register() {
        hipogriffKingdoms.Register(plugin);
    }

}
