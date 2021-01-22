package fr.icm.general.commands;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReply implements CommandExecutor {

    PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player)
        {
            Player p = (Player)sender;
            if(args.length >= 1)
            {

                ICMPlayer icmPlayer = playerLoader.getICMByPlayer((Player) sender);
                if(icmPlayer == null)
                {
                    icmPlayer = new ICMPlayer(sender.getName(), 0, 0, 0, ((Player) sender).getAddress().getHostString());
                    playerLoader.register(icmPlayer);
                }
                if(icmPlayer.getLastDM() == null)
                {
                    Bukkit.getPlayer(icmPlayer.getName()).sendMessage("§7[§c!§7] §cErreur, Vous n'avez DM personne ! §7(§a/w <pseudo> <message>§7)");
                    return false;
                }
                Player ps = Bukkit.getPlayer(icmPlayer.getLastDM());
                if(ps == null)
                {
                    sender.sendMessage("§c[!] Le Joueur que vous essayez de DM n'est pas connecté !");
                    return false;
                }

                ICMPlayer icmVictim = playerLoader.getICMByPlayer(ps);
                if(icmVictim == null)
                {
                    icmVictim = new ICMPlayer(ps.getName(), 0, 0, 0, ps.getAddress().getHostString());
                    playerLoader.register(icmVictim);
                }

                StringBuilder stringBuilder = new StringBuilder();
                for(String part : args){
                    if(part.equalsIgnoreCase(p.getName()))
                        continue;
                    stringBuilder.append(part + " ");
                }


                String message = stringBuilder.toString();
                TextComponent textComponent = new TextComponent("§7(§aMessage§7) §7[§6" + ((Player) sender).getDisplayName() + " §7» §aVous§7] : §b" + message);
                textComponent.setColor(ChatColor.AQUA);
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7[§6!§7] §eSi tu cliques sur le message tu peux directement répondre !").create()));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/w " + sender.getName() + " "));

                TextComponent textComponents = new TextComponent("§7(§aMessage§7) §7[§aVous §7» §6" + ps.getDisplayName() + "§7] : §b" + message);
                textComponents.setColor(ChatColor.AQUA);
                textComponents.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7[§6!§7] §eSi tu cliques sur le message tu peux directement répondre !").create()));
                textComponents.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/w " + p.getName() + " "));

                icmVictim.setLastDM(icmPlayer.getName());
                icmPlayer.setLastDM(icmVictim.getName());

                ps.spigot().sendMessage(textComponent);
                ((Player) sender).spigot().sendMessage(textComponents);
            }
            else
            {
                sender.sendMessage("§7[§c!§7] §aUtilisation : /r <message>");
                return true;
            }
        }

        return false;
    }
}
