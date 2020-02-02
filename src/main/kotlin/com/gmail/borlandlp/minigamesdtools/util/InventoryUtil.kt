package com.gmail.borlandlp.minigamesdtools.util

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import java.util.*

object InventoryUtil {
    fun str2ItemStack(strItem: String?) {}

    //TODO: unit tests
    @JvmStatic
    fun getArmourItemStack(info: String): ItemStack {
        val itemInfo = info.split(",")
        val idDamageValueAndAmount = itemInfo[0].split("!")
        val idAndDamageValue = idDamageValueAndAmount[0].split(":")
        val itemStack = ItemStack(
            Integer.valueOf(idAndDamageValue[0]),
            Integer.valueOf(idDamageValueAndAmount[1])
        )
        if (itemInfo.size > 1) {
            val enchantments = itemInfo[1].split("!")
            val len = enchantments.size
            for (i in 0 until len) {
                val ench = enchantments[i]
                if (ench.length > 2) {
                    val enchantment = ench.split(":")
                    itemStack.addUnsafeEnchantment(
                        Enchantment.getByName(enchantment[0]),
                        Integer.valueOf(enchantment[1])
                    )
                }
            }
        }
        return itemStack
    }

    // id, amount, durability, slot, enchantments
    // TODO: Unit tests
    @JvmStatic
    fun getItemStackOufOfString(info: String): Array<Any> {
        val itemInfo = HashMap<String, String?>()
        var enchantments = String()
        for (row in info.split(",")) {
            val splitted = row.split("=")
            if (splitted.size == 2) {
                if (splitted[0].equals("enchantments", ignoreCase = true)) {
                    enchantments = splitted[1]
                } else {
                    itemInfo[splitted[0]] = splitted[1]
                }
            }
        }
        val material = Material.getMaterial(itemInfo["id"].toString())
        val itemStack =
            ItemStack(material, itemInfo["amount"]!!.toInt())
        if (itemInfo.containsKey("durability")) {
            itemStack.durability = itemInfo["durability"]!!.toShort()
        }
        if (enchantments.length > 0) {
            for (row in enchantments.split("#")) {
                val splitted = row.split(":")
                val enchPower = if (enchantments.length > 1) Integer.valueOf(splitted[1]) else 1
                itemStack.addEnchantment(Enchantment.getByName(splitted[0]), enchPower)
            }
        }

        return arrayOf(itemStack, if (itemInfo.containsKey("slot")) itemInfo["slot"]!!.toInt() else 0)
    }
}