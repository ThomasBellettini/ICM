package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class UserPvPEvent implements Listener {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    ZoneLoader zoneLoader = PvPBox.getZoneLoader;

    @EventHandler
    public void userFightOtherPlayer(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player) {
            if (!(e.getDamager() instanceof Player)) {
                if (!(e.getDamager() instanceof Projectile))
                    return;
                else if (!(((Projectile) e.getDamager()).getShooter() instanceof Player))
                    return;
            }

            Player attacker = null;
            Player victim = (Player)e.getEntity();
            if (e.getDamager() instanceof Player)
                attacker = (Player) e.getDamager();
            else if (e.getDamager() instanceof Projectile)
                attacker = (Player) ((Projectile) e.getDamager()).getShooter();
            if (attacker == null)
                return;

            ICMPlayer icmAttacker = playerLoader.getICMByPlayer(attacker);
            ICMPlayer icmVictim = playerLoader.getICMByPlayer(victim);
            if (icmAttacker == null)
                return;
            if (icmVictim == null)
                return;

            ArrayList<ICMZone> zones = zoneLoader.getIcmZones();
            if (!zones.isEmpty()) {
                for (ICMZone zone : zones) {
                    if (zone.isInsideZone(attacker)) {
                        if (zone.isCanPvp() && !zone.isInvicible())
                            continue;
                        attacker.sendMessage(PvPBox.getConfig.zone_can_use);
                        e.setCancelled(true);
                        return;
                    } else if (zone.isInsideZone(victim)) {
                        if (zone.isCanPvp() && !zone.isInvicible())
                            continue;
                        attacker.sendMessage(PvPBox.getConfig.zone_can_use);
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            if (icmAttacker.getFightingKit() != null) {
                icmAttacker.getFightingKit().whenAttacking(e);
                if (e.getDamager() instanceof Snowball)
                    icmAttacker.getFightingKit().whenHittingPlayerWithSnowBall(e);
                else if (e.getDamager() instanceof Arrow)
                    icmAttacker.getFightingKit().whenArrowHit(e);
            }
            if (icmVictim.getFightingKit() != null)
                icmVictim.getFightingKit().whenGetHit(e);
            icmVictim.setCombatTag();
            icmAttacker.setCombatTag();
        } else {
            if (!(e.getDamager() instanceof Player)) {
                if(!(e.getDamager() instanceof Projectile))
                    return;
                else if (!(((Projectile) e.getDamager()).getShooter() instanceof Player))
                    return;
            }
            Player attacker = null;
            if (e.getDamager() instanceof Player)
                attacker = (Player) e.getDamager();
            else if (e.getDamager() instanceof Projectile)
                attacker = (Player) ((Projectile) e.getDamager()).getShooter();
            if (attacker == null)
                return;

            ICMPlayer icmAttacker = playerLoader.getICMByPlayer(attacker);
            if (icmAttacker == null)
                return;
            ArrayList<ICMZone> zones = zoneLoader.getIcmZones();
            if (!zones.isEmpty())
                for (ICMZone zone : zones)
                    if (zone.isInsideZone(attacker)) {
                        if (zone.isCanEntityDamage())
                            continue;
                        attacker.sendMessage(PvPBox.getConfig.zone_can_use);
                        e.setCancelled(true);
                        return;
                    } else if(zone.isInsideZone(e.getEntity())) {
                        if(zone.isCanEntityDamage())
                            continue;
                        attacker.sendMessage(PvPBox.getConfig.zone_can_use);
                        e.setCancelled(true);
                        return;
                    }
        }
    }
}
