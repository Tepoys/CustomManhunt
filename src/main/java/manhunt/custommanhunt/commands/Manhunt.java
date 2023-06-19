package manhunt.custommanhunt.commands;

import manhunt.custommanhunt.CustomManhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Manhunt implements CommandExecutor {

    private CustomManhunt manhunt;

    public Manhunt(CustomManhunt manhunt){
        this.manhunt = manhunt;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length == 1){
            if(args[0].equals("start")){
                int outcome = manhunt.start();
                if(outcome == 1){
                    Bukkit.broadcastMessage("There are no hunters selected. Can not start manhunt.");
                } else if(outcome == 2){
                    Bukkit.broadcastMessage("There is no runner selected. Can not start manhunt.");
                }
                return true;
            }else if(args[0].equals("stop") || args[0].equals("end")){
                manhunt.setGameInProgress(false, 0);
                return true;
            }else if(args[0].equals("help")){
                //help info WIP
                commandSender.sendMessage("Currently WIP");
                return true;
            } else {
                Bukkit.broadcastMessage("Unrecognized command");
                return false;
            }
        } else{
            return false;
        }
    }
}
