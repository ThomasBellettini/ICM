package fr.icm.kit.module.kits;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Poseidon extends Kit {

    Tier[] tier = new Tier[1];

    public Poseidon() {
        super("poseidon", 21, false, false);

        Presentation p = new Presentation("§6» Kit Poséidon", Arrays.asList("Je suis le roi des mer et des océan"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);

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


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(new ItemStack(Material.WATER_BUCKET));
        stuff.add(new ItemStack(Material.WATER_BUCKET));
        stuff.add(new ItemStack(Material.WATER_BUCKET));
        stuff.add(new ItemStack(Material.WATER_BUCKET));
        stuff.add(new ItemStack(Material.WATER_BUCKET));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }
}
