package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.general.utils.ScoreboardSign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class UserDieEvent implements Listener {

    @EventHandler
    public void whenPlayerDie(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();

            if (p.getKiller() != null && !p.getKiller().getName().equalsIgnoreCase(p.getName())) {
                ICMPlayer icmkiller = PvPBox.getPlayerLoader.getICMByPlayer(p.getKiller());
                if (icmkiller == null)
                    return;
                icmkiller.addKill();
                PvPBox.nmsTitle.sendTabTitle(p.getKiller(),
                        "§d \n §6 ICM Network \n \n §7Vous êtes connecté sur le serveur §aKitPVP §7(§a" + Bukkit.getOnlinePlayers().size() + "§7) \n ",
                        "\n §eKill(s) : §6" + icmkiller.getKill() + "§e | Mort(s) : §6" + icmkiller.getDeath() + "§e | §eCoin(s) : §6" + icmkiller.getCoins() + " \uD83D\uDCB0");
                ScoreboardSign sb = PvPBox.getPlayerLoader.getSignMap().get(icmkiller);
                if (sb != null)
                    sb.setLine(2, "§6Kill(s) » §e" + icmkiller.getKill());
                if (icmkiller.getFightingKit() != null)
                    icmkiller.getFightingKit().whenKillingPlayer(e);
            }

        switch (cause) {
            case FALL: case FALLING_BLOCK:
                e.setDeathMessage("§6ICM » §c" +p.getName() + " §eest tombé de trop haut !");
                break;
            case DROWNING:
                e.setDeathMessage("§6ICM » §c" + p.getName() + " §en'était finalement pas un poisson !");
                break;
            case ENTITY_ATTACK: case PROJECTILE:
                 if (p.getKiller() instanceof Player) {
                     if (p.getKiller().getName().equalsIgnoreCase(p.getName()))
                         e.setDeathMessage("§6ICM » §c" + p.getName() + " §ec'est planté sont épée dans le coeur !");
                     else
                         e.setDeathMessage("§6ICM » §c" + p.getName() + " §ec'est fait abattre de sang froid par §a" + p.getKiller().getName());
                 } else
                     e.setDeathMessage("§6ICM » §c" + p.getName() + " §ec'est fait tuer par une créature inhumaine !");
                break;
            default:
                e.setDeathMessage("§6ICM » §c" + p.getName() + " §eest mort mystérieusement !");
                break;
        }

        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(p);
        if (icmPlayer == null)
            return;
        if (icmPlayer.getFightingKit() != null)
            icmPlayer.getFightingKit().whenDie(e);
        icmPlayer.setFightingKit(null);
        icmPlayer.setFighting(false);
        icmPlayer.setCooldown(0);
        icmPlayer.setCombatTag(0);
        icmPlayer.addDeath();

        PvPBox.nmsTitle.sendTabTitle(p,
                "§d \n §6 ICM Network \n \n §7Vous êtes connecté sur le serveur §aKitPVP §7(§a" + Bukkit.getOnlinePlayers().size() + "§7) \n ",
                "\n §eKill(s) : §6" + icmPlayer.getKill() + "§e | Mort(s) : §6" + icmPlayer.getDeath() + "§e | §eCoin(s) : §6" + icmPlayer.getCoins() + " \uD83D\uDCB0");

        ScoreboardSign scoreboardSign = PvPBox.getPlayerLoader.getSignMap().get(icmPlayer);
        if (scoreboardSign != null) {
            scoreboardSign.setLine(3, "§6Mort(s) » §e" + icmPlayer.getDeath());
            scoreboardSign.setLine(5, "§6Kit » §e" + (icmPlayer.getFightingKit() != null ? icmPlayer.getFightingKit().getKitName() : "§cX"));
            scoreboardSign.setLine(6, "§6Cooldown » §e" + (icmPlayer.getCooldown() > 0 ? icmPlayer.getCooldown() + " seconde(s)" : "§cX"));
            scoreboardSign.setLine(7, "§6Combat » " + (icmPlayer.isCombatTag() ? icmPlayer.getCombatTag() + " seconde(s)" : "§cX"));
        }

        Material b1 = p.getLocation().getBlock().getType();
        Material b2 = new Location(p.getWorld(), p.getLocation().getX() + 1, p.getLocation().getY(), p.getLocation().getZ()).getBlock().getType();

        ArrayList<ItemStack> itemStacks = new ArrayList<>(Arrays.asList(p.getInventory().getContents()));
        for (ItemStack armor : p.getInventory().getArmorContents()) {
            if (armor == null || armor.getType() == Material.AIR)
                continue;
            itemStacks.add(armor);
        }
        p.getLocation().getBlock().setType(Material.CHEST);
        new Location(p.getWorld(), p.getLocation().getX() + 1, p.getLocation().getY(), p.getLocation().getZ()).getBlock().setType(Material.CHEST);
        Chest c = (Chest) p.getLocation().getBlock().getState();
        int max = 53;
        int min = 0;
        for (ItemStack item : itemStacks) {
            int randomslot = new Random().nextInt((max - min) + 1) + min;
            while (c.getInventory().getItem(randomslot) != null)
                randomslot = new Random().nextInt((max - min) + 1) + min;
            c.getInventory().setItem(randomslot, item);
        }
        e.getDrops().clear();

        Location w = p.getLocation();

        p.setHealth(p.getMaxHealth());
        p.teleport(p.getLocation().getWorld().getSpawnLocation());

        Location l = new Location(c.getWorld(), w.getX(), w.getY() + 0.5, w.getZ());
        ArrayList<String> name = new ArrayList<>(Arrays.asList("§e§m-*---------------------------*-",
                "§7 ",
                "§6Ici est Mort §c" + p.getName(),
                "§7Le stuff de sa mort est contenu dans ce coffre !",
                "§7 ",
                "§7Le coffre s'auto-détruira dans exactement§a 30 seconde(s)",
                "§d ",
                "§e§m-*---------------------------*-"));

        ArrayList<ArmorStand> armorStandArrayList = new ArrayList<>();
        while (!name.isEmpty()) {
            double s = (0.3 * name.size());
            Location ls = new Location(c.getWorld(), w.getX() + 0.5, l.getY() + s, w.getZ());
            ArmorStand armorStand = (ArmorStand) ls.getWorld().spawnEntity(ls, EntityType.ARMOR_STAND);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(name.get(0));
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStandArrayList.add(armorStand);
            name.remove(0);
        }

        new BukkitRunnable() {
            int s = 30;
            @Override
            public void run() {
                s--;
                if (s <= 0) {
                    c.getBlockInventory().clear();
                    cancel();
                    remove(armorStandArrayList, w, new Location(p.getWorld(), w.getX() + 1, w.getY(), w.getZ()), b1, b2);
                } else {
                    for (ArmorStand armorStand : armorStandArrayList) {
                        if (armorStand == null)
                            continue;
                        if (armorStand.getCustomName().startsWith("§7Le coffre s'auto-détruira dans exactement§a"))
                            armorStand.setCustomName("§7Le coffre s'auto-détruira dans exactement§a "+ s +" seconde(s)");
                    }
                }
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20);
    }

    private void remove(ArrayList<ArmorStand> armorStandArrayList, Location l1, Location l2, Material b1, Material b2)
    {
        for (ArmorStand armorStand : armorStandArrayList)
            armorStand.remove();
        l1.getBlock().setType(b1);
        l2.getBlock().setType(b2);
    }

    @EventHandler
    private void whenPlayerRespawn(PlayerRespawnEvent e)
    {
        Player p = e.getPlayer();
        PvPBox.getKitNPC.userJoinUpdate(p);
    }
}
