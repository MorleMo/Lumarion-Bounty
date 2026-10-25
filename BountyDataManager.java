package lumarion.lumarionBounty.manager;

import lumarion.lumarionBounty.LumarionBounty;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BountyDataManager {

    private final LumarionBounty plugin;
    private File file;
    private FileConfiguration config;

    public BountyDataManager(LumarionBounty plugin) {
        this.plugin = plugin;
        setupFile();
    }


    private void setupFile() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        file = new File(plugin.getDataFolder(), "bounties.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Konnte bounties.yml nicht erstellen!");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }


    public void setBounty(OfflinePlayer target, double amount) {

        config.set("bounties." + target.getUniqueId().toString() + ".name", target.getName());
        config.set("bounties." + target.getUniqueId().toString() + ".amount", amount);
        save();
    }

    // 3. Kopfgeld abrufen
    public double getBounty(OfflinePlayer target) {
        return config.getDouble("bounties." + target.getUniqueId().toString() + ".amount", 0.0);
    }

    // 4. Alle Kopfgelder aus der bounties.yml in eine Map laden (für das GUI)
    public Map<OfflinePlayer, Double> loadAllBounties() {
        Map<OfflinePlayer, Double> bounties = new HashMap<>();

        if (!config.contains("bounties")) {
            return bounties;
        }

        for (String uuidString : config.getConfigurationSection("bounties").getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                double amount = config.getDouble("bounties." + uuidString + ".amount");
                bounties.put(player, amount);
            } catch (IllegalArgumentException ignored) {
                // Falls die UUID ungültig ist
            }
        }
        return bounties;
    }

    // 5. Änderungen in die Datei schreiben
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Konnte bounties.yml nicht speichern!");
        }
    }
}