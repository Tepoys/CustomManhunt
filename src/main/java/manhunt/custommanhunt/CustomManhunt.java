package manhunt.custommanhunt;

import manhunt.custommanhunt.commands.Hunter;
import manhunt.custommanhunt.commands.Print;
import manhunt.custommanhunt.commands.Runner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import manhunt.custommanhunt.handlers.CompassHandler;

import java.util.ArrayList;

public final class CustomManhunt extends JavaPlugin {

    private boolean gameInProgress = false;
    private ArrayList<Player> hunters = new ArrayList<Player>();
    private Player runner;

    /**
     *
     * @param player player to be added
     * @return int 0 for success, 1 for player adding is already runner, 2 for player adding is already a hunter
     */
    public int addHunter(Player player){
        if(runner == player) return 1;
        else if(hunters.contains(player)) return 2;
        else{
            hunters.add(player);
            return 0;
        }
    }

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

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("CustomManhunt plugin started");
        new CompassHandler(this);
        getCommand("hunter").setExecutor(new Hunter(this));
        getCommand("runner").setExecutor(new Runner(this));
        getCommand("print").setExecutor(new Print(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("CustomManhunt plugin stopped");
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

        }
    }
}
