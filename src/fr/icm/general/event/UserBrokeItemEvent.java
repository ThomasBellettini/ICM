package fr.icm.general.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class UserBrokeItemEvent implements Listener {

    @EventHandler
    public void userBrokeItem(PlayerItemDamageEvent e)
    {
        e.setCancelled(true);
    }

}
