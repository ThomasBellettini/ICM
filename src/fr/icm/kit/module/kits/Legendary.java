package fr.icm.kit.module.kits;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Legendary extends Kit {

    Tier[] tier = new Tier[3];

    public Legendary() {
        super("legendaire", 15, false, false);

        Presentation p = new Presentation("§cLégendaire", Arrays.asList("Je suis une légende"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p, 0);
        tier[2] = new Tier(3, 300, tierThree(), getKitName(), p, 0);

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
        bootsMeta.setColor(Color.fromRGB(255,255,255));
        legsMeta.setColor(Color.fromRGB(255,255,255));
        chestMeta.setColor(Color.fromRGB(255,255,255));
        helmetMeta.setColor(Color.fromRGB(255,255,255));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        legsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(new ItemStack(Material.ARROW, 24));
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
        bootsMeta.setColor(Color.fromRGB(255,255,255));
        legsMeta.setColor(Color.fromRGB(255,255,255));
        chestMeta.setColor(Color.fromRGB(255,255,255));
        helmetMeta.setColor(Color.fromRGB(255,255,255));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        legsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        sword.setItemMeta(swordMeta);


        stuff.add(sword);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(new ItemStack(Material.ARROW, 24));
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
        bootsMeta.setColor(Color.fromRGB(255,255,255));
        legsMeta.setColor(Color.fromRGB(255,255,255));
        chestMeta.setColor(Color.fromRGB(255,255,255));
        helmetMeta.setColor(Color.fromRGB(255,255,255));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        legsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        sword.setItemMeta(swordMeta);


        stuff.add(sword);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(new ItemStack(Material.ARROW, 24));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }
}
