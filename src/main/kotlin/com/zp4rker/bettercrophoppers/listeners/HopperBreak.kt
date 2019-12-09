package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.hopperName
import org.bukkit.Material
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class HopperBreak: Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (event.block == null || event.block.state !is Hopper) return

        val hopper = event.block.state as Hopper
        if (hopper.inventory.name != hopperName) return

        event.isCancelled = true

        event.block.type = Material.AIR
        val hopperItem = ItemStack(Material.HOPPER, 1)
        hopperItem.itemMeta = hopperItem.itemMeta.apply { displayName = hopperName }
        event.block.location.world.dropItemNaturally(event.block.location, hopperItem)
    }

}