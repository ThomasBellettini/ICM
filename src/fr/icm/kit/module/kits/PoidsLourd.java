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

public class PoidsLourd extends Kit {

    Tier[] tier = new Tier[1];

    public PoidsLourd() {
        super("poidslourd", 20, false, false);

        Presentation p = new Presentation("§cPoids Lourds", Arrays.asList("Je suis gros"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);


        stuff.add(new ItemStack(Material.IRON_SWORD));
        stuff.add(legs);
        stuff.add(chest);

        return stuff;
    }
}
