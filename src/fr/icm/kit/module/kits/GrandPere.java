package fr.icm.kit.module.kits;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GrandPere extends Kit {

    Tier[] tier = new Tier[3];

    public GrandPere() {
        super("grandpere", 10, false, false);

        Presentation p = new Presentation("§6» Kit Grand Père", Arrays.asList(" "
                , "     §b[!] Ce Kit est Niveau 1"
                , "     §c[!] Vous devez être VIP pour utilisez ce Kit !"
                , " ")
                , Material.EXP_BOTTLE);
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
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName("Canne usée");
        sword.setItemMeta(swordMeta);


        stuff.add(sword);
        //stuff.add(new Itemstack(Material.WATER, 2, SpeedII))
        //stuff.add(new Itemstack(Material.WATER, 2, Slow))
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
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName("Canne usée");
        sword.setItemMeta(swordMeta);


        stuff.add(sword);
        //stuff.add(new Itemstack(Material.WATER, 2, SpeedII + 30sec))
        //stuff.add(new Itemstack(Material.WATER, 2, Slow))
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
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName("Canne");
        sword.setItemMeta(swordMeta);


        stuff.add(sword);
        //stuff.add(new Itemstack(Material.WATER, 2, SpeedII + 30sec))
        //stuff.add(new Itemstack(Material.WATER, 2, Slow))
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }
}
