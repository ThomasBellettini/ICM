package fr.icm.general.commands.admin;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.general.commands.admin.gui.GuiAccount;
import fr.icm.rank.module.utils.RankEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AccountManagerAccount implements CommandExecutor {

    private PvPBox instance = PvPBox.getInstance;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] arg) {

        if (sender instanceof Player) {
            Player bPlayer = (Player)sender;
            ICMPlayer iPlayer = playerLoader.getICMByPlayer(bPlayer);

            if (iPlayer.getRank() != RankEnum.ADMINISTRATOR) {
                bPlayer.sendMessage("§7[§c!§7] §4Error, you can't edit user account ! {<ADMINISTRATO}");
                return true;
            }
            if (arg.length == 1) {
                ICMPlayer icmPlayer = playerLoader.getICMByName(arg[0]);

                if (icmPlayer == null) {
                    bPlayer.sendMessage("§7[§c!§7] §cError, Player was not found in our database !");
                    return true;
                }
                bPlayer.openInventory(GuiAccount.GuiAccount(icmPlayer));
            }
        }
        return true;
    }
}
