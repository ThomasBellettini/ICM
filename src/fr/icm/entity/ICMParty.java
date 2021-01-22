package fr.icm.entity;

import fr.icm.PvPBox;
import fr.icm.entity.utils.PartyLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ICMParty {

    private ICMPlayer owner;
    private ArrayList<ICMPlayer> member = new ArrayList<>();

    private  int maxMember;

    public ICMParty(ICMPlayer owner, int maxMember)
    {
        this.owner = owner;
        member.add(owner);
        this.maxMember = maxMember;
    }

    public ICMPlayer getOwner() {
        return owner;
    }

    public void setOwner(ICMPlayer owner) {
        this.owner = owner;
    }

    public ArrayList<ICMPlayer> getMember() {
        return member;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public void removeMember(ICMPlayer icmPlayer)
    {
        if(member.contains(icmPlayer))
            if(owner.getName().equalsIgnoreCase(icmPlayer.getName()))
            {
                for(ICMPlayer player : getMember())
                {
                    Player l = Bukkit.getPlayer(player.getName());
                    if(l == null) continue;
                    l.sendMessage("§b[!] Votre groupe vient d'être dissout car votre chef l'a quitté !");
                }
                disband();
            }
            else
            {
                member.remove(icmPlayer);
                if(member.size() <= 0) disband();
            }
    }

    public boolean addMember(ICMPlayer icmPlayer)
    {
        if(member.size() >= maxMember)
            return false;
        if(member.isEmpty()) disband();
        for(ICMPlayer player : member)
        {
            if(player.getName().equalsIgnoreCase(icmPlayer.getName()))
                return false;
        }
        member.add(icmPlayer);
        return true;
    }

    public List<Player> convertArrayPlayer()
    {
        List<Player> p = new ArrayList<>();
        for(ICMPlayer c : getMember())
        {
            Player w = Bukkit.getPlayer(c.getName());
            if(w == null) return null;
            p.add(w);
        }
        return p;
    }

    public boolean promote(ICMPlayer icmPlayer)
    {
        if(!member.contains(icmPlayer))
           return false;
        if(member.isEmpty()) disband();
        setOwner(icmPlayer);
        return true;
    }

    public void disband()
    {
        PartyLoader partyLoader = PvPBox.getPartyLoader;
        if(partyLoader.getParties().isEmpty())
            return;
        partyLoader.getParties().removeIf(icmParty -> icmParty.getOwner().getName().equalsIgnoreCase(owner.getName()));
        return;
    }

}
