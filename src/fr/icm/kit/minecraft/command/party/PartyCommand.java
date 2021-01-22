package fr.icm.kit.minecraft.command.party;

import com.sun.xml.internal.ws.api.server.WSEndpoint;
import fr.icm.PvPBox;
import fr.icm.entity.ICMParty;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PartyLoader;
import fr.icm.entity.utils.PlayerLoader;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartyCommand implements CommandExecutor {

    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    private PartyLoader partyLoader = PvPBox.getPartyLoader;

    @Override
    public boolean onCommand(CommandSender s, Command c, String string, String[] args) {

        if(s instanceof Player)
        {
            ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer((Player) s);
            if(icmPlayer == null)
                return false;
            ICMParty party = PvPBox.getPartyLoader.getPartiesWithMember(icmPlayer);

            if(args.length == 2)
            {
                Player victim = Bukkit.getPlayer(args[1]);
                if(victim == null)
                {
                    s.sendMessage("§c[!] Erreur le joueur n'est pas connecté !");
                    return true;
                }
                ICMPlayer icmVictim = PvPBox.getPlayerLoader.getICMByPlayer(victim);
                if(icmVictim == null)
                {
                    icmVictim = new ICMPlayer(victim.getName(), 0, 0, 0, "D");
                    playerLoader.register(icmVictim);
                }
                switch (args[0].toLowerCase())
                {
                    case "invite": case "add": case "invites": case "inv":
                        if(icmPlayer.getName().equalsIgnoreCase(icmVictim.getName()))
                        {
                            party = new ICMParty(icmPlayer, 3);
                            partyLoader.register(party);
                            s.sendMessage("§7[§c!§7] §aTu as crée ta partie, mais tu ne peux pas t'inviter toi même :) !");
                            break;
                        }
                    if(party == null)
                    {
                        party = new ICMParty(icmPlayer, 3);
                        partyLoader.register(party);
                        icmVictim.sendPartyInvitation(party);
                        return true;
                    }
                    else
                    {
                        if(party.getMember().size() >= party.getMaxMember())
                        {
                            s.sendMessage("§7[§c!§7] §cErreur, ton groupe est complet ! §7(§a" + party.getMaxMember() + " Membres§7)");
                            break;
                        }
                        if(party.getMember().size() >= 1) {
                            for(ICMPlayer player : party.getMember()) {
                                if(player.getName().equalsIgnoreCase(icmVictim.getName()))
                                {
                                    s.sendMessage("§c[!] Erreur le Joueur est déjà dans ton groupe !");
                                    return true;
                                }
                            }
                        }
                        icmVictim.sendPartyInvitation(party);
                    }
                    break;
                    case "promote": case "leader":
                    if(party == null)
                    {
                        s.sendMessage("§c[!] Erreur tu n'as pas de groupe ! §7(§aUtilise /party create§7)");
                        return true;
                    }
                    if(!party.getOwner().getName().equalsIgnoreCase(icmPlayer.getName()))
                    {
                        s.sendMessage("§c[!] Erreur, tu n'es pas le chef du groupe !");
                        return true;
                    }
                    party.setOwner(icmVictim);
                    for(ICMPlayer player : party.getMember())
                    {
                        Player w = Bukkit.getPlayer(player.getName());
                        if(w == null) continue;
                        w.sendMessage("§b[!] Le nouveau chef du groupe est désormais §e" + icmVictim.getName());
                    }
                    break;
                    case "kick": case "remove":
                    if(party == null)
                    {
                        s.sendMessage("§c[!] Erreur tu n'as pas de groupe ! §7(§aUtilise /party create§7)");
                        return true;
                    }
                    if(!party.getOwner().getName().equalsIgnoreCase(icmPlayer.getName()))
                    {
                        s.sendMessage("§c[!] Erreur, tu n'es pas le chef du groupe !");
                        return true;
                    }
                    if(party.getMember().size() <= 1)
                    {
                        s.sendMessage("§c[!] Erreur, tu es seul dans le groupe ! §7(§aUtilise /party disband§7)");
                        return true;
                    }
                    playerLoader.getPlayerByAPI(icmVictim).setMaxHealth(20);
                    party.removeMember(icmVictim);
                    for(ICMPlayer player : party.getMember())
                    {
                        Player w = Bukkit.getPlayer(player.getName());
                        if(w == null) continue;
                        w.sendMessage("§b[!] Le Joueur §c" + icmVictim.getName() + "§b à été kick du groupe !");

                        int partySize = party.getMember().size();
                        if (partySize <= 5)
                            playerLoader.getPlayerByAPI(player).setMaxHealth(20 - (2 * (partySize - 1)));
                    }
                    break;

                }
            }
            else if(args.length == 1)
            {
                switch (args[0].toLowerCase())
                {
                    case "join": case "accept": case "rejoindre":
                        if(icmPlayer.getInvitation() == null)
                        {
                            s.sendMessage("§c[!] Tu n'as aucune invitation en attente !");
                            return true;
                        }
                        if(party == null)
                        {
                            icmPlayer.getInvitation().addMember(icmPlayer);
                            for(ICMPlayer player : icmPlayer.getInvitation().getMember())
                            {
                                Player w = Bukkit.getPlayer(player.getName());
                                if(w == null) continue;
                                w.sendMessage("§a[!] Un Nouveau Joueur a rejoint le groupe ! §7(§a" + s.getName() + "§7)");

                                int partySize = icmPlayer.getInvitation().getMember().size();
                                if (partySize <= 5)
                                    playerLoader.getPlayerByAPI(player).setMaxHealth(20 - (2 * (partySize - 1)));
                            }
                            icmPlayer.setInvitation(null);
                        }
                        else
                        {
                            playerLoader.getPlayerByAPI(icmPlayer).setMaxHealth(20);
                            party.removeMember(icmPlayer);
                            for(ICMPlayer player : party.getMember())
                            {
                                Player w = Bukkit.getPlayer(player.getName());
                                if(w == null) continue;
                                w.sendMessage("§b[!] Le Joueur §c" + s.getName() + "§b est parti du groupe !");

                                int partySize = party.getMember().size();
                                if (partySize <= 5)
                                    playerLoader.getPlayerByAPI(player).setMaxHealth(20 - (2 * (partySize - 1)));
                            }
                            icmPlayer.getInvitation().addMember(icmPlayer);
                            for(ICMPlayer player : icmPlayer.getInvitation().getMember())
                            {
                                Player w = Bukkit.getPlayer(player.getName());
                                if(w == null) continue;
                                w.sendMessage("§a[!] Un Nouveau Joueur a rejoint le groupe ! §7(§a" + s.getName() + "§7)");

                                int partySize = icmPlayer.getInvitation().getMember().size();
                                if (partySize <= 5)
                                    playerLoader.getPlayerByAPI(player).setMaxHealth(20 - (2 * (partySize - 1)));
                            }
                            icmPlayer.setInvitation(null);
                        }
                        break;
                    case "disband": case "dissoudre":
                        if(party == null)
                        {
                            s.sendMessage("§c[!] Erreur tu n'as pas de groupe ! §7(§aUtilise /party create§7)");
                            return true;
                        }
                        if(!party.getOwner().getName().equalsIgnoreCase(icmPlayer.getName()))
                        {
                            s.sendMessage("§c[!] Erreur, tu n'es pas le chef du groupe !");
                            return true;
                        }

                        for(ICMPlayer player : party.getMember())
                        {
                            Player w = Bukkit.getPlayer(player.getName());
                            if(w == null) continue;
                            w.sendMessage("§c[!] Le groupe vient d'être dissout !");

                            playerLoader.getPlayerByAPI(player).setMaxHealth(20);
                        }
                        party.disband();
                        break;
                    case "create": case "nouveau":
                        if(party != null)
                        {
                            s.sendMessage("§c[!] Erreur tu as déjà un groupe ! §7(§aUtilise /party disband§7)");
                            return true;
                        }
                        s.sendMessage("§a[!] Vous venez de crée votre groupe ! §7(§aUtilise /party add <pseudo>§7)");
                        partyLoader.register(new ICMParty(icmPlayer, 3));
                        break;
                    case "list": case "info":
                        if(party == null)
                        {
                            s.sendMessage("§c[!] Erreur tu n'as pas de groupe ! §7(§aUtilise /party create§7)");
                            return true;
                        }
                        s.sendMessage("§7§m-*--------------------*-");
                        s.sendMessage(" ");
                        for(ICMPlayer player : party.getMember())
                        {
                            if(party.getOwner().getName().equalsIgnoreCase(player.getName()))
                                s.sendMessage("    §7» " + player.getName() + " §7(§cChef§7)" );
                            else
                                s.sendMessage("    §7» " + player.getName() + " §7(§aMembre§7)" );
                        }
                        s.sendMessage(" ");
                        s.sendMessage("§7§m-*--------------------*-");
                        break;
                    case "leave": case "quitter":
                        if(party == null)
                        {
                            s.sendMessage("§c[!] Erreur tu n'as pas de groupe ! §7(§aUtilise /party join§7)");
                            return true;
                        }
                        if(party.getOwner().getName().equalsIgnoreCase(icmPlayer.getName()))
                        {
                            for(ICMPlayer player : party.getMember())
                            {
                                Player w = Bukkit.getPlayer(player.getName());
                                if(w == null) continue;
                                w.sendMessage("§c[!] Le groupe vient d'être dissout !");

                                playerLoader.getPlayerByAPI(player).setMaxHealth(20);
                            }
                            party.disband();
                            return true;
                        }
                        playerLoader.getPlayerByAPI(icmPlayer).setMaxHealth(20);
                        party.removeMember(icmPlayer);
                        for(ICMPlayer player : party.getMember())
                        {
                            Player w = Bukkit.getPlayer(player.getName());
                            if(w == null) continue;
                            w.sendMessage("§b[!] Le Joueur §c" + icmPlayer.getName() + "§b a quitté le groupe !");

                            int partySize = party.getMember().size();
                            if (partySize <= 5)
                                playerLoader.getPlayerByAPI(player).setMaxHealth(20 - (2 * (partySize - 1)));
                        }
                        break;

                }
            }
            else //suggest_command
            {
                s.sendMessage("§9§oCommande concernant les Groupes");
                s.sendMessage(" ");
                List<String> message = new ArrayList<>(Arrays.asList(
                        clickableMessage("run_command", "   §7/party create : Permet de créer un Groupe !", "§aClique pour créer un Groupe !", "/p create"),
                        clickableMessage("run_command", "   §7/party disband : Permet de dissoudre ton Groupe !", "§aClique pour dissoudre ton Groupe !", "/p disband"),
                        clickableMessage("run_command", "   §7/party leave : Permet de quitter le groupe !", "§aClique pour quitter le Groupe !", "/p leave"),
                        clickableMessage("run_command", "   §7/party join : Permet d'accepter une invitation !", "§aClique pour Accepter une invitation !", "/p join"),
                        clickableMessage("run_command", "   §7/party info : Permet d'avoir les infos de la partie !", "§aClique pour voir les infos du Groupe !", "/p info"),
                        clickableMessage("suggest_command", "   §7/party add <Joueur> : Permet d'inviter un joueur !", "§aClique pour Inviter un Joueur !", "/p add <joueur>"),
                        clickableMessage("suggest_command", "   §7/party kick <player> : Permet de Kick un jOueur !", "§aClique pour Kick un Joueur !", "/p kick <player>"),
                        clickableMessage("suggest_command", "   §7/party promote <player>: Permet de changer le leader !", "§aClique pour Changer de Leader !", "/p leader <player>")

                        ));




                for(String ms : message)
                {
                    IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(ms);
                    PacketPlayOutChat packet = new PacketPlayOutChat(comp);
                    ((CraftPlayer) Bukkit.getPlayer(icmPlayer.getName())).getHandle().playerConnection.sendPacket(packet);
                }
            }

            return true;
        }


        return false;
    }

    private String clickableMessage(String type, String value, String hover, String execute)
    {
        return "{text:\"" + value + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hover + "\"},clickEvent:{action:\"" + type + "\",value:\"" + execute + "\"}}";
    }

}
