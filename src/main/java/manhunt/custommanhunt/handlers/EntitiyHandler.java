package manhunt.custommanhunt.handlers;

import manhunt.custommanhunt.CustomManhunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntitiyHandler implements Listener {
    private CustomManhunt manhunt;

    public EntitiyHandler(CustomManhunt manhunt){
        this.manhunt = manhunt;
        Bukkit.getPluginManager().registerEvents(this, manhunt);
    }

    @EventHandler
    public void entityDeathEvent(EntityDeathEvent event){
        if(manhunt.isGameInProgress() && event.getEntity().getType() == EntityType.ENDER_DRAGON){
            Bukkit.broadcastMessage(ChatColor.GREEN+"The ender dragon has been killed! The runner wins!"+ChatColor.WHITE);
        }
    }
}
