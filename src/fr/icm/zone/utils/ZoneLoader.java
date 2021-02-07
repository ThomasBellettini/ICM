package fr.icm.zone.utils;

import fr.icm.PvPBox;
import fr.icm.zone.api.ICMZone;

import java.util.ArrayList;

public class ZoneLoader {

    private ArrayList<ICMZone> icmZones = new ArrayList<>();

    public void register(ICMZone icmZone)
    {
        if (icmZones.isEmpty())
            icmZones.add(icmZone);
        else
            for (ICMZone zone :  icmZones)
                if (zone.getUUID().equalsIgnoreCase(icmZone.getUUID())) {
                    icmZones.remove(icmZone);
                    icmZones.add(icmZone);
                    PvPBox.getZoneStorage.write(icmZone);
                    return;
                }
        icmZones.add(icmZone);
        PvPBox.getZoneStorage.write(icmZone);
    }

    public ICMZone getZonePerName(String s)
    {
        if (icmZones.isEmpty())
            return null;
        for (ICMZone z : icmZones)
            if (z.getName().equalsIgnoreCase(s))
                return z;
        return null;
    }

    public ArrayList<ICMZone> getIcmZones() { return icmZones; }
}
