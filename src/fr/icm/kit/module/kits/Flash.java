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

public class Flash extends Kit {

    Tier[] tier = new Tier[1];

    public Flash() {
        super("flash", 9, false, false);

        Presentation p = new Presentation("§6» Kit Flash", Arrays.asList(" "
                , " "
                , "     §eCapacitée »"
                , "     §aVous pouvez allez très vite ?!"
                , " "
                , "     §b[!] Ce Kit est Niveau 1"
                , "     §c[!] Vous devez être VIP pour utilisez ce Kit !"
                , " ")
                , Material.REDSTONE_TORCH_ON);
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
        chestMeta.setColor(Color.fromRGB(255,255,0));
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(new ItemStack(Material.REDSTONE_TORCH_OFF));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }
}
