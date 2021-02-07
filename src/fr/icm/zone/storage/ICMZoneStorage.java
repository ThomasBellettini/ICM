package fr.icm.zone.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.icm.PvPBox;
import fr.icm.zone.api.ICMZone;

import java.io.*;

public class ICMZoneStorage {

    private Gson gson;
    private File DATA_DIR;

    public ICMZoneStorage()
    {
        gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
        DATA_DIR = new File(PvPBox.getInstance.getDataFolder(), "/storage/zone/");
        if (!DATA_DIR.exists())
            DATA_DIR.mkdirs();
    }

    public void remove(ICMZone icmZone)
    {
        File f = new File(DATA_DIR, icmZone.getUUID().toLowerCase() + ".json");
        if (f.exists()) {
            f.delete();
            PvPBox.getZoneLoader.getIcmZones().removeIf(zone -> zone.getUUID().equalsIgnoreCase(icmZone.getUUID()));
        }
    }

    public void setup()
    {
        for (File f : DATA_DIR.listFiles()) {
            if (f.getName().endsWith(".json")) {
                ICMZone icmZone = read(f);
                if (icmZone == null) {
                    System.out.println("Error Serialization Setup ZONE");
                    continue;
                }
                PvPBox.getZoneLoader.getIcmZones().add(icmZone);
            }
        }
    }

    public void write(ICMZone icmZone)
    {
        final FileWriter files;
        File f = new File(DATA_DIR, icmZone.getUUID().toLowerCase() + ".json" );
        try {
            if(!f.exists())
                f.createNewFile();
            files = new FileWriter(f);
            files.write(gson.toJson(icmZone));
            files.flush();
            files.close();
        } catch (IOException e)
        {
            System.out.println("[!] Erreur, Serialization CODE#005");
        }
    }

    public ICMZone read(File f)
    {
        if (f.exists()) {
            try {
                final BufferedReader r = new BufferedReader(new FileReader(f));
                final StringBuilder t = new StringBuilder();
                String l;
                while((l = r.readLine()) != null)
                    t.append(l);
                r.close();
                return gson.fromJson(t.toString(), ICMZone.class);
            }
            catch (IOException e) {}
        }
        return null;
    }
}
