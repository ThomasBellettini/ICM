package fr.icm.kit.module.kits;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import net.minecraft.server.v1_8_R3.PlayerSelector;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Terrorist extends Kit {

    Tier[] tier = new Tier[3];

    public Terrorist() {
        super("terroriste", 26, false, false);

        Presentation p = new Presentation("§cTerroriste", Arrays.asList("Boom"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 10);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p, 10);
        tier[2] = new Tier(3, 300, tierThree(), getKitName(), p, 10);

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
        bootsMeta.setColor(Color.fromRGB(255,0,0));
        legsMeta.setColor(Color.fromRGB(255,0,0));
        chestMeta.setColor(Color.fromRGB(255,0,0));
        helmetMeta.setColor(Color.fromRGB(255,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.TNT, 32));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }

    private ArrayList<ItemStack> tierTwo() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(255,0,0));
        legsMeta.setColor(Color.fromRGB(255,0,0));
        chestMeta.setColor(Color.fromRGB(255,0,0));
        helmetMeta.setColor(Color.fromRGB(255,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.TNT, 64));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }

    private ArrayList<ItemStack> tierThree() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(255,0,0));
        legsMeta.setColor(Color.fromRGB(255,0,0));
        chestMeta.setColor(Color.fromRGB(255,0,0));
        helmetMeta.setColor(Color.fromRGB(255,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.TNT, 64));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }




    @Override
    public void whenPlacingTNT(BlockPlaceEvent e) {
        if(e.isCancelled())
            return;
        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(e.getPlayer());
        int kitLevel = icmPlayer.getKitLevel(this.getKitName());

        if(icmPlayer.getCooldown() > 0)
        {
            e.setCancelled(true);
            Bukkit.getPlayer(icmPlayer.getName()).sendMessage("§c[!] Erreur tu es en cooldown pendant encore §6" + icmPlayer.getCooldown() + " §4secondes");
            return;
        }

        e.getBlock().setType(Material.AIR);

        Tier tierKit = this.getKitTierByLevel(kitLevel);
        icmPlayer.setCooldown(tierKit.getCooldown());

        TNTPrimed tntPrimed = (TNTPrimed) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
        tntPrimed.setFuseTicks(20 * 4);
        tntPrimed.setIsIncendiary(false);
        tntPrimed.setCustomNameVisible(true);

        ArmorStand armorStand = (ArmorStand) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);

        new BukkitRunnable()
        {
            int radius = 5;
            int timer = 3;
            @Override
            public void run() {
                if(timer <= 0)
                {

                    tntPrimed.setCustomName("§bBoom !");
                    tntPrimed.remove();

                    e.getBlock().getWorld().playSound(tntPrimed.getLocation(), Sound.EXPLODE, 1, (float) 1);
                    e.getBlock().getWorld().playEffect(tntPrimed.getLocation(), Effect.EXPLOSION_LARGE, 1);

                    while(radius > 0) {
                        for (Entity p : armorStand.getNearbyEntities(radius, radius, radius)) {
                            if (p instanceof Player && ((Player) p).getGameMode() == GameMode.SURVIVAL)
                            {
                                if(p.isDead()) {
                                    continue;
                                }
                                if(((Player) p).getHealth() - 1 <= 0) {
                                    ((Player) p).setHealth(0);
                                    continue;
                                }
                                ((Player) p).damage(0);
                                ((Player) p).setHealth(((Player) p).getHealth() - 1);
                            }
                        }
                        armorStand.setHealth(0);
                        radius--;
                    }
                    cancel();
                }
                tntPrimed.setCustomName("§b" + timer + " Seconde");
                timer--;
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);
    }
}
