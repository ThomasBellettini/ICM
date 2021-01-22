package fr.icm.kit.module.api;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kit
{

    private String kitName;
    private int kitID;
    private boolean isVIP;

    private boolean isSecret;

    protected Tier[] tier;
    private boolean onCooldown;

    protected Kit(String kitName, int kitID, boolean isVIP, boolean onCooldown) {
        this.kitName = kitName;
        this.kitID = kitID;
        this.isVIP = isVIP;
        this.onCooldown = onCooldown;
        this.isSecret = false;
    }

    public boolean isSecret() {
        return isSecret;
    }

    public void setSecret(boolean secret) {
        isSecret = secret;
    }

    /**
     * @Getter
     */


    public String getKitName() {
        return kitName;
    }
    public int getKitID() {
        return kitID;
    }
    public boolean isVIP() {
        return isVIP;
    }
    public Tier[] getKitTier() {
        return tier;
    }
    public boolean isOnCooldown() {
        return onCooldown;
    }

    public Tier getKitTierByLevel(int level)
    {
        List<Tier> tier = new ArrayList<>(Arrays.asList(this.tier));
        if(tier.isEmpty()) return null;
        for(Tier t : tier)
        {
            if(t.getLevel() == level)
            {
                return t;
            }
        }
        return null;
    }

    /**
     * @Setter
     */

    public void setCooldown(boolean onCooldown) {
        this.onCooldown = onCooldown;
    }

    /**
     * @Checking_Method
     */

    public void whenRightClick(PlayerInteractEvent event) { }
    public void whenRightClickOnPlayer(PlayerInteractEntityEvent event) { }
    public void whenLeftClickOnBlock(PlayerInteractEvent event){ }
    public void whenAttacking(EntityDamageByEntityEvent event){ }
    public void whenGetHit(EntityDamageByEntityEvent event){ }
    public void whenArrowHit(EntityDamageByEntityEvent event) { }
    public void whenHittingPlayerWithSnowBall(EntityDamageByEntityEvent event) { }
    public void whenKillingEntity(EntityDeathEvent event){ }
    public void whenKillingPlayer(PlayerDeathEvent event) { }
    public void whenDismount(EntityDismountEvent event){ }
    public void whenHorseHitting(EntityDamageByEntityEvent event){ }
    public void whenDamageByPearl(PlayerTeleportEvent event) { }
    public void whenFalling(EntityDamageEvent event) { }
    public void whenSneaking(PlayerToggleSneakEvent event){ }
    public void whenWalkingOnSponge(PlayerMoveEvent event) { }
    public void whenPlacingTNT(BlockPlaceEvent event) { }
    public void whenDie(PlayerDeathEvent event) { }
    public void whenBreakBlock(BlockBreakEvent event) { event.setCancelled(true); }
    public void whenPlaceBlock(BlockPlaceEvent event) { event.setCancelled(true); }



    @Override
    public String toString() {
        return "Kit{" +
                "kitName='" + kitName + '\'' +
                ", kitID=" + kitID +
                ", isVIP=" + isVIP +
                ", tier=" + Arrays.toString(tier) +
                ", onCooldown=" + onCooldown +
                '}';
    }
}
