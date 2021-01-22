package fr.icm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ICMTypeAdapter extends TypeAdapter<ICMPlayer> {

    private static Type seriType = new TypeToken<HashMap<String, Integer>>() {}.getType();
    private static Type seriTypes = new TypeToken<List<String>>() {}.getType();

    @Override
    public void write(JsonWriter writer, ICMPlayer icmPlayer) throws IOException {

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        String map = gson.toJson(icmPlayer.getLevelPerKit());
        String list =gson.toJson(icmPlayer.getKitAccess());
        writer.beginObject();

        writer.name("playerName").value(icmPlayer.getName());
        writer.name("kill").value(icmPlayer.getKill());
        writer.name("death").value(icmPlayer.getDeath());
        writer.name("coins").value(icmPlayer.getCoins());
        writer.name("adresseIP").value(icmPlayer.getAdressIP());
        writer.name("levelPerKit").value(map);
        writer.name("accessKit").value(list);

        writer.endObject();

    }

    @Override
    public ICMPlayer read(JsonReader reader) throws IOException {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        reader.beginObject();
        String name = "";
        int kill = 0;
        int death = 0;
        int coins = 0;
        String adresseIP = "";

        HashMap<String, Integer> levelPerKit = new HashMap<>();
        List<String> acessKit = new ArrayList<>();

        while(reader.hasNext())
        {
            String s = reader.nextName();
            switch (s)
            {
                case "playerName":
                    name = reader.nextString();
                    break;
                case "kill":
                    kill = reader.nextInt();
                    break;
                case "death":
                    death = reader.nextInt();
                    break;
                case "coins":
                    coins = reader.nextInt();
                    break;
                case "adresseIP":
                    adresseIP = reader.nextString();
                    break;

                case "levelPerKit":
                    levelPerKit = gson.fromJson(reader.nextString(), seriType);
                    break;
                case "accessKit":
                    acessKit = gson.fromJson(reader.nextString(), seriTypes);
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();

         return new ICMPlayer(name, kill, death, coins, adresseIP ,levelPerKit, acessKit);

    }
}
