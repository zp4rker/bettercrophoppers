package com.zp4rker.bettercrophoppers.utils

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.lang.IllegalStateException

fun Inventory.spaceLeft(itemStack: ItemStack): Int {
    var spaceLeft = 0
    for (content in contents) {
        spaceLeft += if (content == null) 64 else content.maxStackSize - content.amount
    }
    return spaceLeft
}

/*
fun Inventory.removeItems(itemStack: ItemStack) {
    var remaining = removeItemsSingle(itemStack)
    while (remaining > 0) {
        val change = removeItemsSingle(itemStack)
        if (change == -1) throw IllegalStateException("Not enough items in the inventory!")
        remaining = change
    }
}

private fun Inventory.removeItemsSingle(itemStack: ItemStack): Int {
    var remaining = 0

    val newContents = contents
    val slot = first(itemStack.type)

    if (newContents[slot] == null) return -1

    if (newContents[slot].amount >= itemStack.amount) {
        val newAmount = newContents[slot].amount - itemStack.amount
        val newSlot = if (newAmount == 0) ItemStack(Material.AIR) else ItemStack(itemStack.type, newAmount)
        newContents[slot] = newSlot
    } else {
        remaining = itemStack.amount - newContents[slot].amount
        newContents[slot] = ItemStack(Material.AIR)
    }

    this.all
    contents = newContents
    return remaining
}*/

fun Inventory.removeItems(itemStack: ItemStack): Int {
    var remaining = itemStack.amount

    val newContents = contents
    val slots = all(itemStack.type)

    if (slots.isEmpty()) return remaining

    for (currentSlot in slots) {
        var newSlot: ItemStack

        if (currentSlot.value.amount >= itemStack.amount) {
            val newAmount = currentSlot.value.amount - itemStack.amount
            newSlot = if (newAmount == 0) ItemStack(Material.AIR) else ItemStack(itemStack.type, newAmount)
            remaining = 0
        } else {
            newSlot = ItemStack(Material.AIR)
            remaining = itemStack.amount - currentSlot.value.amount
        }

        newContents[currentSlot.key] = newSlot
        slots.remove(currentSlot.key)

        if (remaining == 0) break
    }

    contents = newContents
    return remaining
}
