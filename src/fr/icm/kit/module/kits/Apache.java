package fr.icm.kit.module.kits;

import fr.icm.PvPBox;
import fr.icm.entity.ICMParty;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Apache extends Kit {

    Tier[] tier = new Tier[3];

    public Apache() {
        super("apache", 1, true, false);

        Presentation p = new Presentation("§6» Kit Apache", Arrays.asList(" "
                , " "
                , "     §eCapacitée »"
                , "     §aVous pouvez envoyer des fléches Explosives !"
                , " "
                , "     §b[!] Ce Kit est Niveau 1"
                , "     §c[!] Vous devez être VIP pour utilisez ce Kit !"
                , " ")
                , Material.BONE);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 10);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p, 8);
        tier[2] = new Tier(3, 300, tierThree(), getKitName(), p, 5);

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
        bootsMeta.setColor(Color.fromRGB(0,0,0));
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        helmetMeta.setColor(Color.fromRGB(0,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack fleches = new ItemStack(Material.ARROW, 15);
        ItemMeta flechesMeta = fleches.getItemMeta();
        flechesMeta.setDisplayName("Flèches Explosives");
        fleches.setItemMeta(flechesMeta);

        //stuff.add(tomahawk);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(fleches);
        stuff.add(new ItemStack(Material.MONSTER_EGG,2, (short) 95));
        stuff.add(new ItemStack(Material.BONE, 8));
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
        bootsMeta.setColor(Color.fromRGB(0,0,0));
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        helmetMeta.setColor(Color.fromRGB(0,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack fleches = new ItemStack(Material.ARROW, 15);
        ItemMeta flechesMeta = fleches.getItemMeta();
        flechesMeta.setDisplayName("Flèches Explosives");
        fleches.setItemMeta(flechesMeta);


        //stuff.add(tomahawk);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(fleches);
        stuff.add(new ItemStack(Material.MONSTER_EGG, 3, (short) 95));
        stuff.add(new ItemStack(Material.BONE, 12));
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
        bootsMeta.setColor(Color.fromRGB(0,0,0));
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        helmetMeta.setColor(Color.fromRGB(0,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack fleches = new ItemStack(Material.ARROW, 15);
        ItemMeta flechesMeta = fleches.getItemMeta();
        flechesMeta.setDisplayName("Flèches Explosives");
        fleches.setItemMeta(flechesMeta);


        //stuff.add(tomahawk x2);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(fleches);
        stuff.add(new ItemStack(Material.MONSTER_EGG, 4, (short) 95));
        stuff.add(new ItemStack(Material.BONE, 16));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }



    public void whenArrowHit(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player))
            return;

        Arrow arrow = (Arrow) e.getDamager();
        Player damager = (Player) arrow.getShooter();
        Player victim = (Player) e.getEntity();

        if (victim.getGameMode() != GameMode.SURVIVAL)
            return;

        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(damager);
        ICMParty icmParty = PvPBox.getPartyLoader.getPartiesWithMember(icmPlayer);
        int kitLevel = icmPlayer.getKitLevel(this.getKitName());


        if(icmPlayer.getCooldown() > 0)
        {
            Bukkit.getPlayer(icmPlayer.getName()).sendMessage("§c[!] Erreur tu es en cooldown pendant encore §6" + icmPlayer.getCooldown() + " §4secondes");
            return;
        }

        Tier tierKit = this.getKitTierByLevel(kitLevel);
        icmPlayer.setCooldown(tierKit.getCooldown());

        victim.sendMessage("§c[!] Une flèche explosive vous à touchez et explosera dans 3 secondes.");

        new BukkitRunnable()
        {
            int radius = 5;
            int timer = 3;

            @Override
            public void run() {
                if (timer <= 0)
                {
                    arrow.getWorld().playSound(e.getEntity().getLocation(), Sound.EXPLODE, 1, (float) 1);
                    arrow.getWorld().playEffect(e.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 1);

                    arrow.setCustomName("§bBoom !");
                    arrow.remove();

                    while(radius > 0) {
                        if (icmParty == null) {
                            for (Entity p : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                                if (p.getName().equalsIgnoreCase(damager.getName())) {
                                    continue;
                                }

                                if (p instanceof Player)
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
                            radius--;

                            if (e.getEntity().isDead() || e.getEntity() == damager)
                                continue;
                            if (((Player) e.getEntity()).getHealth() -1 <= 0)
                            {
                                ((Player) e.getEntity()).setHealth(0);
                                continue;
                            }
                            ((Player) e.getEntity()).damage(0);
                            ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - 1);
                        }
                        else {
                            for (Entity p : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                                if(p instanceof  Player) {
                                    Player w = (Player) p;
                                    if (icmParty != null && icmParty.convertArrayPlayer().contains(w))
                                        continue;
                                    if (p == damager) {
                                        continue;
                                    }

                                    if (p instanceof Player) {
                                        if (p.isDead()) {
                                            continue;
                                        }
                                        if (((Player) p).getHealth() - 1 <= 0) {
                                            ((Player) p).setHealth(0);
                                            continue;
                                        }
                                        ((Player) p).damage(0);
                                        ((Player) p).setHealth(((Player) p).getHealth() - 1);
                                    }
                                }
                            }
                            radius--;

                            if(icmParty.convertArrayPlayer().contains((Player) e.getEntity()))
                                continue;
                            if (e.getEntity().isDead() || e.getEntity() == damager)
                                continue;
                            if (((Player) e.getEntity()).getHealth() -1 <= 0)
                            {
                                ((Player) e.getEntity()).setHealth(0);
                                continue;
                            }
                            ((Player) e.getEntity()).damage(0);
                            ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - 1);
                        }

                    }
                    cancel();
                }
                if (timer > 0) {
                    victim.sendMessage("§c[!]§b " + timer + " §cSeconde avant explosion.");
                }
                timer--;
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);
    }
}
