package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.AdvancedPortal;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class FlowStopper implements Listener {

    @SuppressWarnings("unused")
    private final AdvancedPortalsPlugin plugin;

    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
    private boolean WaterFlow = true;

    public FlowStopper(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.WaterFlow = config.getConfig().getBoolean("StopWaterFlow");

        if (WaterFlow) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockFromTo(BlockFromToEvent event) {
        // when checking positions check the block and the to block
        Block blockTo = event.getToBlock();
        Block block = event.getBlock();


        int floodRegion = 3;

        AdvancedPortal inPortal = Portal.blockLocationInPortal(block.getLocation(), floodRegion);
        if(inPortal != null){
            event.setCancelled(true);
        }

        inPortal = Portal.blockLocationInPortal(blockTo.getLocation(), floodRegion);
        if(inPortal != null){
            event.setCancelled(true);
        }
    }


}
