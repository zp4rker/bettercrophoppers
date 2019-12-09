package com.zp4rker.bettercrophoppers

import com.zp4rker.bettercrophoppers.commands.GiveHopper
import com.zp4rker.bettercrophoppers.listeners.BlockPlace
import com.zp4rker.bettercrophoppers.listeners.HopperPickup
import com.zp4rker.bettercrophoppers.listeners.ItemTransfer
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

val hopperName: String = ChatColor.translateAlternateColorCodes('&', "&2&lCrop&c&lHopper")

class BetterCropHoppers : JavaPlugin() {

    override fun onEnable() {
        registerListeners(BlockPlace(this), HopperPickup(this), ItemTransfer(this))

        getCommand("givehopper").executor = GiveHopper

        logger.info("Successfully enabled!")
    }

    private fun registerListeners(vararg listeners: Listener) = listeners.forEach { server.pluginManager.registerEvents(it, this) }

}