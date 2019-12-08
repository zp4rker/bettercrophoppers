package com.zp4rker.bettercrophoppers

import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class BetterCropHoppers : JavaPlugin() {

    override fun onEnable() {
        logger.info("Successfully enabled!")
        server.pluginManager.registerEvents(TestListener(this), this)
    }

}

class TestListener(private val plugin: JavaPlugin): Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.blockPlaced.state is Hopper) {
            event.blockPlaced.setMetadata("crophopper", FixedMetadataValue(plugin, true))
        }
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.clickedBlock.state !is Hopper) return

        val hopper = event.clickedBlock.state as Hopper
        if (!hopper.hasMetadata("crophopper") || !hopper.getMetadata("crophopper")[0].asBoolean()) return

        event.player.sendMessage("You found a custom hopper, well done!")
    }
}