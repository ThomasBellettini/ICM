package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.general.utils.ScoreboardSign;
import fr.icm.kit.minecraft.utils.network.PacketReader;
import fr.icm.rank.module.utils.RankEnum;
import fr.icm.utils.NMSTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

public class UserJoinEvent implements Listener {

    NMSTitle nmsTitle = new NMSTitle();

    @EventHandler
    public void onUserJoin(PlayerJoinEvent e) {
        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(e.getPlayer());
        if (icmPlayer == null) {
            icmPlayer = new ICMPlayer(e.getPlayer().getName(), 0, 0, 0, e.getPlayer().getAddress().getHostString());
            PvPBox.getPlayerLoader.register(icmPlayer);
        }
        if (e.getPlayer() == null)
            return;
        setupScoreAndRank(e.getPlayer(), icmPlayer);

        e.getPlayer().teleport(e.getPlayer().getLocation().getWorld().getSpawnLocation());

        e.setJoinMessage("§7[§a+§7] " + e.getPlayer().getDisplayName());
        PvPBox.getKitNPC.userJoinUpdate(e.getPlayer());
        if(!PvPBox.getInstance.packetReaderMap.containsKey(e.getPlayer()))
        {
            PacketReader packetReader = new PacketReader(e.getPlayer());
            packetReader.inject();
        }
        else
        {
            PvPBox.getInstance.packetReaderMap.get(e.getPlayer()).inject();

        }
        new BukkitRunnable()
        {
            int i = 2;
            @Override
            public void run() {
                i--;
                if(i <= 0)
                {
                    cancel();
                    PvPBox.getKitNPC.rmvFromTablist(e.getPlayer());
                }
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);


    }

    private void setupScoreAndRank(Player p, ICMPlayer icmPlayer)
    {
        ScoreboardSign scoreboardSign = new ScoreboardSign(p, "§6ICM Network");

        if(PvPBox.getPlayerLoader.getSignMap().containsKey(p))
        {
            scoreboardSign = PvPBox.getPlayerLoader.getSignMap().get(p);
        }

        if(!icmPlayer.getAdressIP().equalsIgnoreCase(p.getName()))
        {
            icmPlayer.setAdressIP(p.getAddress().getHostName());
        }

        scoreboardSign.create();
        scoreboardSign.setLine(0, "§c ");
        scoreboardSign.setLine(1, "§6Coin(s) » §e" + icmPlayer.getCoins() + " €");
        scoreboardSign.setLine(2, "§6Kill(s) » §e" + icmPlayer.getKill());
        scoreboardSign.setLine(3, "§6Mort(s) » §e" + icmPlayer.getDeath());

        scoreboardSign.setLine(4, "§d ");
        scoreboardSign.setLine(5, "§6Kit » §e" + (icmPlayer.getFightingKit() != null ? icmPlayer.getFightingKit().getKitName() : "§cX"));
        scoreboardSign.setLine(6, "§6Cooldown » §e" + (icmPlayer.getCooldown() > 0 ? icmPlayer.getCooldown() + " seconde(s)" : "§cX"));
        scoreboardSign.setLine(7, "§6Combat » " + (icmPlayer.isCombatTag() ? icmPlayer.getCombatTag() + " seconde(s)" : "§cX"));

        scoreboardSign.setLine(8, "§7 ");
        scoreboardSign.setLine(9, "§eplay.icm-network.net §7(§a" + PvPBox.getInstance.getServer().getOnlinePlayers().size() + "§7)");
        Team m;

        PvPBox.getPlayerLoader.getSignMap().put(icmPlayer, scoreboardSign);
        switch (p.getName())
        {
            case "Shurisko": case "Zomboglace": case "Orium57":
                m = PvPBox.getTeamManager.getTeamHashMap().get(RankEnum.ADMINISTRATOR);
                p.setDisplayName(RankEnum.ADMINISTRATOR.getPrefix() + p.getName());
                m.addEntry(p.getName());
                icmPlayer.setRank(RankEnum.ADMINISTRATOR);
                break;
            case "ZZ":
                m = PvPBox.getTeamManager.getTeamHashMap().get(RankEnum.MODERATOR);
                p.setDisplayName(RankEnum.MODERATOR.getPrefix() + p.getName());
                m.addEntry(p.getName());
                icmPlayer.setRank(RankEnum.MODERATOR);
                break;
            default:
                m = PvPBox.getTeamManager.getTeamHashMap().get(RankEnum.PLAYER);
                p.setDisplayName(RankEnum.PLAYER.getPrefix() + p.getName());
                m.addEntry(p.getName());
                icmPlayer.setRank(RankEnum.PLAYER);
                break;
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
            sb.setLine(9, "§eplay.icm-network.net §7(§a" + PvPBox.getInstance.getServer().getOnlinePlayers().size() + "§7)");

            nmsTitle.sendTabTitle(pall,
                    "§d \n §6 ICM Network \n \n §7Vous êtes connecté sur le serveur §aKitPVP §7(§a" + Bukkit.getOnlinePlayers().size() + "§7) \n ",
                    "\n §eKill(s) : §6" + ip.getKill() + "§e | Mort(s) : §6" + ip.getDeath() + "§e | §eCoin(s) : §6" + ip.getCoins() + " €");

        }


    }

}
