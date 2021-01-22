package fr.icm.zone.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class UserCancelEvent {

    private static PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    public static boolean UserCancelEvent(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        ICMPlayer icmPlayer = playerLoader.getICMByPlayer(p);
        if(icmPlayer == null) return false;
        if(e.getMessage().equalsIgnoreCase("cancel"))
        {
            if(icmPlayer.getZone_create() != null)
            {
                e.setCancelled(true);
                icmPlayer.setZone_create(null);
                if(icmPlayer.getLoc_zone() !=null) icmPlayer.setLoc_zone(null);
                p.sendMessage("§7[§6ICM§7] §cVous venez de cancel la création de la zone !");
                return true;
            }
        }
        return false;
    }

}
