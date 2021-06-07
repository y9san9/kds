package `fun`.kotlingang.kds.delegate

import `fun`.kotlingang.kds.annotation.ExperimentalKDSApi
import `fun`.kotlingang.kds.annotation.UnsafeKType
import `fun`.kotlingang.kds.mutate.StorageMap
import `fun`.kotlingang.kds.storage.KTypeDataStorage
import kotlin.reflect.typeOf


/**
 * @see [StorageMap]
 */
@ExperimentalKDSApi
@OptIn(ExperimentalStdlibApi::class, UnsafeKType::class)
public inline fun <reified K, reified V> KTypeDataStorage.storageMap (
    map: MutableMap<K, V>
): DelegateProvider<Any?, KDSProperty<MutableMap<K, V>>> =
    DelegateProvider { _, property ->
        property {
            StorageMap(property.name, storage = this, map, typeOf<MutableMap<K, V>>())
        }
    }

/**
 * @see [StorageMap]
 */
@ExperimentalKDSApi
public inline fun <reified K, reified V> KTypeDataStorage.storageMap (
    vararg pairs: Pair<K, V>
): DelegateProvider<Any?, KDSProperty<MutableMap<K, V>>> = storageMap(mutableMapOf(*pairs))
