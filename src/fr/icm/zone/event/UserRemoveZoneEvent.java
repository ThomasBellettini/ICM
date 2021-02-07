package fr.icm.zone.event;

import fr.icm.PvPBox;
import fr.icm.zone.api.ICMZone;
import fr.icm.zone.utils.ZoneLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class UserRemoveZoneEvent implements Listener {

    ZoneLoader zoneLoader = PvPBox.getZoneLoader;

    @EventHandler
    public void userClickOnRemoveZoneInventory(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        Inventory inventory = e.getClickedInventory();
        ItemStack itemStack = e.getCurrentItem();

        if (e.getCurrentItem() == null || e.getInventory() == null)
            return;
        if (inventory.getName().startsWith("§7(§c")) {
            e.setCancelled(true);
            String zone_Name = inventory.getName().replace("§7(§c", "").replace("§7)", "");
            ArrayList<ICMZone> icmZones = new ArrayList<>(zoneLoader.getIcmZones());
            icmZones.removeIf(z -> !z.getName().equalsIgnoreCase(zone_Name));
            if (icmZones.isEmpty()) {
                p.closeInventory();
                return;
            }
            ICMZone icmZone = icmZones.get(0);
            if(itemStack != null && itemStack.hasItemMeta()) {
                if(itemStack.getData().getData() == 13) {
                    icmZone.delete();
                    p.sendMessage("§7[§eICM§7] §cVous venez de supprimer la zone " + icmZone.getName().substring(0, 1).toUpperCase() + icmZone.getName().substring(1));
                    p.closeInventory();
                } else if (itemStack.getData().getData() == 14)
                    p.closeInventory();
            }
        }
    }
}
