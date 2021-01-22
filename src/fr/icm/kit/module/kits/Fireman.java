package fr.icm.kit.module.kits;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Fireman extends Kit {

    Tier[] tier = new Tier[1];

    public Fireman() {
        super("fireman", 8, false, false);

        Presentation p = new Presentation("§6» Kit FireMan", Arrays.asList(" "
                , " "
                , "     §eCapacitée »"
                , "     §aVous êtes insensible aux dêgats de feu !"
                , " "
                , "     §b[!] Ce Kit est Niveau 1"
                , "     §c[!] Vous devez être VIP pour utilisez ce Kit !"
                , " ")
                , Material.BLAZE_POWDER);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(0,0,0));
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);


        stuff.add(new ItemStack(Material.IRON_SWORD));
        stuff.add(new ItemStack(Material.LAVA_BUCKET));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);

        return stuff;
    }
}
