package com.tomlonghurst.contextinject

import android.app.Activity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ActivityInjector<Return> {

    private lateinit var weakContext: WeakReference<Activity>
    private lateinit var initializerContext: (activity: Activity?) -> Return

    constructor(`this`: Activity,
                initializer: (activity: Activity?) -> Return) {
        this.weakContext = WeakReference(`this`)
        this.initializerContext = initializer
    }

    constructor(`this`: Fragment,
                initializer: (activity: Activity?) -> Return) {
        val activity = `this`.activity ?: return
        this.weakContext = WeakReference(activity)
        this.initializerContext= initializer
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): Return {
        val activity = weakContext.get()
        return initializerContext.invoke(activity)
    }
}