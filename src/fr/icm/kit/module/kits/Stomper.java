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

public class Stomper extends Kit {

    Tier[] tier = new Tier[3];

    public Stomper() {
        super("stomper", 23, false, false);

        Presentation p = new Presentation("§cStomper", Arrays.asList("J'écrase mes victime"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 5);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p, 5);
        tier[2] = new Tier(3, 300, tierThree(), getKitName(), p, 5);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.SPONGE, 10));
        stuff.add(new ItemStack(Material.IRON_BOOTS));
        stuff.add(legs);
        stuff.add(chest);

        return stuff;
    }

    private ArrayList<ItemStack> tierTwo() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.SPONGE, 15));
        stuff.add(new ItemStack(Material.IRON_BOOTS));
        stuff.add(legs);
        stuff.add(chest);

        return stuff;
    }

    private ArrayList<ItemStack> tierThree() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);

        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        sword.setItemMeta(swordMeta);

        stuff.add(sword);
        stuff.add(new ItemStack(Material.SPONGE, 15));
        stuff.add(new ItemStack(Material.IRON_BOOTS));
        stuff.add(legs);
        stuff.add(chest);

        return stuff;
    }
}
