package com.cequea.transferme.utils.visualTransfomations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class AccountVisualTransformation(val separator: Char = '-') : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformed = text.text.chunked(4).joinToString(separator.toString())

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val sepCount = (offset / 4).coerceAtMost(transformed.count { it == separator })
                return offset + sepCount
            }

            override fun transformedToOriginal(offset: Int): Int {
                val sepCount = (offset / 5).coerceAtMost(transformed.count { it == separator })
                return offset - sepCount
            }
        }

        return TransformedText(AnnotatedString(transformed), offsetMapping)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccountVisualTransformation) return false
        return separator == other.separator
    }

    override fun hashCode(): Int = separator.hashCode()
}

