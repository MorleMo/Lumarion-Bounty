package lumarion.lumarionBounty;

import lumarion.lumarionBounty.commands.BountyCommand;
import lumarion.lumarionBounty.gui.GUIListener;
import lumarion.lumarionBounty.manager.BountyDataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LumarionBounty extends JavaPlugin {

    private static LumarionBounty instance;
    private BountyDataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // DataManager initialisieren
        this.dataManager = new BountyDataManager(this);

        if (getCommand("bounty") != null) {
            getCommand("bounty").setExecutor(new BountyCommand());
        }

        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    public static LumarionBounty getInstance() {
        return instance;
    }

   
    public BountyDataManager getDataManager() {
        return dataManager;
    }
}