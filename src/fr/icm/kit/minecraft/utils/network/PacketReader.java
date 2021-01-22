package fr.icm.kit.minecraft.utils.network;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.minecraft.gui.ICMGuiKit;
import fr.icm.kit.minecraft.gui.enumeration.ICMGuiType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;

import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketReader {

    Player player;
    Channel channel;
    int id;

    public PacketReader(Player player) {
        this.player = player;
        this.id = (int)Math.ceil(Math.random() * 1000) + 2000;
    }

    public void inject(){
        CraftPlayer cPlayer = (CraftPlayer)this.player;
        channel = cPlayer.getHandle().playerConnection.networkManager.channel;
        channel.pipeline().addAfter("decoder", "PacketInjector" + id,  new MessageToMessageDecoder<Packet<?>>() {@Override protected void decode(ChannelHandlerContext arg0, Packet<?> packet,List<Object> arg2) throws Exception {arg2.add(packet);readPacket(packet);}});
    }

    public void uninject(){
        if(channel.pipeline().get("PacketInjector" + id) != null){
            channel.pipeline().remove("PacketInjector" + id);
        }
    }


    public void readPacket(Packet<?> packet){
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
            int id = (Integer)getValue(packet, "a");

            if(PvPBox.getKitNPC.getEntityID() == id){
                if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
                    ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(player);
                    if(icmPlayer == null) return;
                    player.openInventory(PvPBox.getGuiKit.setGui(ICMGuiType.GUI_MAIN, icmPlayer, 1));
                }
                else if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
                {
                    ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(player);
                    if(icmPlayer == null) return;
                    player.openInventory(PvPBox.getGuiKit.setGui(ICMGuiType.GUI_MAIN, icmPlayer, 1));
                }
            }

        }
        /**
        else if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUpdateSign"))
        {

            IChatBaseComponent[] lines = (IChatBaseComponent[]) getValue(packet, "b");
            String[] translate = new String[4];
            int i = 0;

            for(IChatBaseComponent l : lines)
            {
                translate[i] = IChatBaseComponent.ChatSerializer.a(l).substring(1, IChatBaseComponent.ChatSerializer.a(l).length() - 1 );
                i++;
            }
            if(translate[2] != null && !translate[2].isEmpty())
            {
                Bukkit.broadcastMessage("§7Le Joueur §e" + translate[2] + "§7 va désormais prendre tarif !");
            }
            else
            {
                player.sendMessage("§cPlease Specify a Valid playerName !");
            }
            return;
        }
         **/
    }


    public void setValue(Object obj,String name,Object value){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        }catch(Exception e){}
    }

    public Object getValue(Object obj,String name){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        }catch(Exception e){}
        return null;
    }

}
