package `fun`.kotlingang.kds

import `fun`.kotlingang.kds.file.CommonFile
import `fun`.kotlingang.kds.storage.AsyncCommittableStorage
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.io.File


@OptIn(DelicateCoroutinesApi::class)
public actual open class KFileDataStorage private constructor (
    json: Json,
    private val storage: JsonElementFileDataStorage
) : KJsonDataStorage(json, storage), AsyncCommittableStorage {
    public actual constructor (
        absolutePath: String,
        json: Json,
        scope: CoroutineScope
    ) : this(json, JsonElementFileDataStorage(json, CommonFile(absolutePath), scope))

    public actual constructor(json: Json, scope: CoroutineScope) : this (
        absolutePath = CommonFile.HOME_DIR.join(path = "data").join(path = "data.json").absolutePath,
        json, scope
    )

    public constructor (
        file: File,
        json: Json = Json,
        scope: CoroutineScope = GlobalScope + SupervisorJob()
    ) : this(file.absolutePath, json, scope)

    public actual companion object {
        public actual fun ofName (
            name: String,
            json: Json,
            scope: CoroutineScope
        ): KFileDataStorage = KFileDataStorage (
            absolutePath = CommonFile.HOME_DIR.join(path = "data").join(path = "$name.json").absolutePath,
            json, scope
        )
    }

    final override fun commitBlocking(): Unit = storage.commitBlocking()
    final override fun launchCommit(): Unit = storage.launchCommit()
    final override suspend fun commit(): Unit = storage.commit()
}
