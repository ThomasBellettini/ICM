package fr.icm.kit.minecraft.command.admin;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PartyLoader;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.module.api.Kit;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AdminKitCommand implements CommandExecutor, Listener {

    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    private PartyLoader partyLoader = PvPBox.getPartyLoader;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(p);
            if (icmPlayer == null)
                return false;
            if(icmPlayer.getRank() != RankEnum.ADMINISTRATOR)
            {
                p.sendMessage("§7[§c!§7]§c Erreur, vous n'avez pas les priviléges nécessaires ! §7(§4<ADMINISTRATOR§7)");
                return false;
            }
            if (args.length == 0)
            {
                p.sendMessage("§7[§c!§7]§c Erreur, mauvaise utilisation, §a/akit <player> <add/remove>");
            }
            else if (args.length == 1)
            {
                ICMPlayer icmVictim = PvPBox.getPlayerLoader.getICMByName(args[0]);
                if (icmVictim == null)
                {
                    p.sendMessage("§7[§c!§7]§c Erreur, le joueur n'a pas était trouvé dans la base de données !");
                    return false;
                }
                if (args[0].equalsIgnoreCase(icmVictim.getName()))
                {
                    p.openInventory(removeOrAddKitInventory(icmVictim));
                }
            }
            else
            {
                p.sendMessage("§7[§c!§7]§c Erreur, mauvaise utilisation, §a/akit <player> <add/remove>");
            }


        }
        else
        {
            if(args.length == 2)
            {
                if(args[1].equalsIgnoreCase("list"))
                {
                   ICMPlayer icmPlayer = playerLoader.getICMByName(args[0]);
                    if(icmPlayer == null)
                    {
                        sender.sendMessage("§7[§c!§7] §cAucun Profil n'a été trouvé !");
                        return false;
                    }
                    ArrayList<String> kitAccess = new ArrayList<>(icmPlayer.getKitAccess());
                    if(kitAccess.isEmpty())
                    {
                        sender.sendMessage("§7[§c!§7] §cCe compte ne possède aucun Kit à lui !");
                        return true;
                    }
                    else
                    {
                        sender.sendMessage("§e§m-*-------------------*-");
                        sender.sendMessage(" ");
                        sender.sendMessage("§6 - Liste des kit appartenant à " + icmPlayer.getName());
                        sender.sendMessage(" ");
                        for(String k : kitAccess)
                        {
                            sender.sendMessage("   §6- " + k);
                        }
                        sender.sendMessage(" ");
                        sender.sendMessage("§e§m-*-------------------*-");
                    }
                }
                else
                {
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit <player> <list:add:remove> <kitName>");
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit kitlist");
                    return false;
                }
            }
            else if(args.length == 3)
            {
                ICMPlayer icmPlayer = playerLoader.getICMByName(args[0]);
                if(icmPlayer == null)
                {
                    sender.sendMessage("§7[§c!§7] §cAucun Profil n'a été trouvé !");
                    return false;
                }
                KitLoader kitLoader = PvPBox.getKitLoader;
                Kit k = kitLoader.getKitPerName(args[2]);
                if(k == null)
                {
                    sender.sendMessage("§7[§c!§7] §cAucun kit ne porte ce nom ! §eSyntaxe : /akit <kitlist>");
                    return false;
                }
                switch (args[1].toLowerCase())
                {
                    case "add":
                        if(!icmPlayer.getKitAccess().isEmpty())
                        {
                            if(icmPlayer.getKitAccess().contains(k.getKitName()))
                            {
                                sender.sendMessage("§7[§c!§7] §cCe joueur possède déjà ce kit !");
                                return true;
                            }
                            icmPlayer.getKitAccess().add(k.getKitName());
                            sender.sendMessage("§7[§a!§7] §eVous venez de donner l'accès au kit §6" + k.getKitName() + "§e à §a" + icmPlayer.getName());
                            if(playerLoader.getPlayerByAPI(icmPlayer) != null)
                            {
                                playerLoader.getPlayerByAPI(icmPlayer).sendMessage("§7[§cConsole§7] §aVous venez de reçevoir de manière permanente le kit §a" + k.getKitName());
                            }
                            return true;
                        }
                        else
                        {
                            icmPlayer.getKitAccess().add(k.getKitName());
                            sender.sendMessage("§7[§a!§7] §eVous venez de donner l'accès au kit §6" + k.getKitName() + "§e à §a" + icmPlayer.getName());
                            if(playerLoader.getPlayerByAPI(icmPlayer) != null)
                            {
                                playerLoader.getPlayerByAPI(icmPlayer).sendMessage("§7[§cConsole§7] §aVous venez de reçevoir de manière permanente le kit §a" + k.getKitName());
                            }
                            return true;
                        }
                    case "remove":
                        if(!icmPlayer.getKitAccess().isEmpty())
                        {
                            if(icmPlayer.getKitAccess().contains(k.getKitName()))
                            {
                                icmPlayer.getKitAccess().remove(k.getKitName());
                                sender.sendMessage("§7[§a!§7] §eVous venez de supprimer le kit §6" + k.getKitName() + "§e au joueur §c" + icmPlayer.getName() + "§e !");
                                if(playerLoader.getPlayerByAPI(icmPlayer) != null)
                                {
                                    playerLoader.getPlayerByAPI(icmPlayer).sendMessage("§7[§cConsole§7] §cVous venez de perdre l'accès au kit §a" + k.getKitName());
                                }
                                return true;
                            }
                            else
                            {
                                sender.sendMessage("§7[§c!§7] §cErreur, ce joueur ne possède pas ce kit ! §eSyntaxe : /akit <player> list");
                                return true;
                            }
                        }
                        else
                        {
                            sender.sendMessage("§7[§c!§7] §cErreur, ce joueur ne possède pas de kit Personnel ! ");
                            return true;
                        }
                    default:
                        sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit <player> <list:add:remove> <kitName>");
                        sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit kitlist");
                        return false;
                    }
            }
            else if(args.length == 1)
            {
                if(args[0].equalsIgnoreCase("kitlist"))
                {
                    sender.sendMessage("§e§m-*-------------------*-");
                    sender.sendMessage(" ");
                    sender.sendMessage("§6 - Liste des kit Load");
                    sender.sendMessage(" ");
                    if(!PvPBox.getKitLoader.getKit().isEmpty())
                        for(Kit k : PvPBox.getKitLoader.getKit())
                        {
                            sender.sendMessage("   §6- " + k.getKitName());
                        }
                    else
                        sender.sendMessage("§c / Aucun kit n'est load !");
                    sender.sendMessage(" ");
                    sender.sendMessage("§e§m-*-------------------*-");
                }
                else
                {
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit <player> <list:add:remove> <kitName>");
                    sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit kitlist");
                    return false;
                }
            }
            else
            {
                sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit <player> <list:add:remove> <kitName>");
                sender.sendMessage("§7[§c!§7] §eSyntaxe : /akit kitlist");
                return false;
            }

        }



        return false;
    }

    @EventHandler
    public void whenUserEditKitAccess(InventoryClickEvent e)
    {
        Player user = (Player) e.getWhoClicked();
        ICMPlayer icmPlayerUser = playerLoader.getICMByPlayer(user);
        ICMPlayer icmVictim;

        if(e.getCurrentItem() == null || e.getInventory() == null)
            return;
        if (!e.getInventory().getName().startsWith("§cEditeur de kit"))
            return;

        e.setCancelled(true);

        if(icmPlayerUser.isCombatTag())
            return;

        ItemStack itemStack = e.getCurrentItem();

        if (!itemStack.getItemMeta().hasDisplayName())
            return;

        Material material = itemStack.getType();
        String name = itemStack.getItemMeta().getDisplayName();




    }

    private Inventory removeOrAddKitInventory(ICMPlayer icmPlayer)
    {
        Inventory inventory = Bukkit.createInventory(null, 9*6, "§cEditeur de kit");

        ItemStack nothing = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 15);
        ItemMeta nothingMeta = nothing.getItemMeta();
        nothingMeta.setDisplayName(" ");
        nothing.setItemMeta(nothingMeta);

        ItemStack add = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta addMeta = add.getItemMeta();
        addMeta.setDisplayName("Ajouter un kit");
        add.setItemMeta(addMeta);

        ItemStack remove = new ItemStack(Material.INK_SACK, 1, (short) 1);
        ItemMeta removeMeta = remove.getItemMeta();
        removeMeta.setDisplayName("Supprimer un kit");
        remove.setItemMeta(removeMeta);

        inventory.setItem(0, nothing);
        inventory.setItem(1, nothing);
        inventory.setItem(2, nothing);
        inventory.setItem(3, nothing);
        inventory.setItem(4, nothing);

        return inventory;
    }


}














