package fr.icm.kit.minecraft.gui;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.minecraft.gui.enumeration.ICMGuiType;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import fr.icm.kit.module.utils.KitLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ICMGuiKit {


    private KitLoader kitLoader = PvPBox.getKitLoader;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    public Inventory setGui(ICMGuiType guiType, ICMPlayer icmPlayer, int page)
    {
        String inventoryName = null;
        if (guiType == ICMGuiType.GUI_MAIN)
            inventoryName = "§CKit";
        else if (guiType == ICMGuiType.GUI_UNRESTRICTED)
            inventoryName = "§CKit non VIP";
        else if (guiType == ICMGuiType.GUI_VIP)
            inventoryName = "§CKit VIP";
        else if (guiType == ICMGuiType.GUI_ACCESSED)
            inventoryName = "§CKit Disponible";
        Inventory inventory = Bukkit.createInventory(null, 9*6, inventoryName);

        ItemStack kits = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta kitsMeta = kits.getItemMeta();
        kitsMeta.setDisplayName("Kit");
        if (guiType == ICMGuiType.GUI_MAIN) {
            kitsMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            kitsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); }
        kitsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kits.setItemMeta(kitsMeta);

        ItemStack kitUnrestricted = new ItemStack(Material.WOOD_SWORD);
        ItemMeta kitUnrestrictedMeta = kitUnrestricted.getItemMeta();
        kitUnrestrictedMeta.setDisplayName("Kit non VIP");
        if (guiType == ICMGuiType.GUI_UNRESTRICTED) {
            kitUnrestrictedMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            kitUnrestrictedMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); }
        kitUnrestrictedMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kitUnrestricted.setItemMeta(kitUnrestrictedMeta);

        ItemStack kitVIP = new ItemStack(Material.IRON_SWORD);
        ItemMeta kitVIPMeta = kitVIP.getItemMeta();
        kitVIPMeta.setDisplayName("Kit VIP");
        if (guiType == ICMGuiType.GUI_VIP) {
            kitVIPMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            kitVIPMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); }
        kitVIPMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kitVIP.setItemMeta(kitVIPMeta);

        ItemStack kitUnlocked = new ItemStack(Material.BOOK);
        ItemMeta kitUnlockedMeta = kitUnlocked.getItemMeta();
        kitUnlockedMeta.setDisplayName("Kit Disponible");
        if (guiType == ICMGuiType.GUI_ACCESSED) {
            kitUnlockedMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            kitUnlockedMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); }
        kitUnlockedMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kitUnlocked.setItemMeta(kitUnlockedMeta);

        ItemStack nothing = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 15);
        ItemMeta nothingMeta = nothing.getItemMeta();
        nothingMeta.setDisplayName(" ");
        nothing.setItemMeta(nothingMeta);

        inventory.setItem(0, nothing);
        inventory.setItem(1, nothing);
        inventory.setItem(2, kits);             //Pour ouvrir le gui principal de tout les kit
        inventory.setItem(3, kitUnrestricted);  //Pour ouvrir le gui des kit non vip
        inventory.setItem(4, nothing);
        inventory.setItem(5, kitVIP);           //Pour ouvrir le gui des kit vip
        inventory.setItem(6, kitUnlocked);      //Pour ouvrir le gui des kit unlocked
        inventory.setItem(7, nothing);
        inventory.setItem(8, nothing);
        inventory.setItem(9, nothing);
        inventory.setItem(10, nothing);
        inventory.setItem(11, nothing);
        inventory.setItem(12, nothing);
        inventory.setItem(13, nothing);
        inventory.setItem(14, nothing);
        inventory.setItem(15, nothing);
        inventory.setItem(16, nothing);
        inventory.setItem(17, nothing);
        inventory.setItem(18, nothing);
        inventory.setItem(26, nothing);
        inventory.setItem(27, nothing);
        inventory.setItem(35, nothing);
        inventory.setItem(36, nothing);
        inventory.setItem(44, nothing);
        inventory.setItem(46, nothing);
        inventory.setItem(47, nothing);
        inventory.setItem(48, nothing);
        inventory.setItem(49, nothing);
        inventory.setItem(50, nothing);
        inventory.setItem(51, nothing);
        inventory.setItem(52, nothing);
        ArrayList<Kit> kit = new ArrayList<>(kitLoader.getKit());

        int maxPage= 0;
        int size = 0;
        int max = 0;
        int min = 0;
        switch (guiType)
        {
            case GUI_MAIN:
                kit.removeIf(Kit::isSecret);
                 size = kit.size();
                maxPage = (int) Math.ceil((float) size / 21);
                 max = 21 * page;
                 min = 21 * (page - 1);

                break;
            case GUI_UNRESTRICTED:
                kit.removeIf(Kit::isVIP);
                kit.removeIf(Kit::isSecret);
                size = kit.size();
                maxPage = (int) Math.ceil((float) size / 21);
                 max = 21 * page;
                 min = 21 * (page - 1);
                break;
            case GUI_VIP:
                kit.removeIf(k -> !k.isVIP());
                kit.removeIf(Kit::isSecret);
                size = kit.size();
                maxPage = (int) Math.ceil((float) size / 21);
                max = 21 * page;
                min = 21 * (page - 1);
                break;
            case GUI_ACCESSED:
                if(icmPlayer.getKitAccess().isEmpty())
                    break;
                kit.removeIf(k -> !icmPlayer.getKitAccess().contains(k.getKitName().toLowerCase()));
                if(kit.isEmpty())
                    break;
                size = kit.size();
                maxPage = (int) Math.ceil((float) size / 21);
                max = 21 * page;
                min = 21 * (page - 1);
                break;

            default:
                playerLoader.getPlayerByAPI(icmPlayer).sendMessage("§cError 404");
                break;

        }

        for(int i = min; i<max; i++)
        {
            if(kit.size() <= i)
                break;

            Kit k = kit.get(i);
            int s = icmPlayer.getKitLevel(k.getKitName());
            Tier t = k.getKitTierByLevel(s);
            Presentation p = t.getPresentation();

            Player player = playerLoader.getPlayerByAPI(icmPlayer);
            if(player == null)
                break;

            ItemStack kit_item = new ItemStack(p.getIcon());

            if(!player.hasPermission("icm.vip") && !icmPlayer.getKitAccess().contains(k.getKitName()))
            {
                if(k.isVIP())
                {
                    kit_item = new ItemStack(Material.BARRIER);
                }
            }

            ItemMeta itemMeta = kit_item.getItemMeta();
            itemMeta.setLore(p.getDescription());
            itemMeta.setDisplayName(p.getName());
            kit_item.setItemMeta(itemMeta);



            inventory.addItem(kit_item);
        }

        ItemStack next = new ItemStack(Material.PAPER);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName("§bPage suivante (§a" + page + "§c/" + maxPage + "§a)");
        next.setItemMeta(nextMeta);

        ItemStack previous = new ItemStack(Material.PAPER);
        ItemMeta previousMeta = previous.getItemMeta();
        previousMeta.setDisplayName("§bPage précédente (§a" + page + "§c/" + maxPage + "§a)");
        previous.setItemMeta(previousMeta);

        if (page != 1)                      //Si on est page 1 alors on n'affiche pas "page précédente"
            inventory.setItem(45, previous);
        else
            inventory.setItem(45, nothing);

        if (page < maxPage)                 //Si on est à la dernière page alors on n'affiche pas "page suivante"
            inventory.setItem(53, next);
        else
            inventory.setItem(53, nothing);

        return inventory;
    }


}
