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
                int value;
                if((value = manhunt.addHunter(pl)) == 1){
                    sender.sendMessage("\"" + args[1] + "\" is already a runner.");
                }else if(value == 2){
                    sender.sendMessage("\"" + args[1] + "\" is already a hunter.");
                }else{
                    sender.sendMessage("\"" + args[1] + "\" successfully added to hunters.");
                }
                return true;
            }else if(args[0].equals("remove")){
                if(!manhunt.removeHunter(pl)){
                    sender.sendMessage("\"" + args[1] + "\" is not a hunter.");
                }else{
                    sender.sendMessage("\"" + args[1] + "\" successfully removed");
                }
                return true;
            }else{
                return false;
            }
        } else if(args.length == 1) {
            //clear
            if(args[0].equals("clear")){
                manhunt.clearHunters();
                sender.sendMessage("successfully cleared hunter group");
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }
}
