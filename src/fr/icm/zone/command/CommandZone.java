package fr.icm.zone.command;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.rank.module.utils.RankEnum;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandZone implements CommandExecutor {

    ZoneLoader zoneLoader = PvPBox.getZoneLoader;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player)
        {
            Player p = (Player)sender;
            ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(p);
            if(icmPlayer == null)
                return true;
            if(icmPlayer.getRank() != RankEnum.ADMINISTRATOR)
            {
                p.sendMessage("§7[§c!§7]§c Erreur, vous n'avez pas les privileges necessaires ! §7(§4<ADMINISTRATOR§7)");
                return false;
            }
            ArrayList<ICMZone> icmZones = new ArrayList<>(zoneLoader.getIcmZones());
            if(args.length == 2)
            {
                String zoneName = args[1];
                switch (args[0])
                {
                    case "create":
                        if(!icmZones.isEmpty())
                        {
                            for(ICMZone icm : icmZones)
                            {
                                if(icm.getName().equalsIgnoreCase(zoneName))
                                    {
                                        p.sendMessage("§7[§c!§7] §cErreur, il n'existe déjà une zone avec ce nom !");
                                        return true;
                                    }
                            }
                        }
                        if(icmPlayer.getZone_create() == null)
                        {
                            icmPlayer.setZone_create(new ICMZone(zoneName));
                            p.sendMessage("§7[§6ICM§7] §aVous venez de lancer la création d'une zone, merci de toucher deux blocs pour définir les limites de la zone !");
                        }
                        else
                            p.sendMessage("§7[§c!§7] §cVous êtes déjà en train de crée une zone !");


                        break;
                    case "edit": case "modifier":
                        if(icmZones.isEmpty())
                        {
                            p.sendMessage("§7[§c!§7] §cErreur, il n'existe aucune zone load sur le serveur !");
                            return true;
                        }
                        icmZones.removeIf((ICMZone zone) -> !zone.getName().equalsIgnoreCase(zoneName));
                        if(icmZones.isEmpty())
                        {
                            p.sendMessage("§7[§c!§7] §cErreur, aucune zone ne porte ce nom !");
                            return true;
                        }
                        ICMZone zone = icmZones.get(0);
                        p.openInventory(getZoneInventory(zone));
                        break;
                    case "remove" : case "delete": case "supprimer":
                        if(icmZones.isEmpty())
                        {
                            p.sendMessage("§7[§c!§7] §cErreur, il n'existe aucune zone load sur le serveur !");
                            return true;
                        }
                        icmZones.removeIf((ICMZone zones) -> !zones.getName().equalsIgnoreCase(zoneName));
                        if(icmZones.isEmpty())
                        {
                            p.sendMessage("§7[§c!§7] §cErreur, aucune zone ne porte ce nom !");
                            return true;
                        }
                        ICMZone zones = icmZones.get(0);
                        p.openInventory(getConfirmRemoveInventory(zones));
                        break;
                }
            }
        }


        return false;
    }



    private Inventory getConfirmRemoveInventory(ICMZone icmZone)
    {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "§7(§c" + icmZone.getName() + "§7)");

        ItemStack nothing = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 15);
        ItemMeta nothingMeta = nothing.getItemMeta();
        nothingMeta.setDisplayName(" ");
        nothing.setItemMeta(nothingMeta);

        ItemStack confirm = new ItemStack(Material.WOOL, 1, (short)13);
        ItemMeta confirm_meta = confirm.getItemMeta();
        confirm_meta.setDisplayName("§aConfirmer la Suppression");
        confirm_meta.setLore(Arrays.asList(" ", "     §c/!\\ Attention§e, si vous supprimez la zone,", "     §e      Les données ne seront §4plus recupérable§e !", " ", "           §7Zone concernée : " + icmZone.getName().substring(0, 1).toUpperCase() + icmZone.getName().substring(1), " "));
        confirm.setItemMeta(confirm_meta);

        ItemStack cancel = new ItemStack(Material.WOOL, 1, (short)14);
        ItemMeta cencel_meta = cancel.getItemMeta();
        cencel_meta.setDisplayName("§cAnnuler la Suppression");
        cencel_meta.setLore(Arrays.asList(" ", "     §eSi vous annulez la suppression de zone,", "     §eAucune données ne sera modifiée !", " "));
        cancel.setItemMeta(cencel_meta);


        inventory.setItem(0, nothing);
        inventory.setItem(1, confirm);

        inventory.setItem(2, nothing);

        inventory.setItem(3, cancel);
        inventory.setItem(4, nothing);
        return inventory;
    }

    private Inventory getZoneInventory(ICMZone z)
    {
        Inventory inventory = Bukkit.createInventory(null, 9*6, "§6Editeur de Zone !");

        ItemStack nothing = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 15);
        ItemMeta nothingMeta = nothing.getItemMeta();
        nothingMeta.setDisplayName(" ");
        nothing.setItemMeta(nothingMeta);

        ItemStack book = new ItemStack(Material.BOOK,1);
        ItemMeta bookMeta = book.getItemMeta();
        bookMeta.setDisplayName("§6§oEditeur de Zone !");
        bookMeta.setLore(Arrays.asList(" ", " §e» Nom de la Zone : §a" + z.getName(), "§e» ID de la Zone : §a" + z.getUUID(), " ", "§e» Location Min : " + z.getxMin() + "x " + z.getyMin() + "y " + z.getzMin() + "z",
                "§e» Location Max : " + z.getxMax() + "x " + z.getyMax() + "y " + z.getzMax() + "z" ));
        book.setItemMeta(bookMeta);

        inventory.setItem(0, nothing);
        inventory.setItem(1, nothing);
        inventory.setItem(2, nothing);
        inventory.setItem(3, nothing);
        inventory.setItem(4, book);
        inventory.setItem(5, nothing);
        inventory.setItem(6, nothing);
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
        inventory.setItem(45, nothing);
        inventory.setItem(53, nothing);


        ItemStack negative = new ItemStack(Material.INK_SACK, 1, (short)1);
        ItemMeta meta = negative.getItemMeta();
        meta.setLore(Arrays.asList(" ", "     §eSi tu cliques sur cette item,", "     §etu §aactiveras§e la fonctionalité !", " "));


        ItemStack positive = new ItemStack(Material.INK_SACK, 1, (short)10);
        ItemMeta positive_meta = positive.getItemMeta();
        positive_meta.setLore(Arrays.asList(" ", "     §eSi tu cliques sur cette item,", "     §etu §cdésactiveras§e la fonctionalité !", " "));

        if(z.isCanPvp())
        {
            positive_meta.setDisplayName("§ePVP");
            positive.setItemMeta(positive_meta);
            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§ePVP");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanBuild())
        {
            positive_meta.setDisplayName("§eBUILD");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eBUILD");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanDestroy())
        {
            positive_meta.setDisplayName("§eDESTROY");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eDESTROY");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanEntityDamage())
        {
            positive_meta.setDisplayName("§eENTITY DAMAGE");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eENTITY DAMAGE");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanInteract())
        {
            positive_meta.setDisplayName("§eINTERACT");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eINTERACT");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanGrief())
        {
            positive_meta.setDisplayName("§eMOB GRIEFING");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eMOB GRIEFING");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanUsePower())
        {
            positive_meta.setDisplayName("§eCAN USE KIT POWER");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eCAN USE KIT POWER");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanMobSpawn())
        {
            positive_meta.setDisplayName("§eMOB SPAWN");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eMOB SPAWN");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanViewOtherPlayer())
        {
            positive_meta.setDisplayName("§eSEE OTHER PLAYER");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eSEE OTHER PLAYER");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isInvicible())
        {
            positive_meta.setDisplayName("§eINVINCIBLE");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eINVINCIBLE");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isFoodLess())
        {
            positive_meta.setDisplayName("§eFOODLESS");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eFOODLESS");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }

        if(z.isCanInteractAtEntity())
        {
            positive_meta.setDisplayName("§eENTITY INTERACT");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eENTITY INTERACT");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }
        if(z.isCanTeleport())
        {
            positive_meta.setDisplayName("§eTELEPORT");
            positive.setItemMeta(positive_meta);

            inventory.addItem(positive);
        }
        else
        {
            meta.setDisplayName("§eTELEPORT");
            negative.setItemMeta(meta);
            inventory.addItem(negative);
        }


        return inventory;
    }

}
