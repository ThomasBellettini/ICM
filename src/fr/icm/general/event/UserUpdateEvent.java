package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.ArrayList;

public class UserUpdateEvent implements Listener {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    ZoneLoader zoneLoader = PvPBox.getZoneLoader;

    @EventHandler
    public void userUpdateEvent(FoodLevelChangeEvent e)
    {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            ICMPlayer icmPlayer = playerLoader.getICMByPlayer(player);
            if (icmPlayer == null)
                    return;
            ArrayList<ICMZone> zones = zoneLoader.getIcmZones();
            if (!zones.isEmpty())
                for (ICMZone zone : zones)
                    if (zone.isInsideZone(player))
                        if (zone.isFoodLess()) {
                            if (player.getFoodLevel() != 20)
                                player.setFoodLevel(20);
                            e.setCancelled(true);
                            return;
                        }
        }
    }
}
