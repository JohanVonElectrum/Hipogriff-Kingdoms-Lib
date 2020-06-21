package tk.hipogriff.kingdoms.menu.action;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import tk.hipogriff.kingdoms.utils.EnumUtils;

public class SoundAction extends MenuAction {

    private String sound;

    public SoundAction(String sound) {
        this.sound = sound;
    }

    @Override
    public void run(Player player) {
        player.playSound(player.getLocation(), (Sound) EnumUtils.stringToEnum(Sound.class, sound), 10, 1);
    }
}
