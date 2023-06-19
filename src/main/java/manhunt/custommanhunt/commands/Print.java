package manhunt.custommanhunt.commands;

import manhunt.custommanhunt.CustomManhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Print implements CommandExecutor {
    private CustomManhunt manhunt;

    public Print(CustomManhunt control){
        manhunt = control;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
        Bukkit.broadcastMessage(manhunt.getCurrentTeams());
        return true;
    }
}
