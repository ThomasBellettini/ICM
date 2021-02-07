package fr.icm.general.commands.admin.gui;

import fr.icm.entity.ICMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GuiAccount {

    public static Inventory GuiAccount(ICMPlayer pInfo) {

        Inventory inventory = Bukkit.createInventory(null, 9*3, "§6Account §7(§c" + pInfo.getName() + "§7)");

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sMeta = (SkullMeta) itemStack.getItemMeta();
        sMeta.setOwner(pInfo.getName());
        sMeta.setDisplayName("§6Account Name: §e" + pInfo.getName());
        itemStack.setItemMeta(sMeta);

        inventory.setItem(4, itemStack);
        return inventory;
    }
}
