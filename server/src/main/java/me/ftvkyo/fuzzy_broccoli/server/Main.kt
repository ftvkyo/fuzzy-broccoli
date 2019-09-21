package me.ftvkyo.fuzzy_broccoli.server

import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor


object Main {

    private val PREFIX = " [server] "


    @JvmStatic
    fun main(args: Array<String>) {
        ArgumentProcessor.printAll(PREFIX, args)
        println(PREFIX + "Done!")
    }
}
