package `fun`.kotlingang.kds.delegate

import `fun`.kotlingang.kds.annotation.RawSetterGetter
import `fun`.kotlingang.kds.storage.PrimitiveDataStorage
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


public fun PrimitiveDataStorage.boolean(default: () -> Boolean): KDSBooleanProperty =
    KDSBooleanProperty(storage = this, default)

@OptIn(RawSetterGetter::class)
public class KDSBooleanProperty (
private val storage: PrimitiveDataStorage,
private val default: () -> Boolean
) : ReadWriteProperty<Any?, Boolean> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
    storage.getBoolean(property.name) ?: default()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        storage.putBoolean(property.name, value)
    }
}
