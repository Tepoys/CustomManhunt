package manhunt.custommanhunt;

import manhunt.custommanhunt.commands.Hunter;
import manhunt.custommanhunt.commands.Manhunt;
import manhunt.custommanhunt.commands.Print;
import manhunt.custommanhunt.commands.Runner;

import manhunt.custommanhunt.handlers.EntitiyHandler;
import manhunt.custommanhunt.handlers.PlayerHandler;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import manhunt.custommanhunt.handlers.CompassHandler;

import java.util.ArrayList;

public final class CustomManhunt extends JavaPlugin {

    private boolean gameInProgress = false;
    private ArrayList<Player> hunters = new ArrayList<Player>();
    private Player runner;
    private CompassHandler compassHandler;
    private Location lastLocationOverworld;
    private Location lastLocationNether;

    /**
     *
     * @param player player to be added
     * @return int 0 for success, 1 for player adding is already runner, 2 for player adding is already a hunter
     */
    public int addHunter(Player player){
        if(runner == player) return 1;
        else if(isHunter(player)) return 2;
        else{
            hunters.add(player);
            return 0;
        }
    }

    public boolean isHunter(Player player){ return hunters.contains(player); }

    public boolean removeHunter(Player p){
         return hunters.remove(p);
    }

    public void clearHunters() {
        hunters.clear();
    }

    public Player getRunner(){
        return runner;
    }

    public void setRunner(Player pl){
        runner = pl;
    }

    public String getCurrentTeams(){
        String hunter = "Hunters:";
        if(hunters.size()!=0){
            for(Player i : hunters){
                hunter += "\n" + i.getName();
            }
        }else{
            hunter+="\n" + ChatColor.RED + "No hunters selected" + ChatColor.WHITE;
        }

        String run = "Runner:";
        if(runner!=null){
            run += "\n" + runner.getName();
        }else{
            run+="\n" + ChatColor.RED + "No runner selected" + ChatColor.WHITE;
        }

        return hunter + "\n" + run;
    }

    /**
     *
     * @return int 1 = no hunters 2 = no runners 0 = success
     */
    public int start(){
        if(hunters.size() < 1){
            return 1;
        }
        if(runner==null){
            return 2;
        }
        gameInProgress = true;
        giveHuntersCompass();
        return 0;
    }

    public void giveHuntersCompass(){
        for(Player i : hunters){
            i.getInventory().clear();
            giveCompass(i);
        }
    }

    public void giveCompass(Player pl){
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        compass.addEnchantment(Enchantment.VANISHING_CURSE, 1);
        pl.getInventory().setItem(8, compass);
        compassHandler.setTargetRunner(pl, compass);
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }
    public void setGameInProgress(boolean bool, int reason) {
        gameInProgress = bool;
        if(reason == 1){
            Bukkit.broadcastMessage(ChatColor.GREEN+"The ender dragon has been killed! The runner wins!"+ChatColor.WHITE);
        } else if (reason == 2) {
            Bukkit.broadcastMessage(ChatColor.GREEN+"Runner has died, the hunters win!"+ChatColor.WHITE);
        } else if(reason == 0){
            Bukkit.broadcastMessage(ChatColor.RED+"Manhunt canceled by command"+ChatColor.WHITE);
        }
    }

    public Location getRunnerLocation(Player hunter){
        if(hunter.getLocation().getWorld().equals(runner.getLocation().getWorld())){
            Bukkit.getLogger().info(hunter.getName() + "is in same world");
            return runner.getLocation();
        } else if(hunter.getLocation().getWorld().equals(World.Environment.NORMAL)){
            Bukkit.getLogger().info(hunter.getName() + "is in same overworld, runner is not");
            return lastLocationOverworld;
        } else if(hunter.getLocation().getWorld().equals(World.Environment.NETHER)){
            Bukkit.getLogger().info(hunter.getName() + "is in same nether, runner is not");
            return lastLocationNether;
        } else {
            return runner.getLocation();
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("CustomManhunt plugin started");
        compassHandler = new CompassHandler(this);
        new PlayerHandler(this);
        new EntitiyHandler(this);
        getCommand("hunter").setExecutor(new Hunter(this));
        getCommand("runner").setExecutor(new Runner(this));
        getCommand("print").setExecutor(new Print(this));
        getCommand("manhunt").setExecutor(new Manhunt(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("CustomManhunt plugin stopped");
    }


    public void setLastLocationOverworld(Location lastLocationOverworld) {
        this.lastLocationOverworld = lastLocationOverworld;
    }

    public void setLastLocationNether(Location lastLocationNether) {
        this.lastLocationNether = lastLocationNether;
    }
}
