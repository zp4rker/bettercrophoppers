package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.utils.removeItems
import com.zp4rker.bettercrophoppers.verifyHopper
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.plugin.java.JavaPlugin

class HopperTransfer(private val plugin: JavaPlugin): Listener {

    private val amountPerSec = 160

    @EventHandler
    fun onTransfer(event: InventoryMoveItemEvent) {
        if (event.source.holder !is Hopper) return

        val hopper = event.source.holder as Hopper
        if (!verifyHopper(hopper, plugin)) return

        event.isCancelled = true

        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
            val remainder = hopper.inventory.removeItems(event.item.clone().apply { amount = amountPerSec })
            event.destination.addItem(event.item.clone().apply { amount = amountPerSec - remainder })
        }, 20)
    }

}