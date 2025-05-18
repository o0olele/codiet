package com.github.o0olele.codiet

import com.intellij.DynamicBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

@NonNls
private const val BUNDLE = "messages.MyBundle"

object MyBundle : DynamicBundle(BUNDLE) {

    @JvmStatic
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getMessage(key, *params)

    @Suppress("unused")
    @JvmStatic
    fun messagePointer(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getLazyMessage(key, *params)

    // 斐波那契数列

<think>

</think>

你提供的代码片段是一个使用 IntelliJ IDEA 模块化插件开发的 Kotlin 示例，其中定义了一个 `MyBundle` 类，用于处理资源消息（如插件的国际化字符串）。你注释中提到“斐波那契数列”，但代码中并没有实际实现
}
