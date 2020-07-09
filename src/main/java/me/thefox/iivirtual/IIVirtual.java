package me.thefox.iivirtual;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;

public final class IIVirtual extends JavaPlugin implements Listener
{
    private ConsoleCommandSender console = this.getServer().getConsoleSender();
    private Commands commandManager;
    private IIPlayerList players;

    void Setup()
    {
        getServer().getPluginManager().registerEvents(this, this);
        ArrayList<IIPlayer> iPlayers = new ArrayList<>();
        players = new IIPlayerList(iPlayers);
        console.sendMessage(ChatColor.RED + "------------------------");
        console.sendMessage(ChatColor.GREEN + "II Started");
        console.sendMessage(ChatColor.RED + "------------------------");
        commandManager = new Commands(players, console);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        String uuid = event.getPlayer().getUniqueId().toString(); // get the user's UUID

        if (getPlayer(uuid) == null)
        {
            if (this.getConfig().getConfigurationSection("players."+uuid) == null)
            {
                IIPlayer player = new IIPlayer(uuid);
                player.name = event.getPlayer().getName();
                players.iiPlayers.add(player);
            }
            else {
                IIPlayer player = new IIPlayer(uuid);
                player.name = event.getPlayer().getName();
                player.addIiInfosRaw(this.getConfig().getString("players."+uuid+".I&I's"));
                players.iiPlayers.add(player);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return commandManager.Commands(sender, command, label, args);
    }
    @EventHandler
    public void playerQuit(PlayerQuitEvent e){
        String uuid = e.getPlayer().getUniqueId().toString(); // get the user's UUID
        IIPlayer player = getPlayer(uuid);

        this.getConfig().set("players."+uuid+".name", e.getPlayer().getName());
        if (player != null)
        {
            this.getConfig().set("players."+uuid+".I&I's", player.getIiInfosRaw());
        }
        else {
            console.sendMessage(
                    ChatColor.RED +
                            "Something went Wrong \n +" +
                            e.getPlayer().getName() + "for some reason could not be found");
        }
    }

    IIPlayer getPlayer(String uuid)
    {
        for (int i = 0; i < players.iiPlayers.size(); i++)
        {
            if (players.iiPlayers.get(i).getUUID().equals(uuid))
            {
                return players.iiPlayers.get(i);
            }
        }
        return null;
    }

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        Setup();
    }

    @Override
    public void onDisable()
    {
        console.sendMessage(ChatColor.RED + "------------------------");
        this.saveConfig();
        console.sendMessage(ChatColor.GREEN + "II Shutting down");
        console.sendMessage(ChatColor.RED + "------------------------");
    }
}
