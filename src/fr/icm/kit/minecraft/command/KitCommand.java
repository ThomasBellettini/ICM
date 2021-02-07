package fr.icm.kit.minecraft.command;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.entity.utils.PlayerLoader;
import fr.icm.kit.minecraft.gui.ICMGuiKit;
import fr.icm.kit.minecraft.gui.enumeration.ICMGuiType;
import fr.icm.kit.module.utils.KitLoader;
import fr.icm.rank.module.utils.RankEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private KitLoader kitLoader = PvPBox.getKitLoader;
    private PlayerLoader playerLoader = PvPBox.getPlayerLoader;
    private ICMGuiKit guiKit = PvPBox.getGuiKit;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("kit")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§c[!] Erreur, cette commande n'est utilisable que par les joueurs !");
                return true;
            }
            if (playerLoader.getICMByName(sender.getName()).getRank() == RankEnum.ADMINISTRATOR) {
                ((Player) sender).openInventory(guiKit.setGui(ICMGuiType.GUI_MAIN, playerLoader.getICMByName(sender.getName()), 1));
                return true;
            }
        }
        return false;
    }
}
