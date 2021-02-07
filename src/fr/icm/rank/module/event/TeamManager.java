package fr.icm.rank.module.event;

import fr.icm.rank.module.utils.RankEnum;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamManager {

    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private HashMap<RankEnum, Team> teamHashMap = new HashMap<>();

    public TeamManager()
    {
        Team administrator;
        Team responsable;
        Team moderator;
        Team helper;
        Team staff;
        Team friend;
        Team guest;
        Team VIP;
        Team player;

        if(scoreboard.getTeam(RankEnum.ADMINISTRATOR.getGroup_name()) == null)
            administrator = scoreboard.registerNewTeam(RankEnum.ADMINISTRATOR.getGroup_name());
        else
            administrator = scoreboard.getTeam(RankEnum.ADMINISTRATOR.getGroup_name());

        if(scoreboard.getTeam(RankEnum.RESPONSABLE.getGroup_name()) == null)
            responsable = scoreboard.registerNewTeam(RankEnum.RESPONSABLE.getGroup_name());
        else
            responsable = scoreboard.getTeam(RankEnum.RESPONSABLE.getGroup_name());

        if(scoreboard.getTeam(RankEnum.MODERATOR.getGroup_name()) == null)
            moderator = scoreboard.registerNewTeam(RankEnum.MODERATOR.getGroup_name());
        else
            moderator = scoreboard.getTeam(RankEnum.MODERATOR.getGroup_name());

        if(scoreboard.getTeam(RankEnum.HELPER.getGroup_name()) == null)
            helper = scoreboard.registerNewTeam(RankEnum.HELPER.getGroup_name());
        else
            helper = scoreboard.getTeam(RankEnum.HELPER.getGroup_name());

        if(scoreboard.getTeam(RankEnum.STAFF.getGroup_name()) == null)
            staff = scoreboard.registerNewTeam(RankEnum.STAFF.getGroup_name());
        else
            staff = scoreboard.getTeam(RankEnum.STAFF.getGroup_name());

        if(scoreboard.getTeam(RankEnum.FRIEND.getGroup_name()) == null)
            friend = scoreboard.registerNewTeam(RankEnum.FRIEND.getGroup_name());
        else
            friend = scoreboard.getTeam(RankEnum.FRIEND.getGroup_name());

        if(scoreboard.getTeam(RankEnum.GUEST.getGroup_name()) == null)
            guest = scoreboard.registerNewTeam(RankEnum.GUEST.getGroup_name());
        else
            guest = scoreboard.getTeam(RankEnum.GUEST.getGroup_name());

        if(scoreboard.getTeam(RankEnum.VIP.getGroup_name()) == null)
            VIP = scoreboard.registerNewTeam(RankEnum.VIP.getGroup_name());
        else
            VIP = scoreboard.getTeam(RankEnum.VIP.getGroup_name());

        if(scoreboard.getTeam(RankEnum.PLAYER.getGroup_name()) == null)
            player = scoreboard.registerNewTeam(RankEnum.PLAYER.getGroup_name());
        else
            player = scoreboard.getTeam(RankEnum.PLAYER.getGroup_name());

        administrator.setPrefix(RankEnum.ADMINISTRATOR.getPrefix());
        administrator.setSuffix(" §7[§a✔§7]");
        responsable.setPrefix(RankEnum.RESPONSABLE.getPrefix());
        responsable.setSuffix(" §7[§a✔§7]");
        moderator.setPrefix(RankEnum.MODERATOR.getPrefix());
        moderator.setSuffix(" §7[§a✔§7]");
        helper.setPrefix(RankEnum.HELPER.getPrefix());
        helper.setSuffix(" §7[§a✔§7]");
        staff.setPrefix(RankEnum.STAFF.getPrefix());
        staff.setSuffix(" §7[§a✔§7]");
        friend.setPrefix(RankEnum.FRIEND.getPrefix());
        guest.setPrefix(RankEnum.GUEST.getPrefix());
        VIP.setPrefix(RankEnum.VIP.getPrefix());
        player.setPrefix(RankEnum.PLAYER.getPrefix());

        teamHashMap.clear();
        teamHashMap.put(RankEnum.ADMINISTRATOR, administrator);
        teamHashMap.put(RankEnum.RESPONSABLE, responsable);
        teamHashMap.put(RankEnum.MODERATOR, moderator);
        teamHashMap.put(RankEnum.HELPER, helper);
        teamHashMap.put(RankEnum.STAFF, staff);
        teamHashMap.put(RankEnum.FRIEND, friend);
        teamHashMap.put(RankEnum.GUEST, guest);
        teamHashMap.put(RankEnum.VIP, VIP);
        teamHashMap.put(RankEnum.PLAYER, player);
    }
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public HashMap<RankEnum, Team> getTeamHashMap() {
        return teamHashMap;
    }
}
