package com.exc.privatefix;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PrivateFix
        extends JavaPlugin
        implements Listener
{
    Plugin plugin = this;

    public void onEnable()
    {
        this.plugin.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("PrivateFix has been enabled");
    }

    public void onDisable()
    {
        getLogger().info("PrivateFix has been disabled");
    }

    @EventHandler
    public void debug(PlayerInteractEvent event)
    {
        if (this.plugin.getConfig().getBoolean("config.debug"))
        {
            Player player = event.getPlayer();
            player.sendMessage("Item Data: " + event.getItem().getData().toString());
            player.sendMessage("Item Meta: " + event.getItem().getItemMeta().toString());
            player.sendMessage("Item Type: " + event.getItem().getType().toString());
        }
    }

    @EventHandler
    public void isInPrivate(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Location loc = event.getPlayer().getLocation();
        Location locView = event.getPlayer().getEyeLocation();
        WorldGuardPlugin worldGuard = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (((!worldGuard.canBuild(player, loc)) || (!worldGuard.canBuild(player, locView))) && (
                (event.getItem().getType().toString().equals(this.plugin.getConfig().getString("item.item1"))) || (event.getItem().getType().toString().equals(this.plugin.getConfig().getString("item.item2"))) || (event.getItem().getType().toString().equals(this.plugin.getConfig().getString("item.item3"))) || (event.getItem().getType().toString().equals(this.plugin.getConfig().getString("item.item4"))) || (event.getItem().getType().toString().equals(this.plugin.getConfig().getString("item.item5")))))
        {
            if (this.plugin.getConfig().getBoolean("config.debug")) {
                player.sendMessage("Interaction denied!");
            }
            event.setCancelled(true);
        }
    }
}
