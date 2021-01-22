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

public class ElfKing extends Kit {

    Tier[] tier = new Tier[3];

    public ElfKing() {
        super("roielfique", 7, false, true);

        Presentation p = new Presentation("§6» Kit Roi Elfique", Arrays.asList(" "
                , " "
                , "     §eCapacitée »"
                , "     §aVous pouvez invoquez un cheval d'un simple clic !"
                , " "
                , "     §b[!] Ce Kit est Niveau 1"
                , "     §c[!] Vous devez être VIP pour utilisez ce Kit !"
                , " ")
                , Material.IRON_BARDING);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 10);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p,10);
        tier[2] = new Tier(3, 300, tierThree(), getKitName(), p, 10);

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
        helmetMeta.setColor(Color.fromRGB(255,255,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.DIAMOND_SWORD));
        //stuff.add(un cheval sans armure);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(new ItemStack(Material.ARROW, 64));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }

    private ArrayList<ItemStack> tierTwo() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack chest = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack helmet = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta chestColor = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta helmetColor = (LeatherArmorMeta) helmet.getItemMeta();
        chestColor.setColor(Color.fromRGB(255,255,0));
        helmetColor.setColor(Color.fromRGB(255,255,0));
        chest.setItemMeta(chestColor);
        helmet.setItemMeta(helmetColor);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        bow.setItemMeta(bowMeta);


        stuff.add(new ItemStack(Material.DIAMOND_SWORD));
        //stuff.add(un cheval armure en or);
        stuff.add(new ItemStack(Material.ARROW, 64));
        stuff.add(chest);
        stuff.add(helmet);
        stuff.add(bow);

        return stuff;
    }

    private ArrayList<ItemStack> tierThree() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack chest = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack helmet = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta chestColor = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta helmetColor = (LeatherArmorMeta) helmet.getItemMeta();
        chestColor.setColor(Color.fromRGB(255,255,0));
        helmetColor.setColor(Color.fromRGB(255,255,0));
        chest.setItemMeta(chestColor);
        helmet.setItemMeta(helmetColor);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
        bow.setItemMeta(bowMeta);


        stuff.add(new ItemStack(Material.DIAMOND_SWORD));
        //stuff.add(un cheval armure en fer);
        stuff.add(new ItemStack(Material.ARROW, 64));
        stuff.add(chest);
        stuff.add(helmet);
        stuff.add(bow);

        return stuff;
    }

}
