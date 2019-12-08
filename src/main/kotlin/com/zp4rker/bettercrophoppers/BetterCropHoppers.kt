package com.zp4rker.bettercrophoppers

import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class BetterCropHoppers : JavaPlugin() {

    override fun onEnable() {
        logger.info("Successfully enabled!")
        server.pluginManager.registerEvents(InteractListener, this)
    }

}

object InteractListener: Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.clickedBlock is Hopper) event.player.sendMessage("You found a hopper!")
        else event.player.sendMessage("That's not a hopper!")
    }
}