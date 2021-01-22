package fr.icm.kit.module.utils;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class DuelBox
{

    private Location centralLocation;
    private ICMPlayer owner;
    private ICMPlayer dueler;

    private Location ownerLoc;
    private Location otherLoc;

    private Location ownerLocTP;
    private Location otherLocTP;

    private ArrayList<Location> soon = new ArrayList<>();

    private Location lastOther;
    private Location lastOwner;

    public DuelBox(Location centralLocation, ICMPlayer owner, ICMPlayer dueler, Location lastOwner, Location lastOther) {
        this.centralLocation = centralLocation;
        this.owner = owner;
        this.dueler = dueler;
        this.lastOther = lastOther;
        this.lastOwner = lastOwner;
    }

    public void summon()
    {

        ArrayList<Location> platform = new ArrayList<>();

        int radius = 5;

        int maxX = centralLocation.getBlock().getLocation().getBlockX() +radius;
        int minX = centralLocation.getBlock().getLocation().getBlockX() -radius;
        int maxZ = centralLocation.getBlock().getLocation().getBlockZ() +radius;
        int minZ = centralLocation.getBlock().getLocation().getBlockZ() -radius;

        for(int x = minX; x != maxX + 1; x++)
        {
            for (int z = minZ; z != maxZ + 1; z++)
            {
                Location loc = new Location(centralLocation.getWorld(), x, centralLocation.getY() + 50, z);
                if(loc.getBlock().getType() != Material.AIR)
                {
                    Bukkit.getPlayer(owner.getName()).sendMessage("§c[!] Erreur, un obstacle ce trouve au dessus de toi, merci de t'éloigner !");
                    return;
                }
                platform.add(loc);
            }
        }

        if(getNortWall(radius) == null) return;
        if(getWesttWall(radius) == null) return;
        if(getSouthWall(radius) == null) return;
        if(getEastWall(radius) == null) return;

        this.ownerLoc = new Location(centralLocation.getWorld(), centralLocation.getX() + (radius-2), centralLocation.getY()+51, centralLocation.getZ() + (radius-2));
        this. otherLoc = new Location(centralLocation.getWorld(), centralLocation.getX() - (radius-2), centralLocation.getY()+51, centralLocation.getZ() - (radius-2));

        this.ownerLocTP = new Location(centralLocation.getWorld(), centralLocation.getX() + (radius-2), centralLocation.getY()+52, centralLocation.getZ() + (radius-2));
        this.otherLocTP = new Location(centralLocation.getWorld(), centralLocation.getX() - (radius-2), centralLocation.getY()+52, centralLocation.getZ() - (radius-2));


        ownerLoc.getBlock().setType(Material.BEDROCK);
        otherLoc.getBlock().setType(Material.BEDROCK);

        Bukkit.getPlayer(dueler.getName()).teleport(otherLocTP);
        Bukkit.getPlayer(owner.getName()).teleport(ownerLocTP);

        Bukkit.getPlayer(dueler.getName()).sendMessage("§e§l[!] §eCréation de votre arène en cours ...");
        Bukkit.getPlayer(owner.getName()).sendMessage("§e§l[!] §eCréation de votre arène en cours ...");

        placeArena(Material.GLASS, platform, getSouthWall(radius), getWesttWall(radius), getNortWall(radius), getEastWall(radius));


    }



    private ArrayList<Location> getNortWall(int radius)
    {
        ArrayList<Location> northWall = new ArrayList<>();
        Location wall =  new Location(centralLocation.getWorld(), centralLocation.getX(), centralLocation.getY() + 50 + radius, centralLocation.getZ() - radius);

        int maxX = wall.getBlock().getLocation().getBlockX() +radius;
        int minX = wall.getBlock().getLocation().getBlockX() -radius;
        int maxY = wall.getBlock().getLocation().getBlockY() +radius ;
        int minY = wall.getBlock().getLocation().getBlockY() -radius ;

        for(int y = minY; y != maxY + 1; y++)
        {
            for (int x = minX; x != maxX + 1; x++)
            {
                if(y > wall.getY())
                    continue;

                Location loc = new Location(centralLocation.getWorld(), x, y, wall.getZ());

                if(loc.getBlock().getType() != Material.AIR)
                {
                    Bukkit.getPlayer(owner.getName()).sendMessage("§c[!] Erreur, un obstacle ce trouve au dessus de toi, merci de t'éloigner !");
                    return null;
                }

                northWall.add(loc);
            }
        }
        return northWall;
    }

    private ArrayList<Location> getSouthWall(int radius)
    {
        ArrayList<Location> southWall = new ArrayList<>();
        Location wall =  new Location(centralLocation.getWorld(), centralLocation.getX(), centralLocation.getY() + 50 + radius, centralLocation.getZ() + radius);

        int maxX = wall.getBlock().getLocation().getBlockX() +radius;
        int minX = wall.getBlock().getLocation().getBlockX() -radius;
        int maxY = wall.getBlock().getLocation().getBlockY() +radius ;
        int minY = wall.getBlock().getLocation().getBlockY() -radius ;

        for(int y = minY; y != maxY + 1; y++)
        {
            for (int x = minX; x != maxX + 1; x++)
            {
                if(y > wall.getY())
                    continue;


                Location loc = new Location(centralLocation.getWorld(), x, y, wall.getZ());
                if(loc.getBlock().getType() != Material.AIR)
                {
                    Bukkit.getPlayer(owner.getName()).sendMessage("§c[!] Erreur, un obstacle ce trouve au dessus de toi, merci de t'éloigner !");
                    return null;
                }
                southWall.add(loc);
            }
        }
        return southWall;
    }

    private ArrayList<Location> getWesttWall(int radius)
    {
        ArrayList<Location> westWall = new ArrayList<>();
        Location wall =  new Location(centralLocation.getWorld(), centralLocation.getX() + radius, centralLocation.getY() + 50 + radius, centralLocation.getZ());

        int maxY = wall.getBlock().getLocation().getBlockY() +radius ;
        int minY = wall.getBlock().getLocation().getBlockY() -radius ;

        int maxZ = wall.getBlock().getLocation().getBlockZ() +radius;
        int minZ = wall.getBlock().getLocation().getBlockZ() -radius;

        for(int z = minZ; z != maxZ + 1; z++) {
            for (int y = minY; y != maxY + 1; y++)
            {
                if(y > wall.getY())
                    continue;
                Location loc = new Location(centralLocation.getWorld(), wall.getX(), y, z);
                if(loc.getBlock().getType() != Material.AIR)
                {
                    Bukkit.getPlayer(owner.getName()).sendMessage("§c[!] Erreur, un obstacle ce trouve au dessus de toi, merci de t'éloigner !");
                    return null;
                }
                westWall.add(loc);
            }
        }
        return westWall;
    }

    private ArrayList<Location> getEastWall(int radius)
    {
        ArrayList<Location> eastWall = new ArrayList<>();
        Location wall =  new Location(centralLocation.getWorld(), centralLocation.getX() - radius, centralLocation.getY() + 50 + radius, centralLocation.getZ());

        int maxY = wall.getBlock().getLocation().getBlockY() +radius ;
        int minY = wall.getBlock().getLocation().getBlockY() -radius ;

        int maxZ = wall.getBlock().getLocation().getBlockZ() +radius;
        int minZ = wall.getBlock().getLocation().getBlockZ() -radius;

        for(int z = minZ; z != maxZ + 1; z++) {
            for (int y = minY; y != maxY + 1; y++)
            {
                if(y > wall.getY())
                    continue;
                Location loc = new Location(centralLocation.getWorld(), wall.getX(), y, z);
                if(loc.getBlock().getType() != Material.AIR)
                {
                    Bukkit.getPlayer(owner.getName()).sendMessage("§c[!] Erreur, un obstacle ce trouve au dessus de toi, merci de t'éloigner !");
                    return null;
                }

                eastWall.add(loc);
            }
        }
        return eastWall;
    }

    private void placeArena(Material material, ArrayList<Location> platform, ArrayList<Location> southWall, ArrayList<Location> westtWall, ArrayList<Location> nortWall, ArrayList<Location> eastWall)
    {
        Player others = Bukkit.getPlayer(dueler.getName());
        Player owners = Bukkit.getPlayer(owner.getName());
        new BukkitRunnable()
        {
            @Override
            public void run() {
                if(platform.isEmpty() && southWall.isEmpty() &&
                westtWall.isEmpty() && nortWall.isEmpty() && eastWall.isEmpty())
                    {
                        Player player1 = Bukkit.getPlayer(dueler.getName());
                        Player player2 = Bukkit.getPlayer(owner.getName());
                        if(player1 != null)
                            player1.sendMessage("§6§l[!]§a Arène finie, vous pouvez combattre avec Honneur !");
                        if(player2 != null)
                            player2.sendMessage("§6§l[!]§a Arène finie, vous pouvez combattre avec Honneur !");
                        otherLoc.getBlock().setType(Material.AIR);
                        ownerLoc.getBlock().setType(Material.AIR);
                        cancel();
                    }
                if(!platform.isEmpty()) {
                    if( platform.get(0).getBlock() != null && platform.get(0).getBlock().getType() != Material.STONE)
                    {
                        soon.add(platform.get(0));
                        platform.get(0).getBlock().setType(Material.STONE);
                        platform.remove(0);
                    }
                    else
                    {
                        soon.add(platform.get(0));
                        platform.remove(0);
                    }
                }


                if(!southWall.isEmpty())
                {
                    if( southWall.get(0).getBlock() != null && southWall.get(0).getBlock().getType() != Material.STONE)
                   {
                       soon.add(southWall.get(0));
                       southWall.get(0).getBlock().setType(Material.IRON_FENCE);
                       southWall.remove(0);
                   }
                    else
                    {
                        soon.add(southWall.get(0));
                        southWall.remove(0);
                    }
                }

                if(!nortWall.isEmpty())
                {
                    if( nortWall.get(0).getBlock() != null && nortWall.get(0).getBlock().getType() != Material.STONE)
                    {
                        soon.add(nortWall.get(0));
                        nortWall.get(0).getBlock().setType(Material.IRON_FENCE);
                        nortWall.remove(0);
                        }
                    else
                    {
                        soon.add(nortWall.get(0));
                        nortWall.remove(0);
                    }
                }

                if(!eastWall.isEmpty())
                {
                    if( eastWall.get(0).getBlock() != null && eastWall.get(0).getBlock().getType() != Material.STONE)
                    {
                        soon.add(eastWall.get(0));
                        eastWall.get(0).getBlock().setType(Material.IRON_FENCE);
                        eastWall.remove(0);
                    }
                    else
                    {
                        soon.add(eastWall.get(0));
                        eastWall.remove(0);
                    }
                }

                if(!westtWall.isEmpty())
                {
                    if( westtWall.get(0).getBlock() != null && westtWall.get(0).getBlock().getType() != Material.STONE)
                    {
                        soon.add(westtWall.get(0));
                        westtWall.get(0).getBlock().setType(Material.IRON_FENCE);
                        westtWall.remove(0);

                    }
                    else
                    {
                        soon.add(westtWall.get(0));
                        westtWall.remove(0);
                    }
                }


                if(ownerLocTP.getX() != owners.getLocation().getX() ||
                        ownerLocTP.getZ() != owners.getLocation().getZ() ||
                            ownerLocTP.getY() != owners.getLocation().getY())
                {

                    owners.teleport(new Location(owners.getLocation().getWorld(), owners.getLocation().getX(), owners.getLocation().getY(), owners.getLocation().getZ()));
                }

                if(otherLocTP.getX() != others.getLocation().getX() ||
                        otherLocTP.getZ() != others.getLocation().getZ() ||
                        otherLocTP.getY() != others.getLocation().getY())
                {
                    others.teleport(new Location(others.getLocation().getWorld(), others.getLocation().getX(), others.getLocation().getY(), otherLocTP.getZ()));
                }

            }
        }.runTaskTimer(PvPBox.getInstance, 0, 1);
    }

    public void setLastOther(Location lastOther) {
        this.lastOther = lastOther;
    }

    public void setLastOwner(Location lastOwner) {
        this.lastOwner = lastOwner;
    }

    public void remove()
    {
            if(lastOther != null)
                Bukkit.getPlayer(dueler.getName()).teleport(lastOther);
            if(lastOwner != null)
                Bukkit.getPlayer(owner.getName()).teleport(lastOwner);
            for(Location l : soon)
            {
                l.getBlock().setType(Material.AIR);
            }
    }

}
