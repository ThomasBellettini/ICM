package fr.icm.kit.module.kits;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import fr.icm.kit.module.utils.DuelBox;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Duelliste extends Kit {

    Tier[] tier = new Tier[1];

    private boolean isDuel = false;
    private DuelBox duelBox;

    public Duelliste() {
        super("duelliste", 5, false, false);

        Presentation p = new Presentation("§6» Kit Duelliste", Arrays.asList(" "
                , " "
                , "     §eCapacitée »"
                , "     §aVous pouvez defiez vos adversaire en 1 contre 1 !"
                , " "
                , "     §b[!] Ce Kit est Niveau 1"
                , "     §c[!] Vous devez être VIP pour utilisez ce Kit !"
                , " ")
                , Material.MOB_SPAWNER);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 120);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        chestMeta.setColor(Color.fromRGB(0,125,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack cage = new ItemStack(Material.MOB_SPAWNER);
        ItemMeta cageMeta = cage.getItemMeta();
        cageMeta.setDisplayName("Cage");
        cage.setItemMeta(cageMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(cage);
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }



    @Override
    public void whenRightClickOnPlayer(PlayerInteractEntityEvent event)
    {
        if(event.isCancelled())
            return;
        Player victim = (Player) event.getRightClicked();
        Player user = event.getPlayer();

        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(user);
        ICMPlayer icmVictim = PvPBox.getPlayerLoader.getICMByPlayer(victim);

        if(icmPlayer.isInDuel() || icmVictim.isInDuel())
            return;
        if(isDuel)
            return;
        if(user.getItemInHand().getType() != Material.MOB_SPAWNER)
            return;

        if(icmPlayer.getCooldown() > 0)
        {
            Bukkit.getPlayer(icmPlayer.getName()).sendMessage("§c[!] Erreut tu es en cooldown pendant encore §6" + icmPlayer.getCooldown() + " §4secondes");
            return;
        }

        if(icmPlayer.isInDuel() || icmVictim.isInDuel())
            return;
        Tier tierKit = this.getKitTierByLevel(icmPlayer.getKitLevel(getKitName()));
        icmPlayer.setCooldown(tierKit.getCooldown());

        icmPlayer.setInDuel(true);
        icmVictim.setInDuel(true);

        duelBox = new DuelBox(user.getLocation(), icmPlayer, icmVictim, user.getLocation(), victim.getLocation());
        duelBox.summon();
        isDuel = true;

        new BukkitRunnable()
        {
            int delay = 120;
            @Override
            public void run() {
                if(delay <= 0)
                {
                    isDuel = false;
                    duelBox.remove();
                    cancel();
                }
                if(!isDuel)
                    cancel();
                delay--;
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);

    }

    @Override
    public void whenKillingPlayer(PlayerDeathEvent event)
    {
        if(!isDuel)
            return;
        Player p = event.getEntity();
        ICMPlayer victim = PvPBox.getPlayerLoader.getICMByPlayer(p);
        ICMPlayer killer = PvPBox.getPlayerLoader.getICMByPlayer(event.getEntity().getKiller());
        if(victim == null) return;
        if(killer == null) return;
        if(!victim.isInDuel()) return;
        if(!killer.isInDuel()) return;
        victim.setInDuel(false);
        killer.setInDuel(false);
        duelBox.setLastOther(null);
        duelBox.remove();
        isDuel = false;
    }

    @Override
    public void whenDie(PlayerDeathEvent event)
    {
        if(!isDuel)
            return;
        Player p = event.getEntity();
        ICMPlayer victim = PvPBox.getPlayerLoader.getICMByPlayer(p);
        ICMPlayer killer = PvPBox.getPlayerLoader.getICMByPlayer(event.getEntity().getKiller());
        if(victim == null) return;
        if(killer == null) return;
        if(!victim.isInDuel()) return;
        if(!killer.isInDuel()) return;
        victim.setInDuel(false);
        killer.setInDuel(false);
        duelBox.setLastOwner(null);
        duelBox.remove();
        isDuel = false;
    }
}

