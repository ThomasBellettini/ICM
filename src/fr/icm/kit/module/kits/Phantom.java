package fr.icm.kit.module.kits;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Phantom extends Kit {

    Tier[] tier = new Tier[3];

    public Phantom() {
        super("phantom", 19, false, false);

        Presentation p = new Presentation("§cPhantom", Arrays.asList("Je vol"), Material.DIAMOND);
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
        bootsMeta.setColor(Color.fromRGB(255,175,0));
        legsMeta.setColor(Color.fromRGB(255,175,0));
        chestMeta.setColor(Color.fromRGB(255,175,0));
        helmetMeta.setColor(Color.fromRGB(255,175,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack plume = new ItemStack(Material.FEATHER);
        ItemMeta plumeMeta = plume.getItemMeta();
        plumeMeta.setDisplayName("Plume Magique");
        plume.setItemMeta(plumeMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(plume);
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
        bootsMeta.setColor(Color.fromRGB(255,175,0));
        legsMeta.setColor(Color.fromRGB(255,175,0));
        chestMeta.setColor(Color.fromRGB(255,175,0));
        helmetMeta.setColor(Color.fromRGB(255,175,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack plume = new ItemStack(Material.FEATHER);
        ItemMeta plumeMeta = plume.getItemMeta();
        plumeMeta.setDisplayName("Plume Magique");
        plume.setItemMeta(plumeMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(plume);
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
        bootsMeta.setColor(Color.fromRGB(255,175,0));
        legsMeta.setColor(Color.fromRGB(255,175,0));
        chestMeta.setColor(Color.fromRGB(255,175,0));
        helmetMeta.setColor(Color.fromRGB(255,175,0));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1 , true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        ItemStack plume = new ItemStack(Material.FEATHER);
        ItemMeta plumeMeta = plume.getItemMeta();
        plumeMeta.setDisplayName("Plume Magique");
        plume.setItemMeta(plumeMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(plume);
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }
}
