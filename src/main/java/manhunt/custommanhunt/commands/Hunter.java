package manhunt.custommanhunt.commands;

import manhunt.custommanhunt.CustomManhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hunter implements CommandExecutor {

    private CustomManhunt manhunt;

    public Hunter(CustomManhunt control){
        manhunt = control;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {

        if(args.length == 2){
            //add or remove player
            Player pl = Bukkit.getPlayer(args[1]);
            if(pl==null){
                sender.sendMessage("\"" + args[1] + "\" is not online or a valid player name.");
                return false;
            }

            if(args[0].equals("add")){
                if(!manhunt.addHunter(pl)){
                    sender.sendMessage("\"" + args[1] + "\" is already a runner.");
                }
                return true;
            }else if(args[0].equals("remove")){
                if(!manhunt.removeHunter(pl)){
                    sender.sendMessage("\"" + args[1] + "\" is not a hunter.");
                }
                return true;
            }else{
                return false;
            }
        } else if(args.length == 1) {
            //clear
            if(args[0].equals("clear")){
                manhunt.clearHunters();
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }
}
