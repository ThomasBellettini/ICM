package fr.icm.entity.utils;

import fr.icm.entity.ICMParty;
import fr.icm.entity.ICMPlayer;

import java.util.ArrayList;

public class PartyLoader {

    private ArrayList<ICMParty> parties = new ArrayList<>();


    public void register(ICMParty party)
    {
        if(parties.isEmpty())
            parties.add(party);
        else
        {
            for(ICMParty icmParty : parties)
            {
                if(party.getOwner().getName().equalsIgnoreCase(icmParty.getOwner().getName()))
                    return;
            }
            parties.add(party);
        }
    }

    public ICMParty getPartiesWithMember(ICMPlayer player)
    {
        if(parties.isEmpty())
            return null;
        for(ICMParty icmParty : parties)
        {
            for(ICMPlayer icmPlayer : icmParty.getMember())
            {
                if(icmPlayer.getName().equalsIgnoreCase(player.getName()))
                    return icmParty;
            }
        }
        return null;
    }

    public ArrayList<ICMParty> getParties() {
        return parties;
    }
}
