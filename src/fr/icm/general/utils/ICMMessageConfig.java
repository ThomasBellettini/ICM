package fr.icm.general.utils;

public class ICMMessageConfig {

    /**
     * @Message
     */

    public String player_not_connected = "§7[§c!§7] §cErreur, le joueur %player% n'est pas connecté !";
    public String cooldown_expired = "§6[!] Votre cooldown à expirer !";
    public String combat_tag_remove = "§a[!] Vous n'êtes plus en combat !";
    public String combat_tag_start = "§c[!] Vous êtes désormais en combat pendant 30 secondes !";

    //whisp
    public String message_to_yourself = "§7[§c!§7] §cTu peux DM Zomboglace si tu cherche un ami :)";
    public String message_console_to_player = "§7(§aMessage§7) §7[§cConsole §7» §aVous§7] : §b%msg%";
    public String message_utilisation = "§7[§c!§7] §aUtilisation : /w <player> <message>";
    public String message_player_not_connected = "§c[!] Le Joueur que vous essayez de DM n'est pas connecté !";
    //whisp reply
    public String message_reply_nobody = "§7[§c!§7] §cErreur, Vous n'avez DM personne ! §7(§a/w <pseudo> <message>§7)";
    public String message_reply_utilisation = "§7[§c!§7] §aUtilisation : /r <message>";

    //party
    public String party_create = "§a[!] Vous venez de crée votre groupe ! §7(§aUtilise /party add <pseudo>§7)";
    public String party_alone = "§c[!] Erreur, tu es seul dans le groupe ! §7(§aUtilise /party disband§7)";
    public String party_new_leader = "§b[!] Le nouveau chef du groupe est désormais §e%party_owner%";
    public String party_kick = "§b[!] Le Joueur §c%kicked%§b à été kick du groupe !";
    public String party_no_invitation = "§c[!] Tu n'as aucune invitation en attente !";
    public String party_already_in_party = "§c[!] Erreur tu as déjà un groupe ! §7(§aUtilise /party disband§7)";
    //party invite
    public String party_invite_yourself = "§7[§c!§7] §aTu as crée ta partie, mais tu ne peux pas t'inviter toi même :) !";
    public String party_invite_not_leader = "§c[!] Erreur, tu n'es pas le chef du groupe !";
    public String party_invite_full = "§7[§c!§7] §cErreur, ton groupe est complet ! §7(§a%party_max_size% Membres§7)";
    public String party_invite_already_in = "§c[!] Erreur le Joueur est déjà dans ton groupe !";
    public String party_invite_already_have = "§c[!] Erreur le Joueur est déjà dans un groupe !";
    public String party_invite_player = "§b[!] Vous avez envoyé une invitation pour rejoindre votre groupe à §9%player_victim%";
    //party player
    public String party_player_join = "§a[!] Un Nouveau Joueur a rejoint le groupe ! §7(§a%player%§7)";
    public String party_player_left = "§b[!] Le Joueur §c%player%§b est parti du groupe !";
    //party disband
    public String party_disband = "§c[!] Le groupe vient d'être dissout !";
    public String party_disband_no_group = "§c[!] Erreur tu n'as pas de groupe ! §7(§aUtilise /party create§7)";
    public String party_disband_leader_left = "§b[!] Votre groupe vient d'être dissout car votre chef l'a quitté !";

    public String zone_can_use = "§7[§c!§7] §cVous ne pouvez pas faire cela ici !";
    /**
     * @Broadcast
     */

    public String broadcast_all_files_save = "§7[§eICM§7] §eSauvegarde de §a%number_player_files% §eProfile !";

    public String userJoinServer = "§7[§a+§7] %player_displayname%";
    public String userLeftServer = "§7[§c-§7] %player_displayname%";

    /**
     * @Clickable_Message
     */

    public String click_party_invitation_message = "§b[!] Vous avez reçu une invitation à rejoindre le groupe de §9%party_owner%§b ! §7(§aClique ici§7)";
    public String click_party_invitation_hover = "§a[!] Si tu cliques tu rejoindras le Groupe !";

    public String click_msg_receive_message = "§7(§aMessage§7) §7[§6%sender% §7» §aVous§7] : §b%msgM";
    public String click_msg_receive_hover = "§7[§6!§7] §eSi tu cliques sur le message tu peux directement répondre !";

    public String click_msg_sender_message = "§7(§aMessage§7) §7[§aVous §7» §6%receiver%§7] : §b%msg%";
    public String click_msg_sender_hover = "§7[§6!§7] §eSi tu cliques sur le message tu peux directement répondre !";


    public ICMMessageConfig(String player_not_connected, String group_was_disband_chief_leave, String cooldown_expired, String combat_tag_remove, String combat_tag_start, String part_author_invitation_sent, String you_cant_dm_urself, String console_dm_you, String message_utilisation, String message_player_not_connected, String cant_reply, String reply_utilisation, String u_cant_invite_urself_in_party, String ur_group_is_full, String already_in_ur_group, String not_in_group, String u_are_not_leader, String party_lead_change, String u_are_alone, String party_kick_member, String no_invitation, String new_member, String player_left_group, String party_disband, String already_in_party, String new_party, String broadcast_all_files_save, String userJoinServer, String userLeftServer, String click_party_invitation_message, String click_party_invitation_hover, String click_msg_receive_message, String click_msg_receive_hover, String click_msg_sender_message, String click_msg_sender_hover) {
        this.player_not_connected = player_not_connected;
        this.cooldown_expired = cooldown_expired;
        this.combat_tag_remove = combat_tag_remove;
        this.combat_tag_start = combat_tag_start;
        this.message_utilisation = message_utilisation;
        this.message_player_not_connected = message_player_not_connected;
        this.party_disband = party_disband;
        this.broadcast_all_files_save = broadcast_all_files_save;
        this.userJoinServer = userJoinServer;
        this.userLeftServer = userLeftServer;
        this.click_party_invitation_message = click_party_invitation_message;
        this.click_party_invitation_hover = click_party_invitation_hover;
        this.click_msg_receive_message = click_msg_receive_message;
        this.click_msg_receive_hover = click_msg_receive_hover;
        this.click_msg_sender_message = click_msg_sender_message;
        this.click_msg_sender_hover = click_msg_sender_hover;
    }

    public ICMMessageConfig()
    {
    }
}
