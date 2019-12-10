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
        if (args.size != 2 || Bukkit.getPlayerExact(args[0]) == null || args[1].toIntOrNull() == null) {
            sender.sendMessage("${ChatColor.RED}Invalid arguments! Please try again.")
            return false
        }

        val player = Bukkit.getPlayerExact(args[0])
        val amount = args[1].toInt()
        val hopper = ItemStack(Material.HOPPER, amount)
        hopper.itemMeta = hopper.itemMeta.apply { displayName = hopperName }
        player.inventory.addItem(hopper)
        player.sendMessage("${ChatColor.GREEN}You've been given $amount $hopperName${if (amount > 1) "s" else ""}.")
        sender.sendMessage("${ChatColor.GOLD}You sent $amount $hopperName${if (amount > 1) "s" else ""} ${ChatColor.GOLD}to ${player.name}.")
        return true
    }
}