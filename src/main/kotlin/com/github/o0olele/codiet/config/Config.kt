package com.github.o0olele.codiet.config


import com.github.o0olele.codiet.OllamaLLM
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

class CodietConfig : SearchableConfigurable {
    private var panel: CodietSettingsPanel? = null

    override fun createComponent(): JComponent {
        return CodietSettingsPanel().also { panel = it }.mainPanel
    }

    override fun isModified(): Boolean {
        val panel = this.panel ?: return false
        val state = CodietState.getInstance()
        return panel.modelField.text != state.model
    }

    override fun reset() {
        val panel = this.panel ?: return
        val state = CodietState.getInstance()
        panel.modelField.text = state.model
        OllamaLLM.changeModel(state.model)
    }

    override fun apply() {
        val panel = this.panel ?: return
        val state = CodietState.getInstance()
        state.model = panel.modelField.text
        OllamaLLM.changeModel(state.model)
    }

    override fun disposeUIResources() {
        this.panel = null
    }

    override fun getDisplayName() = "AI code completion idea"
    override fun getId() = "com.aicc.aicodecompletionideaplugin.config.AICCConfig"
}