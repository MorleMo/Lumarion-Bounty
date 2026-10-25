package lumarion.lumarionBounty.commands;

import lumarion.lumarionBounty.LumarionBounty;
import lumarion.lumarionBounty.gui.BountyGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BountyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Nur Spieler können diesen Befehl nutzen.");
            return true;
        }

        Player player = (Player) sender;
        LumarionBounty plugin = LumarionBounty.getInstance();


        if (args.length == 0) {
            Map<OfflinePlayer, Double> bounties = plugin.getDataManager().loadAllBounties();
            new BountyGui(player, bounties);
            return true;
        }


        if (args.length >= 3 && args[0].equalsIgnoreCase("add")) {
            String targetName = args[1];
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

            double amount;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Bitte gib einen gültigen Betrag an!");
                return true;
            }


            double currentBounty = plugin.getDataManager().getBounty(target);
            double newBounty = currentBounty + amount;
            plugin.getDataManager().setBounty(target, newBounty);


            String rawMessage = plugin.getConfig().getString("messages.bounty-set", "&aAuf &e%target% &awurde ein Kopfgeld von &e%amount%$ &agesetzt!");
            String prefix = plugin.getConfig().getString("messages.prefix", "&7[&6Bounty&7] ");

            String formattedMessage = rawMessage
                    .replace("%target%", target.getName() != null ? target.getName() : targetName)
                    .replace("%amount%", String.valueOf(amount));

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + formattedMessage));
            return true;
        }

        player.sendMessage(ChatColor.RED + "Verwendung: /bounty oder /bounty add <Spieler> <Betrag>");
        return true;
    }
}