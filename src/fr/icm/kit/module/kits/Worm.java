package fr.icm.kit.module.kits;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Worm extends Kit {

    Tier[] tier = new Tier[1];

    public Worm() {
        super("worm", 33, false, false);

        Presentation p = new Presentation("§6» Kit Worm", Arrays.asList("J'aime la terre"), Material.DIAMOND);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();
        stuff.add(new ItemStack(Material.DIAMOND_SWORD));

        return stuff;
    }
}
