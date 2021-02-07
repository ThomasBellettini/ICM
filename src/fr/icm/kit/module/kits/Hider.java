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

public class Hider extends Kit {

    Tier[] tier = new Tier[3];

    public Hider() {
        super("hider", 12, false, false);

        Presentation p = new Presentation("§6» Kit Hider", Arrays.asList("J'aime me cacher"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);
        tier[1] = new Tier(2, 100, tierTwo(), getKitName(), p, 0);
        tier[2] = new Tier(3, 300, tierThree(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack chest = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack helmet = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta helmetColor = (LeatherArmorMeta) helmet.getItemMeta();
        helmetColor.setColor(Color.fromRGB(255,0,0));
        helmet.setItemMeta(helmetColor);

        ItemStack changeForme = new ItemStack(Material.REDSTONE_TORCH_OFF);
        ItemMeta changeFormeMeta = changeForme.getItemMeta();
        changeFormeMeta.setDisplayName("Matérialisation");
        changeForme.setItemMeta(changeFormeMeta);


        stuff.add(new ItemStack(Material.WOOD_SWORD));
        stuff.add(changeForme);
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }

    private ArrayList<ItemStack> tierTwo() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack chest = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack helmet = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta helmetColor = (LeatherArmorMeta) helmet.getItemMeta();
        helmetColor.setColor(Color.fromRGB(255,0,0));
        helmet.setItemMeta(helmetColor);

        ItemStack changeForme = new ItemStack(Material.REDSTONE_TORCH_OFF);
        ItemMeta changeFormeMeta = changeForme.getItemMeta();
        changeFormeMeta.setDisplayName("Matérialisation");
        changeForme.setItemMeta(changeFormeMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(changeForme);
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }

    private ArrayList<ItemStack> tierThree() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack chest = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack helmet = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta helmetColor = (LeatherArmorMeta) helmet.getItemMeta();
        helmetColor.setColor(Color.fromRGB(255,0,0));
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        helmet.setItemMeta(helmetColor);

        ItemStack changeForme = new ItemStack(Material.REDSTONE_TORCH_OFF);
        ItemMeta changeFormeMeta = changeForme.getItemMeta();
        changeFormeMeta.setDisplayName("Matérialisation");
        changeForme.setItemMeta(changeFormeMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(changeForme);
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }
}
