package me.thefox.iivirtual;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Commands
{
    IIPlayerList players;
    public static ConsoleCommandSender console;

    public Commands(IIPlayerList players, ConsoleCommandSender console)
    {
        this.players = players;
        this.console = console;
    }

    public boolean Commands(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player)sender;
        boolean returner = false;

        returner = checkIILIST(sender, command, returner);
        returner = checkIIADD(sender, command, args, player, returner);

        return returner;
    }

    private boolean checkIILIST(CommandSender sender, Command command, boolean returner)
    {
        if (command.getName().toUpperCase().equals("IILIST"))
        {
            sender.sendMessage(ChatColor.RED + "---------------------");
            for (int i = 0; i < players.iiPlayers.size(); i++)
            {
                sender.sendMessage(ChatColor.GREEN + players.iiPlayers.get(i).getName());
                sender.sendMessage(ChatColor.WHITE + players.iiPlayers.get(i).getUUID());
                sender.sendMessage(ChatColor.YELLOW + players.iiPlayers.get(i).getIiInfos());
            }
            sender.sendMessage(ChatColor.RED + "---------------------");
            returner = true;
        }
        return returner;
    }

    private boolean checkIIADD(CommandSender sender, Command command, String[] args, Player player, boolean returner)
    {
        if (command.getName().equalsIgnoreCase("IIADD")){
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.RED + "Correct usage: /IIADD <Player>");
                returner = false;
            }
            if (args.length >= 1)
            {
                IIPlayer iiPlayer = GetPlayer(args[0]);
                if (iiPlayer != null)
                {
                    if (args.length > 1)
                    {
                        if (IIInfo.lookup(args[1]) != null)
                        {
                            iiPlayer.addIiInfo(IIInfo.lookup(args[1]));
                            sender.sendMessage(
                                    ChatColor.GREEN +
                                            "Added " +
                                    ChatColor.YELLOW +
                                            IIInfo.lookup(args[1]) +
                                    ChatColor.GREEN +
                                            " To " + args[0]);
                        }else
                        {
                            StringBuilder tmp = new StringBuilder();
                            for (int i = 0; i < IIInfo.values().length; i++)
                            {
                                tmp.append("[").append(IIInfo.values()[i].name()).append("] ");
                            }
                            player.sendMessage(
                                    ChatColor.RED + "Invalid value for Illness or Injury : " +
                                            ChatColor.GOLD + args[1] + "\n" +
                                    ChatColor.RED + "valid values are : " +
                                            ChatColor.GOLD + tmp);
                        }
                    }else {
                        player.sendMessage(ChatColor.RED + "Cannot Add no 'Illness or Injury'");
                    }
                }else {
                    player.sendMessage(ChatColor.RED + "Cannot Find Player");
                    returner = false;
                }

                returner = true;
            }
        }
        return returner;
    }

    IIPlayer GetPlayer(String name)
    {
        for (int i = 0; i < players.iiPlayers.size(); i++)
        {
            if (players.iiPlayers.get(i).getName().equals(name))
            {
                return players.iiPlayers.get(i);
            }
        }
        return null;
    }
}
