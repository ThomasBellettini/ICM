package fr.icm.kit.module.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.module.utils.KitLoader;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class EventMovement implements Listener {

    private KitLoader kitLoader = PvPBox.getKitLoader;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    @EventHandler
    public void whenPlayerSneaking(PlayerToggleSneakEvent e)
    {
        Player damager = e.getPlayer();
        ICMPlayer icmPlayerDamager = playerLoader.getICMByPlayer(damager);

        if(icmPlayerDamager == null)
            return;

        if(!icmPlayerDamager.isFighting())
            return;
        icmPlayerDamager.getFightingKit().whenSneaking(e);
    }

    @EventHandler
    public void whenWalkingOnSponge(PlayerMoveEvent e)
    {
        Player damager = e.getPlayer();
        ICMPlayer icmPlayerDamager = playerLoader.getICMByPlayer(damager);

        if(icmPlayerDamager == null)
            return;

        if(!icmPlayerDamager.isFighting())
            return;
        if(new Location(e.getTo().getWorld(), e.getTo().getX(), e.getTo().getY() -1, e.getTo().getZ()).getBlock().getType() == Material.SPONGE)
            icmPlayerDamager.getFightingKit().whenWalkingOnSponge(e);
    }

}
