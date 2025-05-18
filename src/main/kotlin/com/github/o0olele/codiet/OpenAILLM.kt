package com.github.o0olele.codiet

import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.core.JsonField
import com.openai.core.JsonValue
import com.openai.models.completions.CompletionCreateParams

object OpenAILLM : LLM {

    private val client = OpenAIOkHttpClient
        .builder()
        .apiKey("None")
        .baseUrl("http://192.168.1.6:55052")
        .build()
    override fun call(prefix: String, suffix: String): String? {
        CodietStatusBarWidgetManager.updateStatus("OK")

        val params = CompletionCreateParams.builder()
            .prompt("<PRE> $prefix <SUF>$suffix <MID>")
            .prompt("<|fim_prefix|> $prefix <|fim_suffix|> $suffix <|fim_middle|>/no_think")
//            .prompt("你好")
            .model("")
            .maxTokens(64)
            .stopOfStrings(listOf(
                "<|endoftext|>",
                "<|fim_prefix|>",
                "<|fim_middle|>",
                "<|fim_suffix|>",
                "<|fim_pad|>",
                "<|repo_name|>",
                "<|file_sep|>",
                "<|im_start|>",
                "<|im_end|>",
            ))
            .build()
        val response = this.client.completions().create(params)._additionalProperties()
        val text: JsonValue? = response["content"]
        return text.toString()
    }

    override fun changeModel(model: String) {

    }

}