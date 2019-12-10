package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.hopperName
import com.zp4rker.bettercrophoppers.verifyHopper
import org.bukkit.ChatColor
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class HopperPlace(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        if (event.blockPlaced.state is Hopper && event.itemInHand.itemMeta.displayName == hopperName) {

            if (event.blockPlaced.chunk.tileEntities.filter {it is Hopper && verifyHopper(it, plugin)}.size == 2) {
                event.isCancelled = true
                event.player.sendMessage("${ChatColor.DARK_RED}There can only be two $hopperName${"s"} ${ChatColor.DARK_RED}per chunk!")
                return
            }

            event.blockPlaced.setMetadata("crophopper", FixedMetadataValue(plugin, true))
        }
    }

}