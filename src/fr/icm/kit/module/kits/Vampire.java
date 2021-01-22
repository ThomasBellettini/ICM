package fr.icm.kit.module.kits;

import fr.icm.PvPBox;
import fr.icm.entity.ICMPlayer;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Vampire extends Kit{

    Tier[] tier = new Tier[3];

    public Vampire() {
        super("Vampire", 30, true, false);

        Presentation p = new Presentation("§cKit Vampire", Arrays.asList("Je suce le sang de mes victimes !"), Material.REDSTONE);

        tier[0] = new Tier(1, 0, tierOne(), getKitName(), p, 0);
        tier[1] = new Tier(2, 100, tierOne(), getKitName(), p, 0);
        tier[2] = new Tier(3, 300, tierOne(), getKitName(), p, 0);

        super.tier = this.tier;
    }

    private ArrayList<ItemStack> tierOne() {
        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(0,0,0));
        legsMeta.setColor(Color.fromRGB(0,0,0));
        chestMeta.setColor(Color.fromRGB(0,0,0));
        helmetMeta.setColor(Color.fromRGB(0,0,0));
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);


        stuff.add(new ItemStack(Material.STONE_SWORD));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }



    @Override
    public void whenKillingPlayer(PlayerDeathEvent event) {

        Player damager = (Player) event.getEntity().getKiller();

        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer(damager);
        int kitLevel = icmPlayer.getKitLevel(this.getKitName());

        switch (kitLevel)
        {
            case 1:
                if(damager.getHealth() + 2 > damager.getMaxHealth())
                {
                    damager.setHealth(damager.getMaxHealth());
                    break;
                }
                damager.setHealth(damager.getHealth() + 2);
                break;
            case 2:
                if(damager.getHealth() + 4 > damager.getMaxHealth())
                {
                    damager.setHealth(damager.getMaxHealth());
                    break;
                }
                damager.setHealth(damager.getHealth() + 4);
                break;
            case 3:
                if(damager.getHealth() + 6 > damager.getMaxHealth())
                {
                    damager.setHealth(damager.getMaxHealth());
                    break;
                }
                damager.setHealth(damager.getHealth() + 6);
                break;
        }
    }

    @Override
    public void whenKillingEntity(EntityDeathEvent event) {
        Player damager = event.getEntity().getKiller();
        ICMPlayer icmPlayer = PvPBox.getPlayerLoader.getICMByPlayer((Player) event.getEntity().getKiller());
        int kitLevel = icmPlayer.getKitLevel(this.getKitName());

        switch (kitLevel)
        {
            case 1:
                if(damager.getHealth() + 2 > damager.getMaxHealth())
                {
                    damager.setHealth(damager.getMaxHealth());
                    break;
                }
                damager.setHealth(damager.getHealth() + 1);
                break;
            case 2:
                if(damager.getHealth() + 4 > damager.getMaxHealth())
                {
                    damager.setHealth(damager.getMaxHealth());
                    break;
                }
                damager.setHealth(damager.getHealth() + 2);
                break;
            case 3:
                if(damager.getHealth() + 6 > damager.getMaxHealth())
                {
                    damager.setHealth(damager.getMaxHealth());
                    break;
                }
                damager.setHealth(damager.getHealth() + 3);
                break;
        }
    }
}
