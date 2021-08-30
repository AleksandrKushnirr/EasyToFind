package com.kushnir.app.easytofind.domain.enums

enum class RatingColor {
    GREEN,
    LIME,
    YELLOW,
    ORANGE,
    RED;

    companion object{
        fun fromDoubleValue(value: Double): RatingColor {
            return when {
                value >= 8.0 -> GREEN
                value >= 6.0 -> LIME
                value >= 4.0 -> YELLOW
                value >= 2.0 -> ORANGE
                else -> RED
            }
        }
    }
}