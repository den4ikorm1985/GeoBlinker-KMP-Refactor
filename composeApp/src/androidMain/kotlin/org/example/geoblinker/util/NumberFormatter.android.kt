package org.example.geoblinker.util
actual fun Double.format(decimals: Int): String = "%.${decimals}f".format(java.util.Locale.ENGLISH, this)
