package fr.icm.kit.minecraft.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.general.utils.ScoreboardSign;
import fr.icm.kit.minecraft.gui.ICMGuiKit;
import fr.icm.kit.minecraft.gui.enumeration.ICMGuiType;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import fr.icm.kit.module.utils.KitLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EventClickInventory implements Listener {

    private KitLoader kitLoader = PvPBox.getKitLoader;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    private ICMGuiKit guiKit = PvPBox.getGuiKit;

    @EventHandler
    public void onClickInsideKitInventory(InventoryClickEvent e)
    {
        Player user = (Player) e.getWhoClicked();
        ICMPlayer icmPlayerUser = playerLoader.getICMByPlayer(user);

        if (e.getCurrentItem() == null || e.getInventory() == null)
            return;
        if (!e.getInventory().getName().startsWith("§CKit"))
            return;
        e.setCancelled(true);
        if (icmPlayerUser.isCombatTag())
            return;
        ItemStack itemStack = e.getCurrentItem();
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName())
            return;
        Material material = itemStack.getType();
        String name = itemStack.getItemMeta().getDisplayName();

        if (material == Material.PAPER) {
            if (name.startsWith("§bPage suivante (§a")) {
                String replacement = name.substring(19, 20);
                int replacement_integer =  Integer.parseInt(replacement);
                try {
                    if (e.getInventory().getName().equalsIgnoreCase("§CKit"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_MAIN, icmPlayerUser,replacement_integer + 1));
                    else if (e.getInventory().getName().equalsIgnoreCase("§CKit non VIP"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_UNRESTRICTED, icmPlayerUser, replacement_integer + 1));
                    else if (e.getInventory().getName().equalsIgnoreCase("§CKIT VIP"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_VIP, icmPlayerUser, replacement_integer + 1));
                    else if (e.getInventory().getName().equalsIgnoreCase("§CKit Disponible"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_ACCESSED, icmPlayerUser, replacement_integer + 1));
                } catch (NumberFormatException exception) {
                    user.sendMessage("§cAn internal error occured will attempting to perform this connerie !");
                    return;
                }
            }
            else if (name.startsWith("§bPage précédente (§a")) {
                String replacement = name.substring(21, 22);
                int replacement_integer =  Integer.parseInt(replacement);
                try {
                    if (e.getInventory().getName().equalsIgnoreCase("§CKit"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_MAIN, icmPlayerUser, replacement_integer - 1));
                    else if (e.getInventory().getName().equalsIgnoreCase("§CKit non VIP"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_UNRESTRICTED, icmPlayerUser, replacement_integer - 1));
                    else if (e.getInventory().getName().equalsIgnoreCase("§CKIT VIP"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_VIP, icmPlayerUser, replacement_integer - 1));
                    else if (e.getInventory().getName().equalsIgnoreCase("§CKit Disponible"))
                        user.openInventory(guiKit.setGui(ICMGuiType.GUI_ACCESSED, icmPlayerUser, replacement_integer - 1));
                } catch (NumberFormatException exception) {
                    user.sendMessage("§cAn internal error occured will attempting to perform this connerie !");
                    return;
                }
            }
        }   //système de pagination
        if (material == Material.ENCHANTED_BOOK)
            if (name.equalsIgnoreCase("Kit"))
                user.openInventory(guiKit.setGui(ICMGuiType.GUI_MAIN, icmPlayerUser, 1));
        if (material == Material.WOOD_SWORD)
            if (name.equalsIgnoreCase("Kit non VIP"))
                user.openInventory(guiKit.setGui(ICMGuiType.GUI_UNRESTRICTED, icmPlayerUser, 1));
        if (material == Material.IRON_SWORD)
            if (name.equalsIgnoreCase("Kit VIP"))
                user.openInventory(guiKit.setGui(ICMGuiType.GUI_VIP, icmPlayerUser, 1));
        if (material == Material.BOOK)
            if (name.equalsIgnoreCase("Kit Disponible"))
                user.openInventory(guiKit.setGui(ICMGuiType.GUI_ACCESSED, icmPlayerUser, 1));
        if(!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore() || !itemStack.getItemMeta().hasDisplayName())
            return;

        List<String> lore = itemStack.getItemMeta().getLore();

        if(kitLoader.getKit().isEmpty())
            return;
        for (Kit kit : kitLoader.getKit()) {
            for (Tier tier : kit.getKitTier()) {
                Presentation presentation = tier.getPresentation();
                if (material == presentation.getIcon() && lore.equals(presentation.getDescription()) && name.equalsIgnoreCase(presentation.getName())) {
                    int level = icmPlayerUser.getKitLevel(kit.getKitName());
                    user.closeInventory();
                    user.getInventory().clear();
                    ItemStack clear = new ItemStack(Material.AIR);
                    user.getInventory().setBoots(clear);
                    user.getInventory().setLeggings(clear);
                    user.getInventory().setChestplate(clear);
                    user.getInventory().setHelmet(clear);

                    for (ItemStack item : kit.getKitTierByLevel(level).getComposition()) {
                        if (item.getType() == Material.LEATHER_BOOTS ||
                                item.getType() == Material.GOLD_BOOTS ||
                                item.getType() == Material.CHAINMAIL_BOOTS ||
                                item.getType() == Material.IRON_BOOTS ||
                                item.getType() == Material.DIAMOND_BOOTS) {
                            user.getInventory().setBoots(item);
                            continue;
                        }
                        if (item.getType() == Material.LEATHER_LEGGINGS ||
                                item.getType() == Material.GOLD_LEGGINGS ||
                                item.getType() == Material.CHAINMAIL_LEGGINGS ||
                                item.getType() == Material.IRON_LEGGINGS ||
                                item.getType() == Material.DIAMOND_LEGGINGS) {
                            user.getInventory().setLeggings(item);
                            continue;
                        }
                        if (item.getType() == Material.LEATHER_CHESTPLATE ||
                                item.getType() == Material.GOLD_CHESTPLATE ||
                                item.getType() == Material.CHAINMAIL_CHESTPLATE ||
                                item.getType() == Material.IRON_CHESTPLATE ||
                                item.getType() == Material.DIAMOND_CHESTPLATE) {
                            user.getInventory().setChestplate(item);
                            continue;
                        }
                        if (item.getType() == Material.LEATHER_HELMET ||
                                item.getType() == Material.GOLD_HELMET ||
                                item.getType() == Material.CHAINMAIL_HELMET ||
                                item.getType() == Material.IRON_HELMET ||
                                item.getType() == Material.DIAMOND_HELMET) {
                            user.getInventory().setHelmet(item);
                            continue;
                        }
                        user.getInventory().addItem(item);
                    }
                    icmPlayerUser.setFightingKit(kit);
                    icmPlayerUser.setFighting(true);

                    if (PvPBox.getPlayerLoader.getSignMap().containsKey(icmPlayerUser)) {
                        ScoreboardSign scoreboardSigns = PvPBox.getPlayerLoader.getSignMap().get(icmPlayerUser);
                        scoreboardSigns.setLine(5, "§6Kit » §e" + (icmPlayerUser.getFightingKit() != null ? icmPlayerUser.getFightingKit().getKitName() : "§cX"));
                    }
                    else {
                        ScoreboardSign scoreboardSign = new ScoreboardSign(user, "§6ICM Network");
                        scoreboardSign.create();
                        scoreboardSign.setLine(0, "§c ");
                        scoreboardSign.setLine(1, "§6Coin(s) » §e" + icmPlayerUser.getCoins());
                        scoreboardSign.setLine(2, "§6Kill(s) » §e" + icmPlayerUser.getKill());
                        scoreboardSign.setLine(3, "§6Mort(s) » §e" + icmPlayerUser.getDeath());
                        scoreboardSign.setLine(4, "§d ");
                        scoreboardSign.setLine(5, "§6Kit » §e" + (icmPlayerUser.getFightingKit() != null ? icmPlayerUser.getFightingKit().getKitName() : "§cX"));
                        scoreboardSign.setLine(6, "§6Cooldown » §e" + (icmPlayerUser.getCooldown() > 0 ? icmPlayerUser.getCooldown() + " seconde(s)" : "§cX"));
                        scoreboardSign.setLine(7, "§cCombat » " + (icmPlayerUser.isCombatTag() ? icmPlayerUser.getCombatTag() + " seconde(s)" : "§cX"));
                        scoreboardSign.setLine(8, "§7 ");
                        scoreboardSign.setLine(9, "§eplay.icm-network.net §7(§a" + PvPBox.getInstance.getServer().getOnlinePlayers().size() + "§7)");
                        PvPBox.getPlayerLoader.getSignMap().put(icmPlayerUser, scoreboardSign);
                    }
                    user.sendMessage("§6[!] §aVous venez de choisir le Kit " + kit.getKitName());
                    return;
                }
            }
        }
    }
}
