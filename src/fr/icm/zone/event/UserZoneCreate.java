package fr.icm.zone.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.zone.api.ICMZone;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Locale;

public class UserZoneCreate implements Listener {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    @EventHandler
    public void userClickEvent(PlayerInteractEvent e)
    {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Player p = e.getPlayer();
            ICMPlayer icmPlayer = playerLoader.getICMByPlayer(p);
            if(icmPlayer == null) return;
            if(icmPlayer.getZone_create() == null) return;
            if(icmPlayer.getLoc_zone() != null)
            {
                Location location1 = icmPlayer.getLoc_zone();
                Location location2 = e.getClickedBlock().getLocation();

                ICMZone zone = icmPlayer.getZone_create();
                zone.setNewLocation(location1, location2);
                zone.register();
                p.sendMessage("§7[§6ICM§7] §aVous venez de crée la zone §e" + zone.getName() + " §aavec succès !");
                icmPlayer.setLoc_zone(null);
                icmPlayer.setZone_create(null);
            }
            else
            {
                icmPlayer.setLoc_zone(e.getClickedBlock().getLocation());
                p.sendMessage("§7[§6ICM§7] §aNouvelle Location défini pour la zone §e" + icmPlayer.getZone_create().getName() + "§a, merci de cliquer sur un autre blocs !");
            }
        }
    }


}
