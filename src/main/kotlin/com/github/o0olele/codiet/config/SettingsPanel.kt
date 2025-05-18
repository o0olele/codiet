package com.github.o0olele.codiet.config

import com.intellij.ui.IdeBorderFactory
import javax.swing.JPanel
import javax.swing.JTextField

class CodietSettingsPanel {

    lateinit var mainPanel: JPanel
    lateinit var modelField: JTextField

    init {
        mainPanel.border = IdeBorderFactory.createTitledBorder("Plugin Settings")
    }
}