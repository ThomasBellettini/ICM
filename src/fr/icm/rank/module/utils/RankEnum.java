package fr.icm.rank.module.utils;

import net.md_5.bungee.api.ChatColor;

public enum RankEnum
{

    ADMINISTRATOR("a", "§4§lAdmin ", ChatColor.RED),
    RESPONSABLE("b", "§6Responsable ", ChatColor.YELLOW),
    MODERATOR("c", "§9Modérateur ", ChatColor.AQUA),
    HELPER("d", "§bHelper ", ChatColor.AQUA),
    STAFF("e", "§2Staff ", ChatColor.GREEN),
    FRIEND("f", "§dFriend ", ChatColor.GRAY),
    GUEST("g", "§5Partenaire ", ChatColor.GRAY),
    VIP("h", "§eLégende ", ChatColor.GRAY),
    PLAYER("i", "§7Joueur ", ChatColor.GRAY);

    private String group_name;
    private String prefix;
    private ChatColor color;

    RankEnum(String group_name, String prefix, ChatColor color) {
        this.group_name = group_name;
        this.prefix = prefix;
        this.color = color;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getColor() {
        return color;
    }
}