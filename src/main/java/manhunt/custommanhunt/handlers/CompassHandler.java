package handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import manhunt.custommanhunt.CustomManhunt;

public class CompassHandler implements Listener{
	CustomManhunt currentPlugin;
	public CompassHandler(CustomManhunt plugin) {
		currentPlugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onCompassInteract(PlayerInteractEvent event) {
		if (event.getAction()==Action.RIGHT_CLICK_AIR) {
			if (event.getItem() != null) {
				if(event.getMaterial()==Material.COMPASS) {
					Player player = event.getPlayer();
					player.setCompassTarget(currentPlugin.getRunner().getLocation());
				}
			}
		}
	}
}
