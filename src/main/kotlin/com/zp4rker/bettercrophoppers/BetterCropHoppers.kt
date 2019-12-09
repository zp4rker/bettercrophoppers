package com.zp4rker.bettercrophoppers

import com.zp4rker.bettercrophoppers.commands.GiveHopper
import com.zp4rker.bettercrophoppers.listeners.BlockPlace
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

val hopperName = ChatColor.translateAlternateColorCodes('&', "&2&lCrop&c&lHopper")

class BetterCropHoppers : JavaPlugin() {

    override fun onEnable() {
        registerListeners(TestListener(this), BlockPlace(this))

        getCommand("givehopper").executor = GiveHopper

        logger.info("Successfully enabled!")
    }

    private fun registerListeners(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }

}

class TestListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onHopperPickup(event: InventoryPickupItemEvent) {
        if (event.inventory.holder !is Hopper) return

        val hopper = event.inventory.holder as Hopper
        if (!hopper.hasMetadata("crophopper") || !hopper.getMetadata("crophopper")[0].asBoolean()) return

        val itemType = event.item.itemStack.type
        if (itemType != Material.CACTUS && itemType != Material.SUGAR_CANE) {
            event.isCancelled = true
            return
        }
    }

    @EventHandler
    fun onItemSpawn(event: ItemSpawnEvent) {
        val hoppers = event.entity.location.chunk.tileEntities.filterIsInstance<Hopper>()
        plugin.logger.info("${hoppers.size}")
        for (hopper in hoppers) {
            if (hopper.hasMetadata("crophopper") && hopper.getMetadata("crophopper")[0].asBoolean()) plugin.logger.info("found a crophopper in chunk")
            else plugin.logger.info("found a regular hopper in chunk")
        }
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.clickedBlock == null) return
        if (event.clickedBlock.state !is Hopper) return

        val hopper = event.clickedBlock.state as Hopper
        if (!hopper.hasMetadata("crophopper") || !hopper.getMetadata("crophopper")[0].asBoolean()) return

        event.player.sendMessage("You found a custom hopper, well done!")
    }
}