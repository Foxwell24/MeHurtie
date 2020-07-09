package me.thefox.iivirtual;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;

public class IIPlayer
{
    String uuid, name;
    ArrayList<IIInfo> iiInfos;

    public IIPlayer(String uuid)
    {
        this.uuid = uuid;
        iiInfos = new ArrayList<>();
    }

    public String getUUID()
    {
        return uuid;
    }
    public String getName()
    {
        return name;
    }

    public String getIiInfos()
    {
        String ans = "No Illnesses or Injuries";

        if (iiInfos.size() > 0)
        {
            ans = "";
            for (int i = 0; i < iiInfos.size(); i++)
            {
                ans += "[" + iiInfos.get(i).name().toUpperCase() + "]";
            }
        }

        return ans;
    }

    public String getIiInfosRaw()
    {
        if (iiInfos.size() > 0)
        {
            String ans = "";
            for (int i = 0; i < iiInfos.size(); i++)
            {
                ans += iiInfos.get(i).name();
                ans += ",";
            }
            return ans;
        }
        else {
            return "Nothing Wrong Here";
        }

    }

    public void addIiInfo(IIInfo iiInfo)
    {
        iiInfos.add(iiInfo);
    }
    public void addIiInfosRaw(String rawData)
    {
        String[] names = rawData.split(",");

        for (int i = 0; i < names.length; i++)
        {
            if (IIInfo.lookup(names[i]) != null)
            {
                addIiInfo(IIInfo.lookup(names[i]));
            }
        }
    }
}
