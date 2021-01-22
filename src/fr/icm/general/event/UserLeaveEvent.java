package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.general.utils.ScoreboardSign;
import fr.icm.kit.minecraft.utils.network.PacketReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserLeaveEvent implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e)
    {

        if(PvPBox.getInstance.packetReaderMap.containsKey(e.getPlayer()))
        {
            PacketReader packetReader = PvPBox.getInstance.packetReaderMap.get(e.getPlayer());
            packetReader.uninject();
        }

        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(e.getPlayer());
        if(icmPlayer == null)
        {
            icmPlayer = new ICMPlayer(e.getPlayer().getName(), 0, 0, 0, e.getPlayer().getAddress().getHostString());
            PvPBox.getPlayerLoader.register(icmPlayer);
        }
        if(PvPBox.getPlayerLoader.getSignMap().containsKey(icmPlayer))
        {
            PvPBox.getPlayerLoader.getSignMap().get(icmPlayer).destroy();
            PvPBox.getPlayerLoader.getSignMap().remove(icmPlayer);
        }

        for(Player pall : Bukkit.getOnlinePlayers())
        {
            pall.setScoreboard(PvPBox.getTeamManager.getScoreboard());

            ICMPlayer ip = PvPBox.getPlayerLoader.getICMByPlayer(pall);
            if(ip == null)
            {
                ip = new ICMPlayer(pall.getName(), 0, 0, 0, pall.getAddress().getHostString());
                PvPBox.getPlayerLoader.register(ip);
            }
            ScoreboardSign sb = PvPBox.getPlayerLoader.getSignMap().get(ip);
            if(sb == null)
                continue;
            sb.setLine(9, "§eplay.icm-network.net §7(§a" + (Bukkit.getOnlinePlayers().size() - 1) + "§7)");
            PvPBox.nmsTitle.sendTabTitle(pall,
                    "§d \n §6 ICM Network \n \n §7Vous êtes connecté sur le serveur §aKitPVP §7(§a" + (Bukkit.getOnlinePlayers().size() - 1) + "§7) \n ",
                    "\n §eKill(s) : §6" + ip.getKill() + "§e | Mort(s) : §6" + ip.getDeath() + "§e | §eCoin(s) : §6" + ip.getCoins() + " €");
        }

        e.setQuitMessage("§7[§c-§7] " + e.getPlayer().getDisplayName());

    }


}
