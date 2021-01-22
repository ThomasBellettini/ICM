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

public class Heracles extends Kit {

    Tier[] tier = new Tier[1];

    public Heracles() {
        super("heracles", 11, false, false);

        Presentation p = new Presentation("§cHéraclès", Arrays.asList("Je suis Héraclès..."), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack chest = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack helmet = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsColor = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta helmetColor = (LeatherArmorMeta) helmet.getItemMeta();
        bootsColor.setColor(Color.fromRGB(0,0,255));
        helmetColor.setColor(Color.fromRGB(0,0,255));
        boots.setItemMeta(bootsColor);
        helmet.setItemMeta(helmetColor);


        stuff.add(new ItemStack(Material.STONE_SWORD));

        return stuff;
    }
}
