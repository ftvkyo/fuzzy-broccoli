package me.ftvkyo.fuzzy_broccoli.common

object ArgumentProcessor {

    fun printAll(prefix: String, args: Array<String>) {
        for (i in args.indices) {
            print(prefix)
            println("Argument[" + i + "]: " + args[i])
        }
    }
}
