package com.cequea.transferme.utils.visualTransfomations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class ExpirationDateVisualTransformation(val separator: Char = '/') : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformed = if (text.length < 2) {
            text.text
        } else {
            val firstPart = text.text.take(2)
            val secondPart = text.text.drop(2)
            "$firstPart$separator$secondPart"
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // Add the separators to the original offset
                val sepCount = if (offset <= 2) 0 else 1
                return offset + sepCount
            }

            override fun transformedToOriginal(offset: Int): Int {
                // Remove the separators from the transformed offset
                val sepCount = if (offset <= 2) 0 else 1
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
