package com.zp4rker.bettercrophoppers.utils

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ConcurrentHashMap

fun Inventory.removeItems(itemStack: ItemStack): Int {
    var remaining = itemStack.amount

    val newContents = contents
    val slots = ConcurrentHashMap(all(itemStack.type))

    if (slots.isEmpty()) return remaining

    for (currentSlot in slots) {
        var newSlot: ItemStack

        if (currentSlot.value.amount >= itemStack.amount) {
            val newAmount = currentSlot.value.amount - itemStack.amount
            newSlot = if (newAmount == 0) ItemStack(Material.AIR) else ItemStack(itemStack.type, newAmount)
            remaining -= itemStack.amount
        } else {
            newSlot = ItemStack(Material.AIR)
            remaining -= currentSlot.value.amount
        }

        newContents[currentSlot.key] = newSlot
        slots.remove(currentSlot.key)

        if (remaining == 0) break
    }

    contents = newContents
    return remaining
}

fun Inventory.spaceLeft(itemStack: ItemStack): Int {
    var spaceLeft = 0
    for (content in contents) {
        spaceLeft += if (content == null) 64 else content.maxStackSize - content.amount
    }
    return spaceLeft
}
