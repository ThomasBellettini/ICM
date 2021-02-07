package fr.icm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;

import java.io.*;

public class ICMStoragePlayer {
    private static File DATA_DIR;
    private Gson gson;

    public ICMStoragePlayer()
    {
        gson = new GsonBuilder()
                .registerTypeAdapter(ICMPlayer.class, new ICMTypeAdapter())
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        DATA_DIR = new File(PvPBox.getInstance.getDataFolder(), "/storage/");
        if(!DATA_DIR.exists())
            DATA_DIR.mkdirs();
    }

    public void setup()
    {
        for(File f : DATA_DIR.listFiles())
            if(f.getName().endsWith(".json")) {
                ICMPlayer icmPlayer = deserialize(f);
                if(icmPlayer == null) {
                    System.out.println("Error, Deserialization CODE#002");
                    return;
                }
                PvPBox.getPlayerLoader.getICMPlayer().add(icmPlayer);
            }
    }

    public String serialize(ICMPlayer icmPlayer)
    {
        return gson.toJson(icmPlayer);
    }

    public void write(ICMPlayer icmPlayer)
    {
        final FileWriter files;
        File f = new File(DATA_DIR, icmPlayer.getName().toLowerCase() + ".json" );
        try {
            if(!f.exists())
                f.createNewFile();
            files = new FileWriter(f);
            files.write(serialize(icmPlayer));
            files.flush();
            files.close();
        }
        catch (IOException e)
        {
            System.out.println("[!] Erreur, Serialization CODE#001");
        }
    }

    public ICMPlayer deserialize(File f)
    {
        return gson.fromJson(read(f), ICMPlayer.class);
    }

    public String read(File f)
    {
        if(f.exists()) {
            try {
                final BufferedReader r = new BufferedReader(new FileReader(f));
                final StringBuilder t = new StringBuilder();
                String l;
                while((l = r.readLine()) != null)
                    t.append(l);
                r.close();
                return t.toString();
            }
            catch (IOException e) {}
        }
        return " ";
    }
}
