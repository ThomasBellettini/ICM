package fr.icm.general.event;

import fr.icm.PvPBox;
import fr.icm.entity.ICMParty;
import fr.icm.entity.ICMPlayer;
import fr.icm.rank.module.utils.RankEnum;
import fr.icm.zone.event.UserCancelEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import sun.plugin.dom.core.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class UserSendChatMessage implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e)
    {
        if (e.isCancelled())
            return;
        ICMPlayer icmUser = PvPBox.getPlayerLoader.getICMByPlayer(e.getPlayer());
        if (icmUser == null)
            return;
        e.setCancelled(true);
        if (UserCancelEvent.UserCancelEvent(e))
            return;

        if (e.getMessage().startsWith("*") && PvPBox.getPartyLoader.getPartiesWithMember(icmUser) != null) {
            String msg = e.getMessage().substring(1);
            ICMParty party = PvPBox.getPartyLoader.getPartiesWithMember(icmUser);
            for (Player p : party.convertArrayPlayer()) {
                TextComponent textComponent;
                textComponent = new TextComponent("§7[§9Groupe§7] " + e.getPlayer().getDisplayName() + " §b» ");
                TextComponent msgq =  new TextComponent(msg.replace("&", "§"));
                msgq.setColor(icmUser.getRank().getColor());
                textComponent.addExtra(msgq);
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7[§eTIPS§7] §aPour parler en PartyChat, Mettez une \"*\" devant votre message !").create()));
                p.spigot().sendMessage(textComponent);
            }
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(p);
            if (icmPlayer == null) {
                icmPlayer = new ICMPlayer(p.getName(), 0, 0, 0, p.getAddress().getHostString());
                PvPBox.getPlayerLoader.register(icmPlayer);
            }
            String format;
            String formatadmin =  "§eStatistique de " + e.getPlayer().getName() + " :" +
                "\n \n     §6Kill(s) : " + icmUser.getKill() +
                "\n     §6Mort(s) : " + icmUser.getDeath() +
                "\n     §6KillStreak : §cWIP\n " +
                "\n§cInformation Administrateur :"+
                "\n  \n     §6Kit : §a" + (icmUser.getFightingKit() != null ? icmUser.getFightingKit().getKitName() : "§cX") +
                "\n     §6Combat : §e" + (icmUser.isCombatTag() ? "§a" : "§cX")+
                "\n     §6AdresseIP : §c" + icmUser.getAdressIP() +
                "\n     §6Vie : §d" + e.getPlayer().getHealth() +
                    "\n \n§a§oClique sur le message pour envoyez un DM à §e" + e.getPlayer().getName();

            String formatAll = "§eStatistique de " + e.getPlayer().getName() + " :" +
                    "\n \n     §6Kill(s) : " + icmUser.getKill() +
                    "\n     §6Mort(s) : " + icmUser.getDeath() +
                    "\n     §6KillStreak : §cWIP"+
                    "\n \n§a§oClique sur le message pour envoyez un DM à §e" + e.getPlayer().getName();
            String msg = e.getMessage();

            ArrayList<RankEnum> staff = new ArrayList<>(Arrays.asList(RankEnum.ADMINISTRATOR, RankEnum.MODERATOR, RankEnum.RESPONSABLE, RankEnum.HELPER, RankEnum.STAFF));
            if(staff.contains(icmUser.getRank()))
                msg = e.getMessage().replace("&", "§");

            TextComponent textComponent;
            if (icmPlayer.getRank() == RankEnum.ADMINISTRATOR) {
                textComponent = new TextComponent(e.getPlayer().getDisplayName() + " §7» ");
                TextComponent msgq =  new TextComponent(msg);
                msgq.setColor(icmUser.getRank().getColor());
                textComponent.addExtra(msgq);
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(formatadmin).create()));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/w " + e.getPlayer().getName() + " "));

            } else {
                textComponent = new TextComponent(e.getPlayer().getDisplayName() + " §7» ");
                TextComponent msgq =  new TextComponent(msg);
                msgq.setColor(icmUser.getRank().getColor());
                textComponent.addExtra(msgq);
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(formatAll).create()));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/w " + e.getPlayer().getName() + " "));
            }
            p.spigot().sendMessage(textComponent);
        }
    }

    private String clickableMessage(String value, String hover)
    {
        return "{text:\"" + value + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hover + "\"}}";
    }

    private String clickableMessage(String type, String value, String hover, String execute)
    {
        return "{text:\"" + value + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hover + "\"},clickEvent:{action:\"" + type + "\",value:\"" + execute + "\"}}";
    }
}