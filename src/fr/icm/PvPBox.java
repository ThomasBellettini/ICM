package fr.icm;

import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PartyLoader;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.general.commands.CommandMessage;
import fr.icm.general.commands.CommandReply;
import fr.icm.general.event.*;
import fr.icm.general.utils.ICMMessageConfig;
import fr.icm.general.utils.ScoreboardSign;
import fr.icm.kit.minecraft.command.KitCommand;
import fr.icm.kit.minecraft.command.admin.AdminKitCommand;
import fr.icm.kit.minecraft.command.party.PartyCommand;
import fr.icm.kit.minecraft.event.*;
import fr.icm.kit.minecraft.gui.ICMGuiKit;
import fr.icm.kit.minecraft.utils.ICMNPC;
import fr.icm.kit.minecraft.utils.network.PacketReader;
import fr.icm.kit.module.event.*;
import fr.icm.kit.module.kits.*;
import fr.icm.kit.module.utils.KitLoader;
import fr.icm.rank.module.event.TeamManager;
import fr.icm.rank.module.utils.RankEnum;
import fr.icm.utils.ICMStoragePlayer;
import fr.icm.utils.NMSTitle;
import fr.icm.utils.message.ICMConfigMessageStorage;
import fr.icm.zone.command.CommandZone;
import fr.icm.zone.event.UserEditZoneEvent;
import fr.icm.zone.event.UserRemoveZoneEvent;
import fr.icm.zone.event.UserZoneCreate;
import fr.icm.zone.storage.ICMZoneStorage;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import java.util.HashMap;
import java.util.Map;


public class PvPBox extends JavaPlugin
{

    public static PvPBox getInstance;

    public static KitLoader getKitLoader;
    public static PlayerLoader getPlayerLoader;
    public static PartyLoader getPartyLoader;
    public static ICMStoragePlayer getStorage;
    public static TeamManager getTeamManager;
    public static NMSTitle nmsTitle;
    public static ZoneLoader getZoneLoader;
    public static ICMGuiKit getGuiKit;
    public static ICMZoneStorage getZoneStorage;

    public static ICMMessageConfig getConfig;

    public static ICMNPC getKitNPC;

    public Map<Player, PacketReader> packetReaderMap = new HashMap<>();

    /**
     * @Libs
     */

    public void onEnable()
    {
        getInstance = this;
        getKitLoader = new KitLoader();
        getPlayerLoader = new PlayerLoader();
        getPartyLoader = new PartyLoader();
        getStorage = new ICMStoragePlayer();
        getTeamManager = new TeamManager();
        nmsTitle = new NMSTitle();
        getZoneLoader = new ZoneLoader();
        getZoneStorage = new ICMZoneStorage();

        getGuiKit = new ICMGuiKit();
        getKitNPC = new ICMNPC("", Bukkit.getWorlds().get(0).getSpawnLocation());

        loadKit();

        getStorage.setup();
        getZoneStorage.setup();

        getServer().getPluginManager().registerEvents(new EventEntity(), this);
        getServer().getPluginManager().registerEvents(new EventMovement(), this);

        getServer().getPluginManager().registerEvents(new EventClickInventory(), this);

        getServer().getPluginManager().registerEvents(new UserJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new UserLeaveEvent(), this);

        getServer().getPluginManager().registerEvents(new EventDurability(), this);
        getServer().getPluginManager().registerEvents(new UserDieEvent(), this);
        getServer().getPluginManager().registerEvents(new UserSendChatMessage(), this);

        getServer().getPluginManager().registerEvents(new UserEditZoneEvent(), this);
        getServer().getPluginManager().registerEvents(new UserZoneCreate(), this);

        getServer().getPluginManager().registerEvents(new UserRemoveZoneEvent(), this);


        getServer().getPluginManager().registerEvents(new UserPvPEvent(), this);
        getServer().getPluginManager().registerEvents(new UserTakeDamage(), this);
        getServer().getPluginManager().registerEvents(new UserUpdateEvent(), this);
        getServer().getPluginManager().registerEvents(new UserPlaceOrBreakBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new UserInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new UserTeleportEvent(), this);
        getServer().getPluginManager().registerEvents(new UserBrokeItemEvent(), this);
        getServer().getPluginManager().registerEvents(new AdminKitCommand(), this);

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("party").setExecutor(new PartyCommand());

        getCommand("message").setExecutor(new CommandMessage());
        getCommand("reply").setExecutor(new CommandReply());
        getCommand("zone").setExecutor(new CommandZone());
        getCommand("akit").setExecutor(new AdminKitCommand());

        setupRankScoreboard();

        new ICMConfigMessageStorage().setup();

        if(getConfig == null)
        {
            getConfig = new ICMMessageConfig();
        }
        savingSystem();
    }

    private void savingSystem()
    {
        new BukkitRunnable()
        {
            @Override
            public void run() {
                getPlayerLoader.saveAll();
            }
        }.runTaskTimer(this, 0, 1200*30L);
    }

    private void setupRankScoreboard()
    {
        getKitNPC.spawn();
        for(Player p : Bukkit.getOnlinePlayers())
        {
            ICMPlayer icmPlayer = getPlayerLoader.getICMByPlayer(p);
            if(icmPlayer == null)
            {
                icmPlayer = new ICMPlayer(p.getName(), 0, 0, 0, p.getAddress().getHostName());
                PvPBox.getPlayerLoader.register(icmPlayer);
            }


            ScoreboardSign scoreboardSign = new ScoreboardSign(PvPBox.getPlayerLoader.getPlayerByAPI(icmPlayer), "§6ICM Network");
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

            PvPBox.getPlayerLoader.getSignMap().put(icmPlayer, scoreboardSign);


            Team m;

            switch (p.getName())
            {
                case "Shurisko": case "Zomboglace": case "Nastyco_": case "Orium57":
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
                case "ChocAAA":
                    m = PvPBox.getTeamManager.getTeamHashMap().get(RankEnum.HELPER);
                    p.setDisplayName(RankEnum.HELPER.getPrefix() + p.getName());
                    m.addEntry(p.getName());
                    icmPlayer.setRank(RankEnum.HELPER);
                    break;
                default:
                    m = PvPBox.getTeamManager.getTeamHashMap().get(RankEnum.PLAYER);
                    p.setDisplayName(RankEnum.PLAYER.getPrefix() + p.getName());
                    m.addEntry(p.getName());
                    icmPlayer.setRank(RankEnum.PLAYER);
                    break;
            }
            nmsTitle.sendTabTitle(p,
                    "§d \n §6 ICM Network \n \n §7Vous êtes connecté sur le serveur §aKitPVP §7(§a" + Bukkit.getOnlinePlayers().size() + "§7) \n ",
                    "\n §eKill(s) : §6" + icmPlayer.getKill() + "§e | Mort(s) : §6" + icmPlayer.getDeath() + "§e | §eCoin(s) : §6" + icmPlayer.getCoins() + " €");
        }

        Location location = Bukkit.getWorlds().get(0).getSpawnLocation();
        location.setY(10);
        location.getBlock().setType(Material.SIGN_POST);


        for(Player pall : Bukkit.getOnlinePlayers())
        {
            pall.setScoreboard(PvPBox.getTeamManager.getScoreboard());
        }
        /**
        IChatBaseComponent iChatBaseComponent[] = { IChatBaseComponent.ChatSerializer.a("{\"text\": \"§6§lEnter Pseudo\"}"), IChatBaseComponent.ChatSerializer.a("{\"text\": \"§4§m-*---------*\"}"),
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}"), IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}")};

        for(Player packetPlayer : Bukkit.getOnlinePlayers())
        {
            CraftPlayer p = (CraftPlayer) packetPlayer;

            PacketPlayOutOpenSignEditor packetPlayOutOpenSignEditor = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getX(), location.getY(), location.getZ()));
            PacketPlayOutUpdateSign packetPlayOutUpdateSign = new PacketPlayOutUpdateSign(((CraftWorld)(Bukkit.getWorlds().get(0))).getHandle(), new BlockPosition(location.getX(), location.getY(), location.getZ()),
                   iChatBaseComponent);
            p.getHandle().playerConnection.sendPacket(packetPlayOutUpdateSign);
            p.getHandle().playerConnection.sendPacket(packetPlayOutOpenSignEditor);
        }
         **/
    }


    @Override
    public void onDisable()
    {
        getKitNPC.destroy();
        getPlayerLoader.saveAll();

        if(!getPlayerLoader.getSignMap().isEmpty())
       {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                if(packetReaderMap.containsKey(p))
                {
                    PacketReader packetReader = PvPBox.getInstance.packetReaderMap.get(p);
                    packetReader.uninject();
                }
                ICMPlayer icmPlayer = getPlayerLoader.getICMByPlayer(p);
                if(icmPlayer == null)
                    continue;
                getPlayerLoader.getSignMap().get(icmPlayer).destroy();
            }
       }
    }

    private void loadKit()
    {
        getKitLoader.register(new Apache());
        getKitLoader.register(new Archer());
        getKitLoader.register(new BlackBeard());
        getKitLoader.register(new Cameleon());
        getKitLoader.register(new Duelliste());
        getKitLoader.register(new Lightner());
        getKitLoader.register(new Elf());
        getKitLoader.register(new ElfKing());
        getKitLoader.register(new Fireman());
        getKitLoader.register(new Flash());
        getKitLoader.register(new GrandPere());
        getKitLoader.register(new Heracles());
        getKitLoader.register(new Hider());
        getKitLoader.register(new Iceman());
        getKitLoader.register(new Jumper());
        getKitLoader.register(new Legendary());
        getKitLoader.register(new Tank());
        getKitLoader.register(new TimeMaster());
        getKitLoader.register(new Monk());
        getKitLoader.register(new Paladin());
        getKitLoader.register(new Phantom());
        getKitLoader.register(new PoidsLourd());
        getKitLoader.register(new Poseidon());
        getKitLoader.register(new Specialist());
        getKitLoader.register(new Stomper());
        getKitLoader.register(new Switcher());
        getKitLoader.register(new Terrorist());
        getKitLoader.register(new Thor());
        getKitLoader.register(new Urgal());
        getKitLoader.register(new Vampire());
        getKitLoader.register(new Viking());
        getKitLoader.register(new Viper());
        getKitLoader.register(new Worm());
    }
}

