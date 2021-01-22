package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class UserTakeDamage implements Listener {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    ZoneLoader zoneLoader = PvPBox.getZoneLoader;

    @EventHandler
    public void userTakeDamageEvent(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            Player victim = (Player) e.getEntity();
            ICMPlayer icmPlayer = playerLoader.getICMByPlayer(victim);
            if(icmPlayer == null)
                return;
            ArrayList<ICMZone> zones = zoneLoader.getIcmZones();
            if(!zones.isEmpty())
            {
                for(ICMZone zone : zones)
                {
                    if(zone.isInsideZone(victim))
                    {
                        if(zone.isInvicible())
                        {
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }



        }
    }



}
