package com.qibla.muslimday.app2025.util

import org.greenrobot.eventbus.EventBus

object EventBusHelper {
    private val eventBus = EventBus.getDefault()

    fun register(subscriber: Any) {
        eventBus.register(subscriber)
    }

    fun unregister(subscriber: Any) {
        eventBus.unregister(subscriber)
    }

    fun postSticky(event: Any) {
        eventBus.postSticky(event)
    }
}