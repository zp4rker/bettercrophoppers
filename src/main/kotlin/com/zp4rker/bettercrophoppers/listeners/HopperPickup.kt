package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.hopperName
import org.bukkit.Material
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector

class HopperPickup(private val plugin: JavaPlugin): Listener {
    @EventHandler
    fun onHopperPickup(event: InventoryPickupItemEvent) {
        if (event.inventory.holder !is Hopper) return

        val hopper = event.inventory.holder as Hopper
        if (hopper.inventory.name == hopperName && !hopper.hasMetadata("crophopper")) hopper.setMetadata("crophopper", FixedMetadataValue(plugin, true))
        if (!hopper.hasMetadata("crophopper") || !hopper.getMetadata("crophopper")[0].asBoolean()) return

        val itemType = event.item.itemStack.type
        if (itemType != Material.CACTUS && itemType != Material.SUGAR_CANE) {
            event.isCancelled = true
            return
        }
    }

    @EventHandler
    fun onItemSpawn(event: ItemSpawnEvent) {
        if (event.entity.itemStack.type != Material.CACTUS && event.entity.itemStack.type != Material.SUGAR_CANE) return

        for (hopper in event.entity.location.chunk.tileEntities.filterIsInstance<Hopper>()) {
            if (hopper.inventory.name == hopperName && !hopper.hasMetadata("crophopper")) hopper.setMetadata("crophopper", FixedMetadataValue(plugin, true))
            if (hopper.hasMetadata("crophopper") && hopper.getMetadata("crophopper")[0].asBoolean()) {
                event.entity.teleport(hopper.location.add(0.0, 2.0, 0.0))
                event.entity.velocity = Vector(0.0, -0.5, 0.0)
            }
        }
    }
}