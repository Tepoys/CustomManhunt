package manhunt.custommanhunt.handlers;

import manhunt.custommanhunt.CustomManhunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandler implements Listener {

    private CustomManhunt manhunt;

    public PlayerHandler(CustomManhunt manhunt) {
        this.manhunt = manhunt;
        Bukkit.getPluginManager().registerEvents(this, manhunt);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player pl = event.getPlayer();
        if(manhunt.removeHunter(pl)){
            Bukkit.broadcastMessage("\"" + pl.getName() + "\" left the game, removed from hunter.");
        }else if(manhunt.getRunner()==pl){
            manhunt.setRunner(null);
            Bukkit.broadcastMessage("\"" + pl.getName() + "\" left the game, removed from runner.");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(manhunt.isGameInProgress() && event.getEntity() == manhunt.getRunner()){
            manhunt.setGameInProgress(false);
            Bukkit.broadcastMessage(ChatColor.GREEN+"Runner has died, the hunters win!"+ChatColor.WHITE);
        }
    }


}
