package com.qibla.muslimday.app2025.util


class Global {
    companion object {
        var listDuas: MutableList<String> = mutableListOf()
        var listAzkar: MutableList<String> = mutableListOf()

        @JvmField
        var intDay: Int = 0

        var strDuas: String = ""
        var strAzkar: String = ""

        @JvmField
        var isCheckTime = false

        @JvmField
        var count = 0
    }
}

