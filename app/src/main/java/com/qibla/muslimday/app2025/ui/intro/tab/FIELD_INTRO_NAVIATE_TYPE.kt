package com.qibla.muslimday.app2025.ui.intro.tab

enum class FIELD_INTRO_NAVIATE_TYPE(val value: Int) {
    INTRO1(0), INTRO2(1), INTRO3(2), INTRO4(3);

    companion object {
        fun getValueType(value: Int?) = entries.find { it.value == value } ?: INTRO1
    }
}

enum class FIELD_NATIVE_FULL(val value: Int) {
    INTRO_FULL_2(4), INTRO_FULL_3(5);

    companion object {
        fun getValueType(value: Int?) =
            FIELD_NATIVE_FULL.entries.find { it.value == value } ?: INTRO_FULL_3
    }
}