package com.zp4rker.bettercrophoppers.commands

import com.zp4rker.bettercrophoppers.hopperName
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object GiveHopper : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.DARK_RED}You must be a player to use that command!")
            return true
        }

        val hopper = ItemStack(Material.HOPPER, 1)
        hopper.itemMeta = hopper.itemMeta.apply { displayName = hopperName }
        sender.inventory.addItem(hopper)
        sender.sendMessage("You've been given a crophopper.")
        return true
    }
}