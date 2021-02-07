package fr.icm.zone.event;

import fr.icm.PvPBox;
import fr.icm.zone.api.ICMZone;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserEditZoneEvent implements Listener {

    @EventHandler
    public void userInteractInInventory(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null)
            return;
        if (e.getInventory().getName().equalsIgnoreCase("§6Editeur de zone !")) {
            e.setCancelled(true);
            ArrayList<ItemStack> itemStacks = new ArrayList<>(Arrays.asList(e.getInventory().getContents()));
            itemStacks.removeIf(Objects::isNull);
            itemStacks.removeIf(i -> i.getType() != Material.BOOK);
            ItemStack info = itemStacks.get(0);
            if (info.hasItemMeta() && info.getItemMeta().hasLore()) {
                List<String> lore = info.getItemMeta().getLore();
                lore.removeIf(l -> !l.startsWith(" §e» Nom de la Zone : §a"));
                String name = lore.get(0).replace(" §e» Nom de la Zone : §a", "");
                ICMZone icmZone = PvPBox.getZoneLoader.getZonePerName(name);
                if (icmZone == null) {
                    p.closeInventory();
                    return;
                }
                ItemStack negative = new ItemStack(Material.INK_SACK, 1, (short)1);
                ItemMeta meta = negative.getItemMeta();
                meta.setLore(Arrays.asList(" ", "     §eSi tu cliques sur cette item,", "     §etu §aactiveras§e la fonctionalité !", " "));

                ItemStack positive = new ItemStack(Material.INK_SACK, 1, (short)10);
                ItemMeta positive_meta = positive.getItemMeta();
                positive_meta.setLore(Arrays.asList(" ", "     §eSi tu cliques sur cette item,", "     §etu §cdésactiveras§e la fonctionalité !", " "));

                ItemStack item = e.getCurrentItem();
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getType() == Material.INK_SACK) {
                    String names = item.getItemMeta().getDisplayName();
                    String l = "NULL";
                    switch (item.getData().getData()) {
                        case 1:
                            switch (names.replace("§e", "")) {
                                case "PVP":
                                    icmZone.setCanPvp(true);
                                    l = "le PVP";
                                    break;
                                case "BUILD":
                                    icmZone.setCanBuild(true);
                                    l = "le Build";
                                    break;
                                case "DESTROY":
                                    icmZone.setCanDestroy(true);
                                    l = "la Destruction";
                                    break;
                                case "ENTITY DAMAGE":
                                    icmZone.setCanEntityDamage(true);
                                    l = "le dégâts d'entité";
                                    break;
                                case "INTERACT":
                                    icmZone.setCanInteract(true);
                                    l = "les interactions";
                                    break;
                                case "MOB GRIEFING":
                                    icmZone.setCanGrief(true);
                                    l = "le Griefing des mobs";
                                    break;
                                case "CAN USE KIT POWER":
                                    icmZone.setCanUsePower(true);
                                    l = "l'utilisation des pouvoirs";
                                    break;
                                case "MOB SPAWNING":
                                    icmZone.setCanMobSpawn(true);
                                    l = "le Spawn des mobs";
                                    break;
                                case "SEE OTHER PLAYER":
                                    icmZone.setCanViewOtherPlayer(true);
                                    l = "le faites de voir les autres joueurs";
                                    break;
                                case "INVINCIBLE":
                                    icmZone.setInvicible(true);
                                    l = "l'invincibilité";
                                    break;
                                case "FOODLESS":
                                    icmZone.setFoodLess(true);
                                    l = "le faites de ne plus perdre de nourriture";
                                    break;
                                case "ENTITY INTERACT":
                                    icmZone.setCanInteractAtEntity(true);
                                    l = "l'interaction sur les entités";
                                    break;
                                case "TELEPORT":
                                    icmZone.setCanTeleport(true);
                                    l = "les téléportations";
                                    break;
                            }
                            String mdrs = "§7[§6ICM§7] Vous venez d'§aactiver§7 %m% §7dans la zone §e" + icmZone.getName();
                            p.sendMessage(mdrs.replace("%m%", l));
                            positive_meta.setDisplayName(item.getItemMeta().getDisplayName());
                            positive.setItemMeta(positive_meta);
                            e.getInventory().setItem(e.getRawSlot(), positive);
                            icmZone.save();
                            return;
                        case 10:
                            switch (names.replace("§e", "")) {
                                case "PVP":
                                    icmZone.setCanPvp(false);
                                    l = "le PVP";
                                    break;
                                case "BUILD":
                                    icmZone.setCanBuild(false);
                                    l = "le Build";
                                    break;
                                case "DESTROY":
                                    icmZone.setCanDestroy(false);
                                    l = "la Destruction";
                                    break;
                                case "ENTITY DAMAGE":
                                    icmZone.setCanEntityDamage(false);
                                    l = "le dégâts d'entité";
                                    break;
                                case "INTERACT":
                                    icmZone.setCanInteract(false);
                                    l = "les interactions";
                                    break;
                                case "MOB GRIEFING":
                                    icmZone.setCanGrief(false);
                                    l = "le Griefing des mobs";
                                    break;
                                case "CAN USE KIT POWER":
                                    icmZone.setCanUsePower(false);
                                    l = "l'utilisation des pouvoirs";
                                    break;
                                case "MOB SPAWNING":
                                    icmZone.setCanMobSpawn(false);
                                    l = "le Spawn des mobs";
                                    break;
                                case "SEE OTHER PLAYER":
                                    icmZone.setCanViewOtherPlayer(false);
                                    l = "le faites de voir les autres joueurs";
                                    break;
                                case "INVINCIBLE":
                                    icmZone.setInvicible(false);
                                    l = "l'invincibilité";
                                    break;
                                case "FOODLESS":
                                    icmZone.setFoodLess(false);
                                    l = "le faites de ne plus perdre de nourriture";
                                    break;
                                case "ENTITY INTERACT":
                                    icmZone.setCanInteractAtEntity(false);
                                    l = "l'interaction sur les entités";
                                    break;
                                case "TELEPORT":
                                    icmZone.setCanTeleport(false);
                                    l = "les téléportations";
                                    break;
                            }
                            meta.setDisplayName(item.getItemMeta().getDisplayName());
                            negative.setItemMeta(meta);
                            e.getInventory().setItem(e.getRawSlot(), negative);
                            String mdr = "§7[§6ICM§7] Vous venez de §cdésactiver§7 %m% §7dans la zone §e" + icmZone.getName();
                            p.sendMessage(mdr .replace("%m%", l));
                            icmZone.save();
                            return;
                        default:
                            p.sendMessage("§cError 505");
                            break;
                    }
                }
            }
        }
    }
}
