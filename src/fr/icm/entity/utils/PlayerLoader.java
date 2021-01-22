package fr.icm.entity.utils;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.general.utils.ScoreboardSign;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;

public class PlayerLoader {



    private ArrayList<ICMPlayer> playerLoader = new ArrayList<>();
    private Map<ICMPlayer, ScoreboardSign> signMap = new HashMap<>();
    public void register(ICMPlayer player)
    {
        if(!playerLoader.isEmpty())
        {
            for(ICMPlayer icmPlayer : playerLoader)
            {
                if(icmPlayer.getName().equalsIgnoreCase(player.getName()))
                {
                    return;
                }
            }
        }
        System.out.println("[!] Adding Player to Loader ! (" + player.getName() + ")");
        if(player.getLevelPerKit().isEmpty())
        {
            player.setDefaultKit();
        }
        PvPBox.getStorage.write(player);
        playerLoader.add(player);
    }

    public void saveAll()
    {
        if(playerLoader.isEmpty())
            return;
        for(ICMPlayer icmPlayer : playerLoader)
        {
            PvPBox.getStorage.write(icmPlayer);
        }
        Bukkit.broadcastMessage("§7[§eICM§7] §eSauvegarde de §a" + playerLoader.size() + " §eProfile !");
    }

    public void savePlayer(ICMPlayer icmPlayer)
    {
        if(playerLoader.contains(icmPlayer))
        {
            PvPBox.getStorage.write(icmPlayer);
        }
        else
            return;
    }

    public ArrayList<ICMPlayer> getICMPlayer()
    {
        return playerLoader;
    }

    public Map<ICMPlayer, ScoreboardSign> getSignMap() {
        return signMap;
    }

    public ICMPlayer getICMByPlayer(Player bukkitPlayer)
    {
        if(playerLoader.isEmpty())
            return null;
        for(ICMPlayer joueur : playerLoader)
        {
            if(joueur.getName().equalsIgnoreCase(bukkitPlayer.getName()))
            {
                return joueur;
            }
        }
        return null;
    }

    public Player getPlayerByAPI(ICMPlayer icmPlayer)
    {
        if(playerLoader.isEmpty())
            return null;
        for(ICMPlayer joueur : playerLoader)
        {
            if(joueur.getName().equalsIgnoreCase(icmPlayer.getName()))
            {
                return Bukkit.getPlayer(icmPlayer.getName());
            }
        }
        return null;
    }

    public ICMPlayer getICMByName(String playerName)
    {
        if(playerLoader.isEmpty())
            return null;
        for(ICMPlayer joueur : playerLoader)
        {
            if(joueur.getName().equalsIgnoreCase(playerName))
            {
                return joueur;
            }
        }
        return null;
    }
}
