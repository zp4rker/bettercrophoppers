package com.zp4rker.bettercrophoppers.utils

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.spaceLeft(itemStack: ItemStack): Int {
    var spaceLeft = 0
    for (content in contents) {
        spaceLeft += if (content == null) 64 else content.maxStackSize - content.amount
    }
    return spaceLeft
}