package fr.icm.kit.module.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.module.utils.KitLoader;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EventEntity implements Listener {

    private KitLoader kitLoader = PvPBox.getKitLoader;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    @EventHandler
    public void onKillingEntity(EntityDeathEvent e)
    {
        if(e.getEntity() == null || e.getEntity().getKiller() == null)
            return;
        Player damager = e.getEntity().getKiller();
        ICMPlayer icmPlayerUser = playerLoader.getICMByPlayer(damager);
        if (icmPlayerUser == null) {
            icmPlayerUser = new ICMPlayer(damager.getName(), 0, 0, 0, "D");
            playerLoader.register(icmPlayerUser);
        }
        if (!icmPlayerUser.isFighting())
            return;
        icmPlayerUser.getFightingKit().whenKillingEntity(e);
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e)
    {
        if(e.getEntity() == null || e.getDismounted() == null)
            return;
        if(!(e.getEntity() instanceof Player))
            return;
        if(!(e.getDismounted() instanceof Horse))
            return;

        Player rider = (Player) e.getEntity();
        ICMPlayer icmPlayerUser = playerLoader.getICMByPlayer(rider);
        if (icmPlayerUser == null) {
            icmPlayerUser = new ICMPlayer(rider.getName(), 0, 0, 0, "D");
            playerLoader.register(icmPlayerUser);
        }
        if (!icmPlayerUser.isFighting()) {
            return;
        }
        icmPlayerUser.getFightingKit().whenDismount(e);
    }

    @EventHandler
    public void onMountGetDamaged(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() == null || !(e.getDamager() instanceof Player))
            return;
        Player damager = (Player) e.getDamager();
        ICMPlayer icmPlayerDamager = playerLoader.getICMByPlayer(damager);
        if (icmPlayerDamager == null) {
            icmPlayerDamager = new ICMPlayer(damager.getName(), 0, 0, 0, "D");
            playerLoader.register(icmPlayerDamager);
        }
        if (!icmPlayerDamager.isFighting())
            return;
        if (e.getEntity().getPassenger() != null && e.getEntity().getPassenger() instanceof Player && e.getEntity() instanceof Horse)
            icmPlayerDamager.getFightingKit().whenHorseHitting(e);
    }

    @EventHandler
    public void whenPlayerFalling(EntityDamageEvent e)
    {
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL || !(e.getEntity() instanceof Player))
            return;
        Player damager = (Player) e.getEntity();
        ICMPlayer icmPlayerDamager = playerLoader.getICMByPlayer(damager);
        if (icmPlayerDamager == null) {
            icmPlayerDamager = new ICMPlayer(damager.getName(), 0, 0, 0, "D");
            playerLoader.register(icmPlayerDamager);
        }
        if (!icmPlayerDamager.isFighting())
            return;
        icmPlayerDamager.getFightingKit().whenFalling(e);
    }

    @EventHandler
    public void whenExplode(EntityExplodeEvent e)
    {
        e.setCancelled(true);
    }

}
