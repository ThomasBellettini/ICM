package fr.icm.kit.module.utils;

import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Tier;

import java.util.ArrayList;

public class KitLoader {

    public KitLoader()
    {
        System.out.println("Launching Kit Loader Class ...");
    }

    private ArrayList<Kit> kitLoader = new ArrayList<>();

    public void register(Kit kit)
    {
        if(kitLoader.contains(kit))
            return;
        if(kit.getKitTier() == null)
        {
            System.out.println("   [!] This kit is Unavailable (No Tier Found)");
            return;
        }
        kitLoader.add(kit);
    }

    public ArrayList<Kit> getKit()
    {
        return kitLoader;
    }

    public Kit getKitPerName(String name)
    {
        if(kitLoader.isEmpty())
            return null;
        for(Kit kit : kitLoader)
        {
            if(kit.getKitName().equalsIgnoreCase(name))
            {
                return kit;
            }
        }
        return null;
    }

}
