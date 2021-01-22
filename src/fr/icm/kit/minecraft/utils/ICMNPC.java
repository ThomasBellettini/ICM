package fr.icm.kit.minecraft.utils;

import java.util.List;
import java.util.UUID;

import fr.icm.PvPBox;
import fr.icm.kit.minecraft.utils.network.PacketReader;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.scheduler.BukkitRunnable;


public class ICMNPC extends Reflections{

    private int entityID;
    private Location location;
    private GameProfile gameprofile;
    private ArmorStand armorStand;
    private ArmorStand armorStand2;

    public PacketPlayOutNamedEntitySpawn spawnpacket;


    public ICMNPC(String name,Location location){
        entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
        gameprofile = new GameProfile(UUID.randomUUID(), name);
        changeSkin();
        this.location = location.clone();
    }

    public void changeSkin(){
        String value = "ewogICJ0aW1lc3RhbXAiIDogMTYwMDIwNzEyNTMwNywKICAicHJvZmlsZUlkIiA6ICIwYTU5MzNmZGYxZGI0Mzc0YWFmNGUzMTEwOTIxZGNjYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJPcml1bTU3IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y5MmIxZTAzN2EyZDU5NGI5NDA1ZjM1YmNkYjZiMTAyMTE5OWI3NWI1NWE2MzhmZTY5M2Q2MjdiZTBkNTcxMjIiCiAgICB9CiAgfQp9";
        String signature =
                "YTNSnOkv/myfFQIzmS5Ue2RfRTO0g11EACC9axcxuE17NsBfHpH3Cc6g9WDSajIktUfE1tJ+S/IaiNz+VedGZ/e5dpv8wTLA6q3CJjNrFzpqvSra/2TmlDK/1CJKz3gfmSiCds/vuBiKdbxEaLckk7jFe+Oay1TOwrBhhMhp9GtiHYkLosbTs8cUvMl53hrRMSmUBRWdNSFYihW1Ry5MM2FTKxJj4nYIYTln8dIVdTribAjLN3uWDkYtE3RjCtwCB+LN3kGtrQ5iI8Hu8n+yGr/O8KEfCmJI9vWxonCAsX65HaD4+mNfBqxzN0UPPzGjsZ2Fgf1OmK6OrWFBtPRUA7Kaa9bSRemVPdRZ3YgYtF/84AC9XSr+o+i/A5OE/3m6cidfY/zQMgZJKNfqQ5CdtRIFUYoz3bnJSfZUPgFW8cfQvILazWdo3fgH79SHsO4/rBSlvmLdc6ETn7fQCnaYYTCTLggLUAD/0DeMQZdoT/4l6AOiC0Q+h+g6H3qw30hJ5Kw0jWuviGBZhOt1OWRuA2zs4nBBPw+KqSWz7VN0dGTV3grlJDpnkG4PEJ7a4vg+utKOUvPk67UWpdUqRb21TPz6nrQMfTeEFkO7ci63oAHLTGsICVXdcX+3MfXmuzd4Yr1OjfKDonfVSyx8Lk6uaUgApivZqiVMmfI3nrTs2uY=";
        gameprofile.getProperties().put("textures", new Property("textures", value, signature));
    }


    public void animation(int animation){
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        setValue(packet, "a", entityID);
        setValue(packet, "b", (byte)animation);
        sendPacket(packet);
    }

    public void status(int status){
        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
        setValue(packet, "a", entityID);
        setValue(packet, "b", (byte)status);
        sendPacket(packet);
    }

    public void equip(int slot,ItemStack itemstack){
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        setValue(packet, "a", entityID);
        setValue(packet, "b", slot);
        setValue(packet, "c", itemstack);
        sendPacket(packet);
    }

    public void sleep(boolean state){
        if(state){
            Location bedLocation = new Location(location.getWorld(), 1, 1, 1);
            PacketPlayOutBed packet = new PacketPlayOutBed();
            setValue(packet, "a", entityID);
            setValue(packet, "b", new BlockPosition(bedLocation.getX(),bedLocation.getY(),bedLocation.getZ()));

            for(Player pl : Bukkit.getOnlinePlayers()){
                pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
            }

            sendPacket(packet);
            teleport(location.clone().add(0,0.3,0));
        }else{
            animation(2);
            teleport(location.clone().subtract(0,0.3,0));
        }
    }

    public void spawn(){

        armorStand = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX(), location.getY() + 0.3, location.getZ()), EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§eSélécteur de Kit");

        armorStand2 = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()), EntityType.ARMOR_STAND);
        armorStand2.setGravity(false);
        armorStand2.setVisible(false);
        armorStand2.setCustomNameVisible(true);
        armorStand2.setCustomName("§aClic-Droit §7Pour choisir un Kit !");

        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

        setValue(packet, "a", entityID);
        setValue(packet, "b", gameprofile.getId());
        setValue(packet, "c", getFixLocation(location.getX()));
        setValue(packet, "d", getFixLocation(location.getY()));
        setValue(packet, "e", getFixLocation(location.getZ()));
        setValue(packet, "f", getFixRotation(location.getYaw()));
        setValue(packet, "g", getFixRotation(location.getPitch()));
        setValue(packet, "h", 0);
        DataWatcher w = new DataWatcher(null);
        w.a(6,(float)20);
        w.a(10,(byte)127);
        setValue(packet, "i", w);
        spawnpacket = packet;
        addToTablist();
        sendPacket(packet);
        headRotation(location.getYaw(), location.getPitch());

        new BukkitRunnable()
        {
            int i = 1;
            @Override
            public void run() {
                i--;
                if(i <= 0)
                {
                    cancel();
                    for(Player player : Bukkit.getOnlinePlayers())
                    {
                        rmvFromTablist(player);
                        if(!PvPBox.getInstance.packetReaderMap.containsKey(player))
                        {
                            PacketReader packetReader = new PacketReader(player);
                            packetReader.inject();
                            PvPBox.getInstance.packetReaderMap.put(player, packetReader);
                        }
                    }
                }
            }
        }.runTaskTimer(PvPBox.getInstance, 0, 20L);

    }

    public void userJoinUpdate(Player player)
    {
        setValue(spawnpacket, "a", entityID);
        setValue(spawnpacket, "b", gameprofile.getId());
        setValue(spawnpacket, "c", getFixLocation(location.getX()));
        setValue(spawnpacket, "d", getFixLocation(location.getY()));
        setValue(spawnpacket, "e", getFixLocation(location.getZ()));
        setValue(spawnpacket, "f", getFixRotation(location.getYaw()));
        setValue(spawnpacket, "g", getFixRotation(location.getPitch()));
        setValue(spawnpacket, "h", 0);
        DataWatcher w = new DataWatcher(null);
        w.a(6,(float)20);
        w.a(10,(byte)127);
        addToTablist(player);

        setValue(spawnpacket, "i", w);
        sendPacket(spawnpacket, player);

        PacketPlayOutEntityLook packets = new PacketPlayOutEntityLook(entityID, getFixRotation(location.getYaw()),getFixRotation(location.getPitch()) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(location.getYaw()));

        sendPacket(packets, player);
        sendPacket(packetHead, player);

    }


    public void teleport(Location location){
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
        setValue(packet, "a", entityID);
        setValue(packet, "b", getFixLocation(location.getX()));
        setValue(packet, "c", getFixLocation(location.getY()));
        setValue(packet, "d", getFixLocation(location.getZ()));
        setValue(packet, "e", getFixRotation(location.getYaw()));
        setValue(packet, "f", getFixRotation(location.getPitch()));

        sendPacket(packet);
        headRotation(location.getYaw(), location.getPitch());
        this.location = location.clone();
    }

    public void headRotation(float yaw,float pitch){
        PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw));


        sendPacket(packet);
        sendPacket(packetHead);
    }

    public void destroy(){
        if(armorStand != null)
        {
            armorStand.remove();
            armorStand = null;
        }
        if(armorStand2 != null)
        {
            armorStand2.remove();
            armorStand2 = null;
        }
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
        for(Player player : Bukkit.getOnlinePlayers())
        {
            rmvFromTablist(player);
            sendPacket(packet, player);
        }
    }

    public void addToTablist(){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);

        sendPacket(packet);
    }
    public void addToTablist(Player player){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);

        sendPacket(packet, player);
    }

    public void rmvFromTablist(Player player){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);

        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);

        sendPacket(packet, player);
    }

    public int getFixLocation(double pos){
        return (int)MathHelper.floor(pos * 32.0D);
    }

    public int getEntityID() {
        return entityID;
    }

    public byte getFixRotation(float yawpitch){
        return (byte) ((int) (yawpitch * 256.0F / 360.0F));
    }





}
