package fr.icm.kit.module.kits.admin;

import fr.icm.PvPBox;
import fr.icm.kit.module.api.Kit;
import fr.icm.kit.module.api.Presentation;
import fr.icm.kit.module.api.Tier;
import fr.icm.utils.NMSTitle;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class PvpKit extends Kit {

    Tier[] tier = new Tier[1];
    NMSTitle nmsTitle = PvPBox.nmsTitle;

    public PvpKit() {
        super("adminpvpkit", 50, true, false);
        setSecret(true);
        Presentation p = new Presentation("§6» Kit AdminPvPKit", Arrays.asList(" ", "§cCeci est un kit Admin,", "§cNe mourrez pas !"), Material.BEDROCK);

        tier[0] = new Tier(1, 0, oneTier(), getKitName(), p, 0);
        super.tier = this.tier;
    }

    private ArrayList<ItemStack> oneTier() {

        ArrayList<ItemStack> stuff = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) legs.getItemMeta();
        ItemMeta chestMeta = chest.getItemMeta();
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(255,255,255));
        legsMeta.setColor(Color.fromRGB(255,255,255));
        helmetMeta.setColor(Color.fromRGB(255,255,255));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        legsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        boots.setItemMeta(bootsMeta);
        legs.setItemMeta(legsMeta);
        chest.setItemMeta(chestMeta);
        helmet.setItemMeta(helmetMeta);

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        sword.setItemMeta(swordMeta);


        stuff.add(sword);
        stuff.add(new ItemStack(Material.BOW));
        stuff.add(new ItemStack(Material.ARROW, 24));
        stuff.add(new ItemStack(Material.FISHING_ROD, 1));
        stuff.add(new ItemStack(Material.GOLDEN_APPLE, 64));
        stuff.add(boots);
        stuff.add(legs);
        stuff.add(chest);
        stuff.add(helmet);

        return stuff;
    }

    @Override
    public void whenAttacking(EntityDamageByEntityEvent event) {
        super.whenAttacking(event);
        Player p = (Player) event.getDamager();

        if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.IRON_SWORD) {
            Player player = (Player) event.getEntity();
            if (player.getHealth() + 2.0 <= 20.0)
                player.setHealth(player.getHealth() + 2);
            nmsTitle.sendTitle(p, 0, 30, 10, "§7[§cICM§7]§c Duel Admin", "§cTu triches en duel, T'es Sah dans ce que tu fait??");
        } else if (!p.isSneaking()) {
            p.sendMessage("§dDieu: §b§oA chaque fois que tu taperas, la personne gagneras, trouve une astuce !");
            p.setHealth(0);
        }

    }
}
