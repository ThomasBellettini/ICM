package fr.icm.zone.api;

import fr.icm.PvPBox;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ICMZone {

    private String name, UUID;
    private double xMax, zMax, yMax;
    private double xMin, zMin, yMin;
    boolean canPvp, canBuild, canDestroy, canEntityDamage, canInteract, canGrief, canUsePower, canMobSpawn, canViewOtherPlayer, isInvicible, foodLess, canInteractAtEntity, canTeleport;

    public ICMZone(String name, String UUID, double xMax, double zMax, double yMax, double xMin, double zMin, double yMin, boolean canPvp, boolean canBuild, boolean canDestroy, boolean canEntityDamage, boolean canInteract, boolean canGrief, boolean canUsePower, boolean canMobSpawn, boolean canViewOtherPlayer, boolean isInvicible, boolean foodLess, boolean canInteractAtEntity, boolean canTeleport)
    {
        this.name = name;
        this.UUID = UUID;
        this.xMax = xMax;
        this.zMax = zMax;
        this.yMax = yMax;
        this.xMin = xMin;
        this.zMin = zMin;
        this.yMin = yMin;
        this.canPvp = canPvp;
        this.canBuild = canBuild;
        this.canDestroy = canDestroy;
        this.canEntityDamage = canEntityDamage;
        this.canInteract = canInteract;
        this.canGrief = canGrief;
        this.canUsePower = canUsePower;
        this.canMobSpawn = canMobSpawn;
        this.canViewOtherPlayer = canViewOtherPlayer;
        this.isInvicible = isInvicible;
        this.foodLess = foodLess;
        this.canInteractAtEntity = canInteractAtEntity;
        this.canTeleport = canTeleport;
    }

    public ICMZone(String name)
    {
        this.name = name;
        this.UUID = java.util.UUID.randomUUID().toString();
        this.canPvp = true;
        this.canBuild = true;
        this.canDestroy = true;
        this.canEntityDamage = true;
        this.canInteract = true;
        this.canGrief = true;
        this.canUsePower = true;
        this.canMobSpawn = true;
        this.canViewOtherPlayer = true;
        this.isInvicible = false;
        this.foodLess = false;
        this.canInteractAtEntity = true;
        this.canTeleport = true;
    }

    public void setNewLocation(Location location_1, Location location_2)
    {
        if (location_1.getX() > location_2.getX()) {
            this.xMax = location_1.getX();
            this.xMin = location_2.getX();
        } else {
            this.xMin = location_1.getX();
            this.xMax = location_2.getX();
        }
        if (location_1.getZ() > location_2.getZ()) {
            this.zMax = location_1.getZ();
            this.zMin = location_2.getZ();
        } else {
            this.zMin = location_1.getZ();
            this.zMax = location_2.getZ();
        }
        if (location_1.getY() > location_2.getY()) {
            this.yMax = location_1.getY() + 1;
            this.yMin = location_2.getY() - 1;
        } else {
            this.yMin = location_1.getY() - 1;
            this.yMax = location_2.getY() + 1;
        }
    }

    public boolean isInsideZone(Entity player)
    {
        Location l = player.getLocation();
        double x = l.getX();
        double z = l.getZ();
        double y = l.getY();
        if (x < xMax && z < zMax && y < yMax)
            if (x > xMin && z > zMin && y > yMin)
                return true;
        return false;
    }

    public boolean isInsideZone(Location l)
    {
        double x = l.getX();
        double z = l.getZ();
        double y = l.getY();
        if (x < xMax && z < zMax && y < yMax)
            if (x > xMin && z > zMin && y > yMin)
                return true;
        return false;
    }

    public void register()
    {
        if(yMax < 0)
            return;
        PvPBox.getZoneLoader.register(this);
    }

    public boolean isCanTeleport() { return canTeleport; }
    public void setCanTeleport(boolean canTeleport) { this.canTeleport = canTeleport; }
    public boolean isInvicible() { return isInvicible; }
    public void setInvicible(boolean invicible) { isInvicible = invicible; }
    public boolean isCanInteractAtEntity() { return canInteractAtEntity; }
    public void setCanInteractAtEntity(boolean canInteractAtEntity) { this.canInteractAtEntity = canInteractAtEntity; }
    public boolean isFoodLess() { return foodLess; }
    public void setFoodLess(boolean foodLess) { this.foodLess = foodLess; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUUID() { return UUID; }
    public void setUUID(String UUID) { this.UUID = UUID; }
    public double getxMax() { return xMax; }
    public void setxMax(double xMax) { this.xMax = xMax; }
    public double getzMax() { return zMax; }
    public void setzMax(double zMax) { this.zMax = zMax; }
    public double getyMax() { return yMax; }
    public void setyMax(double yMax) { this.yMax = yMax; }
    public double getxMin() { return xMin; }
    public void setxMin(double xMin) { this.xMin = xMin; }
    public double getzMin() { return zMin; }
    public void setzMin(double zMin) { this.zMin = zMin; }
    public double getyMin() { return yMin; }
    public void setyMin(double yMin) { this.yMin = yMin; }
    public boolean isCanPvp() { return canPvp; }
    public void setCanPvp(boolean canPvp) { this.canPvp = canPvp; }
    public boolean isCanBuild() { return canBuild; }
    public void setCanBuild(boolean canBuild) { this.canBuild = canBuild; }
    public boolean isCanDestroy() { return canDestroy; }
    public void setCanDestroy(boolean canDestroy) { this.canDestroy = canDestroy; }
    public boolean isCanEntityDamage() { return canEntityDamage; }
    public void setCanEntityDamage(boolean canEntityDamage) { this.canEntityDamage = canEntityDamage; }
    public boolean isCanInteract() { return canInteract; }
    public void setCanInteract(boolean canInteract) { this.canInteract = canInteract; }
    public boolean isCanGrief() { return canGrief; }
    public void setCanGrief(boolean canGrief) { this.canGrief = canGrief; }
    public boolean isCanUsePower() { return canUsePower; }
    public void setCanUsePower(boolean canUsePower) { this.canUsePower = canUsePower; }
    public boolean isCanMobSpawn() { return canMobSpawn; }
    public void setCanMobSpawn(boolean canMobSpawn) { this.canMobSpawn = canMobSpawn; }
    public boolean isCanViewOtherPlayer() { return canViewOtherPlayer; }
    public void setCanViewOtherPlayer(boolean canViewOtherPlayer) { this.canViewOtherPlayer = canViewOtherPlayer; }
    public void save() { PvPBox.getZoneStorage.write(this); }
    public void delete() { PvPBox.getZoneStorage.remove(this); }
}