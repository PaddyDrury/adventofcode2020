package util

import org.assertj.core.api.Assertions
import org.junit.Test

class AnIterableExtension {

    @Test
    fun hasAnExtensionFunctionSplitWhenWhichSplitsAListOnElementsMatchingPredicate() {
        Assertions.assertThat(mutableListOf("a", "b", "c", "d", "e").splitWhen { it == "c" })
                .hasSize(2)
                .contains(mutableListOf("a", "b"))
                .contains(mutableListOf("d", "e"))
    }
}