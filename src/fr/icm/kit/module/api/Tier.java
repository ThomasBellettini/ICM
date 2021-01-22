package fr.icm.kit.module.api;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Tier
{

    private int level;
    private int price;
    private ArrayList<ItemStack> composition;
    private String kitName;
    private Presentation presentation;
    private int cooldown;


    public Tier(int level, int price, ArrayList<ItemStack> composition, String kitName, Presentation presentation, int cooldown) {
        this.level = level;
        this.price = price;
        this.composition = composition;
        this.kitName = kitName;
        this.presentation = presentation;
        this.cooldown = cooldown;
    }

    /**
     * @Getter
     */

    public int getPrice() { return price; }
    public int getLevel() { return level; }
    public ArrayList<ItemStack> getComposition() { return composition; }
    public String getKitName() { return kitName; }
    public Presentation getPresentation() { return presentation; }
    public int getCooldown() { return cooldown; }

    /**
     * @Setter
     */


    @Override
    public String toString() {
        return "Tier{" +
                "level=" + level +
                ", price=" + price +
                ", composition=" + composition +
                ", kitName='" + kitName + '\'' +
                ", presentation=" + presentation +
                ", cooldown=" + cooldown +
                '}';
    }
}
