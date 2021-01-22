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

public class Paladin extends Kit {

    Tier[] tier = new Tier[1];

    public Paladin() {
        super("paladin", 18, false, false);

        Presentation p = new Presentation("§cPaladin", Arrays.asList("Je me bat pour l'inquisition"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(new ItemStack(Material.IRON_CHESTPLATE));
        stuff.add(helmet);

        return stuff;
    }
}
