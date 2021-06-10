private const val MAJOR = 1
private const val MINOR = 0
private val PATCH = System.getenv()["BUILD_NUMBER"]?.toInt() ?: 1

val VERSION_CODE = PATCH
val VERSION_NAME = System.getenv()["BUILD_VERSION"] ?: "$MAJOR.$MINOR.$PATCH"