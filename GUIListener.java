package lumarion.lumarionBounty.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("§l§cBounty-Liste")) {

            // Verhindert, dass Spieler Items herausziehen
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;

            Player player = (Player) event.getWhoClicked();

            // Wenn auf einen Spielerkopf geklickt wird
            if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                player.sendMessage(ChatColor.GREEN + "Du kannst das nicht machen!");
            }
        }
    }
}