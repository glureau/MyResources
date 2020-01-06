package com.glureau.myresources.core

fun Int.toHex() = toUInt().toString(16).toUpperCase()

private const val KiloBytes = 1024.0
private const val MegaBytes = 1024 * 1024.0
fun Int.toHumanByteCount() =
    when {
        this > MegaBytes -> String.format("%.2f MB", (this / MegaBytes))
        this > KiloBytes -> String.format("%.2f KB", (this / KiloBytes))
        else -> String.format("%d B", this)
    }
