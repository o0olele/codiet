package com.github.o0olele.codiet.config

import com.github.o0olele.codiet.OllamaLLM
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.State
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.github.o0olele.codiet.config.CodietState",
    storages = [Storage("codiet.xml")]
)
class CodietState : PersistentStateComponent<CodietState> {

    @JvmField
    var model: String = "codellama:7b-code"

    override fun getState(): CodietState = this

    override fun loadState(state: CodietState) {
        XmlSerializerUtil.copyBean(state, this)
        OllamaLLM.changeModel(state.model)
    }

    companion object {
        fun getInstance(): CodietState {
            return ApplicationManager.getApplication().getService(CodietState::class.java)
        }
    }
}