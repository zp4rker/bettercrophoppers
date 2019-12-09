package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.utils.removeItems
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ItemTransfer(private val plugin: JavaPlugin): Listener {
    @EventHandler
    fun onTransfer(event: InventoryMoveItemEvent) {
        if (event.source.holder !is Hopper) return

        val hopper = event.source.holder as Hopper
        if (!hopper.hasMetadata("crophopper") || !hopper.getMetadata("crophopper")[0].asBoolean()) return

        event.isCancelled = true

        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
            val hopperLoc = hopper.block.location
            val newHopper = hopperLoc.block.state as Hopper
            val remainder = newHopper.inventory.removeItems(ItemStack(event.item.type, 10))
            event.destination.addItem(ItemStack(event.item.type, 10 - remainder))
        }, 1)
    }
}