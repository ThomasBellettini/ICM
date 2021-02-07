package fr.icm.kit.module.kits;

import fr.icm.PvPBox;
import fr.icm.entity.ICMParty;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Switcher extends Kit {

    Tier[] tier = new Tier[3];

    public Switcher() {
        super("switcher", 24, false, false);

        Presentation p = new Presentation("§6» Kit Switcher", Arrays.asList("Switch !"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 5);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p, 5);
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
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.SNOW_BALL, 32));
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
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.SNOW_BALL, 32));
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
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(new ItemStack(Material.SNOW_BALL, 32));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }



    public void whenHittingPlayerWithSnowBall(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player))
            return;

        Snowball snowball = (Snowball) e.getDamager();
        Player damager = (Player) snowball.getShooter();
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

        damager.sendMessage("§c[!] SWITCH !");
        victim.sendMessage("§c[!] SWITCH !");

        damager.playSound(damager.getLocation(), Sound.ENDERMAN_TELEPORT,10,0);
        victim.playSound(victim.getLocation(), Sound.ENDERMAN_TELEPORT, 10,0);

        Location damagerLoc = damager.getLocation();
        Location victimLoc = victim.getLocation();

        damager.teleport(victimLoc);
        victim.teleport(damagerLoc);
    }
}
