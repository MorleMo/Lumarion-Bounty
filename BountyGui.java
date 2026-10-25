package lumarion.lumarionBounty.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BountyGui {


    public BountyGui(Player playerToShow, Map<OfflinePlayer, Double> bounties) {


        Inventory gui = Bukkit.createInventory(null, 54, "§l§cBounty");

        int slot = 0;

        // 2. Durch alle Kopfgelder aus der bounties.yml iterieren
        for (Map.Entry<OfflinePlayer, Double> entry : bounties.entrySet()) {
            if (slot >= 54) break; // Bricht ab, wenn das Inventar voll ist

            OfflinePlayer target = entry.getKey();
            double amount = entry.getValue();

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();

            if (meta != null) {
                meta.setOwningPlayer(target);

                // Namen sicher abfragen
                String displayName = target.getName() != null ? target.getName() : "Unbekannt";
                meta.setDisplayName(ChatColor.GOLD + displayName);

                // Lore (Beschreibung) mit Kopfgeld setzen
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Kopfgeld: " + ChatColor.GREEN + amount + "$");

                meta.setLore(lore);

                head.setItemMeta(meta);
            }

            // Kopf in das GUI setzen
            gui.setItem(slot, head);
            slot++;
        }

        // 3. GUI für den Spieler öffnen
        playerToShow.openInventory(gui);
    }
}