package com.zp4rker.bettercrophoppers.listeners

import org.bukkit.Material
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryPickupItemEvent

class HopperPickup: Listener {
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
}