package org.example.geoblinker.util
import platform.Foundation.*
actual fun Double.format(decimals: Int): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = decimals.toULong()
    formatter.maximumFractionDigits = decimals.toULong()
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    return formatter.stringFromNumber(NSNumber(this)) ?: ""
}
