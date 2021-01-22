package fr.icm.entity;

import fr.icm.PvPBox;
import fr.icm.general.utils.ScoreboardSign;
import fr.icm.kit.minecraft.gui.ICMGuiKit;
import fr.icm.kit.module.api.Kit;
import fr.icm.rank.module.utils.RankEnum;
import fr.icm.zone.api.ICMZone;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ICMPlayer {

    private String name;

    private HashMap<String, Integer> levelPerKit = new HashMap<>();
    private List<String> kitAccess = new ArrayList<>();
    private int kill;
    private int death;
    private int coins;
    private String adressIP;

    private transient String lastDM;

    private transient boolean isFighting;
    private transient Kit fightingKit;
    private  transient boolean inDuel = false;
    private transient ICMParty invitation;
    private transient RankEnum rank;

    private transient int cooldown = 0;
    private transient int combatTag = 0;

    private transient ICMZone zone_create;
    private transient Location loc_zone;

    public transient ICMZone actualZone;

    public ICMPlayer(String name, int kill, int death, int coins, String adressIP) {
        this.name = name;
        this.kill = kill;
        this.death = death;
        this.coins = coins;
        this.adressIP = adressIP;

        this.isFighting = false;
        invitation = null;
        rank = RankEnum.PLAYER;

    }

    public ICMPlayer(String name, int kill, int death, int coins, String adressIP, HashMap<String, Integer> levelPerKit, List<String> kitAccess) {
        this.name = name;
        this.kill = kill;
        this.death = death;
        this.coins = coins;
        this.adressIP = adressIP;
        this.levelPerKit = levelPerKit;
        this.kitAccess = kitAccess;

        this.isFighting = false;
        invitation = null;

    }

    public Location getLoc_zone() {
        return loc_zone;
    }

    public void setLoc_zone(Location loc_zone) {
        this.loc_zone = loc_zone;
    }

    public ICMZone getZone_create() {
        return zone_create;
    }

    public void setZone_create(ICMZone zone_create) {
        this.zone_create = zone_create;
    }

    public ICMZone getActualZone() {
        return actualZone;
    }

    public void register()
    {
        PvPBox.getPlayerLoader.register(this);
    }

    public void setActualZone(ICMZone actualZone) {
        this.actualZone = actualZone;
    }

    public String getLastDM() {
        return lastDM;
    }

    public void setLastDM(String lastDM) {
        this.lastDM = lastDM;
    }

    public RankEnum getRank() {
        return rank;
    }

    public void setRank(RankEnum rank) {
        this.rank = rank;
    }

    public int getCooldown() {
        return cooldown;
    }
    public int getCombatTag() {
        return combatTag;
    }

    public void setCooldown(int cooldown) {
        if(this.cooldown>0 && cooldown != 0) return;
        this.cooldown = cooldown;
        if(cooldown != 0)
            startCooldown();
    }

    public void setLevelPerKit(HashMap<String, Integer> levelPerKit) {
        this.levelPerKit = levelPerKit;
    }

    private void removeCC()
    {
        this.cooldown = getCooldown() - 1;
    }
    private void removeCombatSecond()
    {
        this.combatTag = getCombatTag() - 1;
    }

    public boolean isInDuel() {
        return inDuel;
    }

    public ICMParty getInvitation() {
        return invitation;
    }

    public void setInvitation(ICMParty invitation) {
        this.invitation = invitation;
    }

    public void setInDuel(boolean inDuel) {
        this.inDuel = inDuel;
    }

    public List<String> getKitAccess() {
        return kitAccess;
    }

    public void setKitAccess(List<String> kitAccess) {
        this.kitAccess = kitAccess;
    }

    public boolean isCombatTag()
    {
        if(this.combatTag > 0)
            return true;
        return false;
    }

    private void startCooldown()
    {

        new BukkitRunnable()
        {
            @Override
            public void run() {
                removeCC();
                Player p = Bukkit.getPlayer(name);
                if(p != null)
                {
                    if(PvPBox.getPlayerLoader.getSignMap().containsKey(PvPBox.getPlayerLoader.getICMByPlayer(p)))
                    {
                        ScoreboardSign s = PvPBox.getPlayerLoader.getSignMap().get(PvPBox.getPlayerLoader.getICMByPlayer(Bukkit.getPlayer(name)));
                        s.setLine(6, "§6Cooldown » §e" + (getCooldown() > 0 ? getCooldown() + " seconde(s)" : "§cX"));
                    }
                    else
                    {
                        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(Bukkit.getPlayer(name));
                        ScoreboardSign scoreboardSign = new ScoreboardSign(PvPBox.getPlayerLoader.getPlayerByAPI(icmPlayer), "§6ICM Network");
                        scoreboardSign.create();
                        scoreboardSign.setLine(0, "§c ");
                        scoreboardSign.setLine(1, "§6Coin(s) » §e" + icmPlayer.getCoins());
                        scoreboardSign.setLine(2, "§6Kill(s) » §e" + icmPlayer.getKill());
                        scoreboardSign.setLine(3, "§6Mort(s) » §e" + icmPlayer.getDeath());

                        scoreboardSign.setLine(4, "§d ");
                        scoreboardSign.setLine(5, "§6Kit » §e" + (icmPlayer.getFightingKit() != null ? icmPlayer.getFightingKit().getKitName() : "§cX"));
                        scoreboardSign.setLine(6, "§6Cooldown » §e" + (icmPlayer.getCooldown() > 0 ? icmPlayer.getCooldown() + " seconde(s)" : "§cX"));
                        scoreboardSign.setLine(7, "§cCombat » " + (icmPlayer.isCombatTag() ? "§a✔" : "§cX"));

                        scoreboardSign.setLine(8, "§7 ");
                        scoreboardSign.setLine(9, "§eplay.icm-network.net §7(§a" + PvPBox.getInstance.getServer().getOnlinePlayers().size() + "§7)");
                        PvPBox.getPlayerLoader.getSignMap().put(icmPlayer, scoreboardSign);
                    }
                }
                if(getCooldown() <= 0)
                {
                    cancel();
                    if(Bukkit.getPlayer(name) != null)
                        Bukkit.getPlayer(name).sendMessage("§6[!] Votre cooldown à expirer !");
                }
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);
    }

    private void startCombatCooldown()
    {
        new BukkitRunnable()
        {
            @Override
            public void run() {
                removeCombatSecond();
                Player p = Bukkit.getPlayer(name);
                if(p != null)
                {
                    if(PvPBox.getPlayerLoader.getSignMap().containsKey(PvPBox.getPlayerLoader.getICMByPlayer(p)))
                    {
                        ScoreboardSign s = PvPBox.getPlayerLoader.getSignMap().get(PvPBox.getPlayerLoader.getICMByPlayer(Bukkit.getPlayer(name)));
                        s.setLine(7, "§6Combat » " + (isCombatTag() ? getCombatTag() + " seconde(s)" : "§cX"));
                    }
                    else
                    {


                        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(Bukkit.getPlayer(name));
                        ScoreboardSign scoreboardSign = new ScoreboardSign(PvPBox.getPlayerLoader.getPlayerByAPI(icmPlayer), "§6ICM Network");
                        scoreboardSign.create();
                        scoreboardSign.setLine(0, "§c ");
                        scoreboardSign.setLine(1, "§6Coin(s) » §e" + icmPlayer.getCoins());
                        scoreboardSign.setLine(2, "§6Kill(s) » §e" + icmPlayer.getKill());
                        scoreboardSign.setLine(3, "§6Mort(s) » §e" + icmPlayer.getDeath());

                        scoreboardSign.setLine(4, "§d ");
                        scoreboardSign.setLine(5, "§6Kit » §e" + (icmPlayer.getFightingKit() != null ? icmPlayer.getFightingKit().getKitName() : "§cX"));
                        scoreboardSign.setLine(6, "§6Cooldown » §e" + (icmPlayer.getCooldown() > 0 ? icmPlayer.getCooldown() + " seconde(s)" : "§cX"));
                        scoreboardSign.setLine(7, "§6Combat » " + (icmPlayer.isCombatTag() ? icmPlayer.getCombatTag() + " seconde(s)" : "§cX"));

                        scoreboardSign.setLine(8, "§7 ");
                        scoreboardSign.setLine(9, "§eplay.icm-network.net §7(§a" + PvPBox.getInstance.getServer().getOnlinePlayers().size() + "§7)");
                        PvPBox.getPlayerLoader.getSignMap().put(icmPlayer, scoreboardSign);
                    }
                }
                if(getCombatTag() <= 0)
                {
                    cancel();
                    Bukkit.getPlayer(name).sendMessage("§a[!] Vous n'êtes plus en combat !");
                }
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);
    }


    public void setCombatTag(int combatTag) {
        this.combatTag = combatTag;
    }

    public void setCombatTag() {
        if(this.combatTag <= 0)
        {
            this.combatTag = 30;
            Bukkit.getPlayer(getName()).sendMessage("§c[!] Vous êtes désormais en combat pendant 30 secondes !");
            startCombatCooldown();
        }
        else
        {
            this.combatTag = 30;
        }
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getLevelPerKit() {
        return levelPerKit;
    }

    public int getKill() {
        return kill;
    }

    public int getDeath() {
        return death;
    }

    public int getCoins() {
        return coins;
    }

    public String getAdressIP() {
        return adressIP;
    }

    public boolean isFighting() {
        return isFighting;
    }

    public Kit getFightingKit() { return fightingKit; }

    public void setLevelPerKit(String kit, int level) {
        this.levelPerKit.replace(kit, level);
    }

    public void addKill() {
        this.kill = kill + 1;
    }

    public void addDeath() {
        this.death = death + 1;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setAdressIP(String adressIP) {
        this.adressIP = adressIP;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }

    public void setFightingKit(Kit fightingKit) {
        this.fightingKit = fightingKit;
    }

    public double getKD()
    {
        return kill / death;
    }

    public void setDefaultKit()
    {

        HashMap<String, Integer> levelPerKit = new HashMap<>();
                for(Kit k : PvPBox.getKitLoader.getKit())
                {
                    if(levelPerKit.containsKey(k.getKitName()))
                    {
                        continue;
                    }
                    levelPerKit.put(k.getKitName(), 1);
                }
        this.levelPerKit = levelPerKit;
    }

    public int getKitLevel(String kitName)
    {
        Kit k = PvPBox.getKitLoader.getKitPerName(kitName);
        if(k == null)
        {
            return 1;
        }
        if(levelPerKit.containsKey(k.getKitName()))
        {
            return levelPerKit.get(k.getKitName());
        }

        return 1;
    }

    public void sendPartyInvitation(ICMParty party)
    {
        if(!PvPBox.getPartyLoader.getParties().contains(party)) return;
        if(invitation != null && invitation.getOwner().getName().equalsIgnoreCase(party.getOwner().getName())) return;
        this.invitation = party;
        List<String> message = new ArrayList<>(Arrays.asList(
                clickableMessage("§b[!] Vous avez reçu une invitation à rejoindre le groupe de §9" + party.getOwner().getName() + " §7(§aClique ici§7)"
                , "§a[!] Si tu cliques tu rejoindras le Groupe !", "/party join")));
        for(String ms : message)
        {
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(ms);
            PacketPlayOutChat packet = new PacketPlayOutChat(comp);
            ((CraftPlayer) Bukkit.getPlayer(getName())).getHandle().playerConnection.sendPacket(packet);
        }
        Bukkit.getPlayer(party.getOwner().getName()).sendMessage("§b[!] Vous avez envoyé une invitation pour rejoindre votre groupe à §9" + name);

    }

    private String clickableMessage(String value, String hover, String execute)
    {
        return "{text:\"" + value + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hover + "\"},clickEvent:{action:\"run_command\",value:\"" + execute + "\"}}";
    }
}
