package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class UserPlaceOrBreakBlockEvent implements Listener {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    ZoneLoader zoneLoader = PvPBox.getZoneLoader;

    @EventHandler
    public void userBreakBlock(BlockBreakEvent e)
    {
        Player p = e.getPlayer();
        ICMPlayer icmPlayer = playerLoader.getICMByPlayer(p);
        if (icmPlayer == null)
            return;
        ArrayList<ICMZone> icmZones = zoneLoader.getIcmZones();
        if (!icmZones.isEmpty())
            for (ICMZone zone : icmZones)
                if (zone.isInsideZone(e.getBlock().getLocation())) {
                    if (zone.isCanDestroy() || p.isOp())
                        continue;
                    else if (icmPlayer.getFightingKit() != null && zone.isCanUsePower())
                        continue;
                    p.sendMessage(PvPBox.getConfig.zone_can_use);
                    e.setCancelled(true);
                    return;
                }
        if (icmPlayer.getFightingKit() != null)
            icmPlayer.getFightingKit().whenBreakBlock(e);
    }

    @EventHandler
    public void userPlaceBlock(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();
        ICMPlayer icmPlayer = playerLoader.getICMByPlayer(p);
        if (icmPlayer == null)
            return;
        ArrayList<ICMZone> icmZones = zoneLoader.getIcmZones();
        if (!icmZones.isEmpty())
            for (ICMZone zone : icmZones)
                if (zone.isInsideZone(e.getBlock().getLocation())) {
                    if (zone.isCanBuild() || p.isOp())
                        continue;
                    else if (icmPlayer.getFightingKit() != null && zone.isCanUsePower())
                        continue;
                    p.sendMessage(PvPBox.getConfig.zone_can_use);
                    e.setCancelled(true);
                    return;
                }
        if (icmPlayer.getFightingKit() != null) {
            if (e.getBlock().getType() == Material.TNT)
                icmPlayer.getFightingKit().whenPlacingTNT(e);
            icmPlayer.getFightingKit().whenPlaceBlock(e);
        }
    }
}
