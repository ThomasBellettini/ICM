package fr.icm.kit.minecraft.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class EventDurability implements Listener {

    @EventHandler
    public void durability(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }
}
