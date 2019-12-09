package com.zp4rker.bettercrophoppers.commands

import com.zp4rker.bettercrophoppers.hopperName
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

object GiveHopper : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (args.size != 1 || Bukkit.getPlayerExact(args[0]) == null) {
            sender.sendMessage("${ChatColor.RED}Invalid arguments! Please try again.")
            return false
        }

        val player = Bukkit.getPlayerExact(args[0])
        val hopper = ItemStack(Material.HOPPER, 1)
        hopper.itemMeta = hopper.itemMeta.apply { displayName = hopperName }
        player.inventory.addItem(hopper)
        player.sendMessage("${ChatColor.GREEN}You've been given a $hopperName.")
        sender.sendMessage("${ChatColor.GOLD}You sent a $hopperName ${ChatColor.GOLD}to ${player.name}.")
        return true
    }
}