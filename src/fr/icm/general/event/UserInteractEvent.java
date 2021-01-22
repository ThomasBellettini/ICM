package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.minecraft.gui.ICMGuiKit;
import fr.icm.kit.minecraft.gui.enumeration.ICMGuiType;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class UserInteractEvent implements Listener {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    ZoneLoader zoneLoader = PvPBox.getZoneLoader;
    ICMGuiKit icmGuiKit = PvPBox.getGuiKit;

    @EventHandler
    public void userInteractEvent(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        ICMPlayer icmPlayer = playerLoader.getICMByPlayer(p);
        if(icmPlayer == null)
            return;
        ArrayList<ICMZone> icmZones = zoneLoader.getIcmZones();
        if(!icmZones.isEmpty())
        {
            for(ICMZone zone : icmZones)
            {
                if(zone.isInsideZone(p))
                {
                    if(zone.isCanDestroy() && e.getAction() == Action.LEFT_CLICK_BLOCK)
                        continue;
                    else if(zone.isCanBuild() && e.getAction() == Action.RIGHT_CLICK_BLOCK)
                        continue;
                    else if(zone.isCanInteract() || p.isOp())
                        continue;
                    e.setCancelled(true);
                    if(p.getItemInHand() != null)
                        p.sendMessage(PvPBox.getConfig.zone_can_use);
                    return;
                }
            }
        }

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
            if(e.getItem() != null && icmPlayer.getFightingKit() != null)
                icmPlayer.getFightingKit().whenRightClick(e);
        else if(e.getAction() == Action.LEFT_CLICK_BLOCK)
            if(e.getItem() != null && icmPlayer.getFightingKit() != null)
                icmPlayer.getFightingKit().whenLeftClickOnBlock(e);
    }

    @EventHandler
    public void userInteractAtEntity(PlayerInteractAtEntityEvent e)
    {
        if(e.getRightClicked() instanceof Player)
        {
            Player p = e.getPlayer();
            Player victim = (Player) e.getRightClicked();
            ICMPlayer icmPlayer = playerLoader.getICMByPlayer(p);
            if(icmPlayer == null)
                return;
            ICMPlayer icmVictim = playerLoader.getICMByPlayer(victim);
            if(icmVictim == null)
                return;
            ArrayList<ICMZone> icmZones = zoneLoader.getIcmZones();
            for(ICMZone zone : icmZones)
            {
                if(zone.isInsideZone(p) || zone.isInsideZone(victim))
                {
                    if(zone.isCanInteractAtEntity() || p.isOp())
                        continue;
                    if(p.getItemInHand() != null)
                        p.sendMessage(PvPBox.getConfig.zone_can_use);
                    e.setCancelled(true);
                    return;
                }
            }
            if(icmPlayer.getFightingKit() != null)
                icmPlayer.getFightingKit().whenRightClickOnPlayer(e);
        }

    }

   


}
