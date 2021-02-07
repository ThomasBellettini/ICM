package fr.icm.kit.minecraft.command.admin;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PartyLoader;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.minecraft.gui.enumeration.ICMGuiType;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import fr.icm.kit.module.utils.KitLoader;
import fr.icm.rank.module.utils.RankEnum;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminKitCommand implements CommandExecutor, Listener {

    private KitLoader kitLoader = PvPBox.getKitLoader;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    private PartyLoader partyLoader = PvPBox.getPartyLoader;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(p);
            if (icmPlayer == null)
                return false;
            if (icmPlayer.getRank() != RankEnum.ADMINISTRATOR) {
                p.sendMessage("§7[§c!§7]§c Erreur, vous n'avez pas les priviléges nécessaires ! §7(§4<ADMINISTRATOR§7)");
                return false;
            }
            if (args.length != 1) {
                p.sendMessage("§7[§c!§7]§c Erreur, mauvaise utilisation, §a/akit <player>");
                return false;
            }
            ICMPlayer icmVictim = PvPBox.getPlayerLoader.getICMByName(args[0]);
            if (icmVictim == null) {
                p.sendMessage("§7[§c!§7]§c Erreur, le joueur n'a pas était trouvé dans la base de données !");
                return false;
            }
            if (args[0].equalsIgnoreCase(icmVictim.getName())) {
                p.openInventory(AdminKitInventory(icmVictim, 1, icmVictim.getName()));
                return true;
            }
        }
        else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("kitlist")) {
                    sender.sendMessage("§e§m*---------------------*");
                    sender.sendMessage(" ");
                    sender.sendMessage("§6 - Liste des kit Load");
                    sender.sendMessage(" ");
                    if (!PvPBox.getKitLoader.getKit().isEmpty())
                        for (Kit k : PvPBox.getKitLoader.getKit())
                            sender.sendMessage("   §6- " + k.getKitName());
                    else
                        sender.sendMessage("§c / Aucun kit n'est load !");
                    sender.sendMessage(" ");
                    sender.sendMessage("§e§m*---------------------*");
                    return true;
                } else {
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : akit <player> <list:add:remove> <kitName>");
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : akit kitlist");
                    return false;
                }
            }
            else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("list")) {
                    ICMPlayer icmPlayer = playerLoader.getICMByName(args[0]);
                    if (icmPlayer == null) {
                        sender.sendMessage("§7[§c!§7] §cAucun Profil n'a ete trouve !");
                        return false;
                    }
                    ArrayList<String> kitAccess = new ArrayList<>(icmPlayer.getKitAccess());
                    if (kitAccess.isEmpty()) {
                        sender.sendMessage("§7[§c!§7] §cCe compte ne possede aucun Kit !");
                        return true;
                    } else {
                        sender.sendMessage("§e§m*---------------------*");
                        sender.sendMessage(" ");
                        sender.sendMessage("§6 - Liste des kit appartenant a " + icmPlayer.getName());
                        sender.sendMessage(" ");
                        for (String k : kitAccess)
                            sender.sendMessage("   §6- " + k);
                        sender.sendMessage(" ");
                        sender.sendMessage("§e§m*---------------------*");
                    }
                } else {
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : akit <player> <list:add:remove> <kitName>");
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : akit kitlist");
                    return false;
                }
            }
            else if (args.length == 3) {
                ICMPlayer icmPlayer = playerLoader.getICMByName(args[0]);
                if (icmPlayer == null) {
                    sender.sendMessage("§7[§c!§7] §cAucun Profil n'a ete trouve !");
                    return false;
                }
                KitLoader kitLoader = PvPBox.getKitLoader;
                Kit k = kitLoader.getKitPerName(args[2]);
                if (k == null) {
                    sender.sendMessage("§7[§c!§7] §cAucun kit ne porte ce nom ! §eSyntaxe : akit kitlist");
                    return false;
                }
                switch (args[1].toLowerCase()) {
                    case "add":
                        if (!icmPlayer.getKitAccess().contains(k.getKitName())) {
                            icmPlayer.getKitAccess().add(k.getKitName());
                            sender.sendMessage("§7[§a!§7] §eVous venez de donner l'acces au kit §6" + k.getKitName() + "§e a §a" + icmPlayer.getName());
                            if (playerLoader.getPlayerByAPI(icmPlayer) != null)
                                playerLoader.getPlayerByAPI(icmPlayer).sendMessage("§7[§cConsole§7] §aVous venez de reçevoir de manière permanente le kit §a" + k.getKitName());
                        }
                        else
                            sender.sendMessage("§7[§c!§7] §cCe joueur possede deja ce kit !");
                        return true;
                    case "remove":
                        if (icmPlayer.getKitAccess().contains(k.getKitName())) {
                            icmPlayer.getKitAccess().remove(k.getKitName());
                            sender.sendMessage("§7[§a!§7] §eVous venez de supprimer le kit §6" + k.getKitName() + "§e au joueur §c" + icmPlayer.getName() + "§e !");
                            if (playerLoader.getPlayerByAPI(icmPlayer) != null)
                                playerLoader.getPlayerByAPI(icmPlayer).sendMessage("§7[§cConsole§7] §cVous venez de perdre l'accès au kit §a" + k.getKitName());
                        }
                        else
                            sender.sendMessage("§7[§c!§7] §cErreur, ce joueur ne possède pas ce kit ! §eSyntaxe : akit <player> list");
                        return true;
                    default:
                        sender.sendMessage("§7[§c!§7] §eSyntaxe : akit <player> <list:add:remove> <kitName>");
                        sender.sendMessage("§7[§c!§7] §eSyntaxe : akit kitlist");
                        return false;
                }
            }
            else {
                sender.sendMessage("§7[§c!§7] §eSyntaxe : akit <player> <list:add:remove> <kitName>");
                sender.sendMessage("§7[§c!§7] §eSyntaxe : akit kitlist");
                return false;
            }
        }
        return false;
    }

    @EventHandler
    public void whenUserEditKitAccess(InventoryClickEvent e) {
        Player user = (Player) e.getWhoClicked();
        ICMPlayer icmPlayerUser = playerLoader.getICMByPlayer(user);
        ItemStack item = e.getCurrentItem();

        if (item == null || e.getInventory() == null)
            return;
        if (!e.getInventory().getName().startsWith("§cÉditeur de kit"))
            return;
        e.setCancelled(true);

        ItemStack itemStack = e.getCurrentItem();
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName())
            return;
        Material material = itemStack.getType();
        String name = itemStack.getItemMeta().getDisplayName();
        ICMPlayer icmVictim = playerLoader.getICMByName(e.getInventory().getName().substring(20));

        if (material == Material.PAPER) {
            if (name.startsWith("§bPage suivante (§a")) {
                String replacement = name.substring(19, 20);
                int replacement_integer =  Integer.parseInt(replacement);
                try {
                    if (e.getInventory().getName().startsWith("§cÉditeur de kit"))
                        user.openInventory(AdminKitInventory(icmPlayerUser, replacement_integer + 1, icmVictim.getName()));
                } catch (NumberFormatException exception) {
                    user.sendMessage("§cAn internal error occured will attempting to perform this connerie !");
                }
            }
            else if (name.startsWith("§bPage précédente (§a")) {
                String replacement = name.substring(21, 22);
                int replacement_integer =  Integer.parseInt(replacement);
                try {
                    if (e.getInventory().getName().startsWith("§cÉditeur de kit"))
                        user.openInventory(AdminKitInventory(icmPlayerUser, replacement_integer - 1, icmVictim.getName()));
                } catch (NumberFormatException exception) {
                    user.sendMessage("§cAn internal error occured will attempting to perform this connerie !");
                }
            }
        }   //système de pagination

        if (material == Material.INK_SACK) {
            name = name.replace("§6» Kit ", "").replace(" ", "");
            name = name.toLowerCase();
            name = name.replace("è", "e").replace("é", "e").replace("î", "i");
            Kit k = kitLoader.getKitPerName(name);

            ItemStack add = new ItemStack(Material.INK_SACK, 1, (short) 10);
            ItemMeta addMeta = add.getItemMeta();
            addMeta.setDisplayName(item.getItemMeta().getDisplayName());
            add.setItemMeta(addMeta);

            ItemStack remove = new ItemStack(Material.INK_SACK, 1, (short) 1);
            ItemMeta removeMeta = remove.getItemMeta();
            removeMeta.setDisplayName(item.getItemMeta().getDisplayName());
            remove.setItemMeta(removeMeta);

            if (icmVictim.getKitAccess().contains(k.getKitName())) {
                icmVictim.getKitAccess().remove(k.getKitName());
                e.getInventory().setItem(e.getRawSlot(), remove);
            }
            else if (!icmVictim.getKitAccess().contains(k.getKitName())) {
                icmVictim.getKitAccess().add(k.getKitName());
                e.getInventory().setItem(e.getRawSlot(), add);
            }
        }
    }

    private Inventory AdminKitInventory(ICMPlayer icmPlayer, int page, String pname)
    {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§cÉditeur de kit de " + pname);

        ItemStack nothing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta nothingMeta = nothing.getItemMeta();
        nothingMeta.setDisplayName(" ");
        nothing.setItemMeta(nothingMeta);

        inventory.setItem(0, nothing);
        inventory.setItem(1, nothing);
        inventory.setItem(2, nothing);
        inventory.setItem(3, nothing);
        inventory.setItem(4, nothing);
        inventory.setItem(5, nothing);
        inventory.setItem(6, nothing);
        inventory.setItem(7, nothing);
        inventory.setItem(8, nothing);
        inventory.setItem(9, nothing);
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
        int size = kit.size();
        int maxPage = (int) Math.ceil((float) size / 28);
        int max = 28 * page;
        int min = 28 * (page - 1);

        for (int i = min; i < max; i++) {
            if (kit.size() <= i)
                break;
            Kit k = kit.get(i);
            int s = icmPlayer.getKitLevel(k.getKitName());
            Tier t = k.getKitTierByLevel(s);
            Presentation p = t.getPresentation();
            ItemStack kit_item = new ItemStack(p.getIcon());

            if (!icmPlayer.getKitAccess().contains(k.getKitName()))
                kit_item = new ItemStack(Material.INK_SACK, 1, (short) 1);
            if (icmPlayer.getKitAccess().contains(k.getKitName()))
                kit_item = new ItemStack(Material.INK_SACK, 1, (short) 10);

            ItemMeta itemMeta = kit_item.getItemMeta();
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