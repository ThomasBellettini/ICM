package fr.icm.kit.module.kits;

import fr.icm.PvPBox;
import fr.icm.entity.ICMParty;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class TimeMaster extends Kit {

    Tier[] tier = new Tier[1];

    public TimeMaster() {
        super("maitredutemps", 28, false, true);

        setSecret(true);

        Presentation p = new Presentation("§6» Kit Maître du temps", Arrays.asList("Je stop le temps"), Material.WATCH);
        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 30);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(0,255,255));
        chestMeta.setColor(Color.fromRGB(0,255,255));
        boots.setItemMeta(bootsMeta);
        chest.setItemMeta(chestMeta);

        stuff.add(new ItemStack(Material.IRON_SWORD));
        stuff.add(new ItemStack(Material.WATCH));
        stuff.add(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
        stuff.add(boots);
        stuff.add(chest);

        return stuff;
    }



    public ArrayList<Player> getNearbyPlayers(Player p, int radius) {
        ArrayList<Player> nearbyPlayers = new ArrayList<>();
        for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof Player)
                nearbyPlayers.add((Player) e);
        }
        return nearbyPlayers;
    }

    @Override
    public void whenRightClick(PlayerInteractEvent e) {
        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(e.getPlayer());
        ICMParty icmParty = PvPBox.getPartyLoader.getPartiesWithMember(icmPlayer);
        int kitLevel = icmPlayer.getKitLevel(this.getKitName());
        Player user = e.getPlayer();

        if (user.getItemInHand().getType() != Material.WATCH)
            return;

        if(icmPlayer.getCooldown() > 0)
        {
            Bukkit.getPlayer(icmPlayer.getName()).sendMessage("§c[!] Erreur tu es en cooldown pendant encore §6" + icmPlayer.getCooldown() + " §4secondes");
            return;
        }


        Tier tierKit = this.getKitTierByLevel(kitLevel);
        icmPlayer.setCooldown(tierKit.getCooldown());

        int radius;

        radius = 20;

        String slowMsg = "§6[!] §aVous venez de ralentir le(s) joueur(s) §b : ";

        if (icmParty == null){
            for (Player p : getNearbyPlayers(user, radius)) {
                slowMsg +=  p.getName() + ", ";
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*7, 1));
            }
            if(getNearbyPlayers(user, radius).size() == 1)
                e.getPlayer().sendMessage("§6[!] §aVous venez de ralentir le joueur §b : " + getNearbyPlayers(user, radius).get(0).getName());
            else if(getNearbyPlayers(user, radius).size() > 1)
                e.getPlayer().sendMessage(slowMsg);
            else
                e.getPlayer().sendMessage("§c[!] Vous n'avez ralenti Personne !");

        }
        else {
            for (Player p : getNearbyPlayers(user, radius)) {
                if(icmParty.convertArrayPlayer().contains(p))
                    continue;
                slowMsg +=  p.getName() + ", ";
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*7, 1));
            }
            if(getNearbyPlayers(user, radius).size() == 1)
                e.getPlayer().sendMessage("§6[!] §aVous venez de ralentir le joueur §b : " + getNearbyPlayers(user, radius).get(0).getName());
            else if(getNearbyPlayers(user, radius).size() > 1)
                e.getPlayer().sendMessage(slowMsg);
            else
                e.getPlayer().sendMessage("§c[!] Vous n'avez ralenti Personne !");
        }
    }
}
