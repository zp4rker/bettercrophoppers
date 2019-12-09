package com.zp4rker.bettercrophoppers

import com.zp4rker.bettercrophoppers.commands.GiveHopper
import com.zp4rker.bettercrophoppers.listeners.BlockPlace
import com.zp4rker.bettercrophoppers.listeners.HopperPickup
import com.zp4rker.bettercrophoppers.utils.spaceLeft
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

val hopperName: String = ChatColor.translateAlternateColorCodes('&', "&2&lCrop&c&lHopper")

class BetterCropHoppers : JavaPlugin() {

    override fun onEnable() {
        registerListeners(TestListener(this), BlockPlace(this), HopperPickup())

        getCommand("givehopper").executor = GiveHopper

        logger.info("Successfully enabled!")
    }

    private fun registerListeners(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }

}

class TestListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onItemSpawn(event: ItemSpawnEvent) {
        if (event.entity.itemStack.type != Material.CACTUS && event.entity.itemStack.type != Material.SUGAR_CANE) return

        for (hopper in event.entity.location.chunk.tileEntities.filterIsInstance<Hopper>()) {
            if (hopper.hasMetadata("crophopper") && hopper.getMetadata("crophopper")[0].asBoolean()) {
                event.entity.remove()
                if (hopper.inventory.spaceLeft(event.entity.itemStack) < 1) continue
                hopper.inventory.addItem(event.entity.itemStack)
            }
        }
    }

    @EventHandler
    fun onTransfer(event: InventoryMoveItemEvent) {
        if (event.source.holder !is Hopper) return

        val hopper = event.source.holder as Hopper
        if (!hopper.hasMetadata("crophopper") || !hopper.getMetadata("crophopper")[0].asBoolean()) return

        event.item = event.item.apply { amount = 64 }
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