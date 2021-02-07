package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.zone.api.ICMZone;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;

public class UserTeleportEvent implements Listener {

    @EventHandler
    public void userTeleportEvent(PlayerTeleportEvent e)
    {
        Player p = e.getPlayer();
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            ArrayList<ICMZone> icmZones = PvPBox.getZoneLoader.getIcmZones();
            if (!icmZones.isEmpty())
                for (ICMZone zone : icmZones)
                    if (zone.isInsideZone(e.getTo())) {
                        if (zone.isCanTeleport())
                            continue;
                        e.setCancelled(true);
                        p.sendMessage(PvPBox.getConfig.zone_can_use);
                        return;
                    }
            e.setCancelled(true);
            p.teleport(e.getTo());
        }
    }
}
