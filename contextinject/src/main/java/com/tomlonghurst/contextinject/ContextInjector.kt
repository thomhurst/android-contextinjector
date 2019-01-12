package com.tomlonghurst.contextinject

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ContextInjector<Return> {

    private lateinit var weakContext: WeakReference<Context>
    private lateinit var initializerContext: (context: Context?) -> Return

    constructor(`this`: Context,
                initializer: (context: Context?) -> Return) {
        this.weakContext = WeakReference(`this`)
        this.initializerContext = initializer
    }

    constructor(`this`: View,
                initializer: (context: Context?) -> Return) {
        this.weakContext = WeakReference(`this`.context)
        this.initializerContext = initializer
    }

    constructor(`this`: Fragment,
                initializer: (context: Context?) -> Return) {
        val activity = `this`.activity ?: return
        this.weakContext = WeakReference(activity)
        this.initializerContext= initializer
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): Return {
        val context = weakContext.get()
        return initializerContext.invoke(context)
    }
}