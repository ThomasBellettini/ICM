package fr.icm.utils.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.general.utils.ICMMessageConfig;

import java.io.*;

public class ICMConfigMessageStorage {

    private Gson gson;
    private File DATA_DIR;
    private File CONFIG;

    public ICMConfigMessageStorage() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
        DATA_DIR = new File(PvPBox.getInstance.getDataFolder(), "/storage/config/");
        if(!DATA_DIR.exists())
            DATA_DIR.mkdirs();
        CONFIG = new File(DATA_DIR, "config_message.json");
        if(!CONFIG.exists())
            write(new ICMMessageConfig());


    }

    public void setup()
    {
        if(CONFIG.exists())
        {
            ICMMessageConfig icmMessageConfig = deserialize(CONFIG);
            if(icmMessageConfig != null)
            {
                PvPBox.getConfig = icmMessageConfig;
                System.out.println("SUCCESS CONFIG");
            }
            else
                System.out.println("Error in serialization (CONFIG)");
        }
        else
        {
            write(new ICMMessageConfig());
            PvPBox.getConfig = new ICMMessageConfig();
        }


    }

    public String read(File f)
    {
        if (f.exists())
        {
            try
            {
                final BufferedReader br = new BufferedReader(new FileReader(f));
                final StringBuilder sb = new StringBuilder();
                String str;

                while((str = br.readLine()) != null)
                {
                    sb.append(str);
                }

                return sb.toString();
            }
            catch (IOException e)
            {
            }
        }
        return "";
    }

    public void write(ICMMessageConfig icmConfigMessageStorage)
    {
        String str = gson.toJson(icmConfigMessageStorage);
        final FileWriter files;

        try
        {
            if(!CONFIG.exists())
            {
                CONFIG.createNewFile();
            }
            files = new FileWriter(CONFIG);
            files.write(str);
            files.flush();
            files.close();
        }
        catch (IOException e)
        {
            System.out.println("[!] Erreur, Serialization CODE#003");
        }
    }

    public ICMMessageConfig deserialize(File f)
    {
        return gson.fromJson(read(f), ICMMessageConfig.class);
    }

}
