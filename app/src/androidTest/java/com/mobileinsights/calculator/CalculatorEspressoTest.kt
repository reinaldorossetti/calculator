package com.mobileinsights.calculator

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mobileinsights.calculator.model.Operation
import com.mobileinsights.calculator.ui.TestTags
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CalculatorEspressoTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun clearCalculator() {
        tapSpecial(Operation.AC)
        assertDisplayEquals("0")
    }

    @Test
    fun ct01_displayStartsAtZero() {
        assertDisplayEquals("0")
    }

    @Test
    fun ct02_acceptsDigitEntries() {
        tapDigit('5')
        tapDigit('3')
        assertDisplayEquals("53")
    }

    @Test
    fun ct03_acceptsDecimalEntries() {
        tapDigit('3')
        tapSpecial(Operation.COMMA)
        tapDigit('1')
        tapDigit('4')
        assertDisplayEquals("3.14")
    }

    @Test
    fun ct04_enforcesTwelveDigitLimit() {
        repeat(15) {
            tapDigit('9')
        }
        assertDisplayEquals("999999999999")
    }

    @Test
    fun ct05_addsIntegers() {
        performBinaryOperation("5", Operation.ADDITION, "3", 8f)
    }

    @Test
    fun ct06_addsDecimals() {
        performBinaryOperation("2.5", Operation.ADDITION, "3.7", 6.2f)
    }

    @Test
    fun ct07_handlesAdditionChain() {
        enterNumber("1")
        tapOperation(Operation.ADDITION)
        enterNumber("2")
        tapOperation(Operation.ADDITION)
        enterNumber("3")
        tapEquals()
        assertDisplayApprox(6f)
    }

    @Test
    fun ct08_addsWithZero() {
        performBinaryOperation("7", Operation.ADDITION, "0", 7f)
    }

    @Test
    fun ct09_subtractsIntegers() {
        performBinaryOperation("10", Operation.SUBTRACTION, "4", 6f)
    }

    @Test
    fun ct10_subtractsDecimals() {
        performBinaryOperation("5.8", Operation.SUBTRACTION, "2.3", 3.5f)
    }

    @Test
    fun ct11_handlesNegativeSubtractionResult() {
        performBinaryOperation("3", Operation.SUBTRACTION, "7", -4f)
    }

    @Test
    fun ct12_subtractsWithZero() {
        performBinaryOperation("9", Operation.SUBTRACTION, "0", 9f)
    }

    @Test
    fun ct13_multipliesIntegers() {
        performBinaryOperation("6", Operation.MULTIPLICATION, "7", 42f)
    }

    @Test
    fun ct14_multipliesDecimals() {
        performBinaryOperation("2.5", Operation.MULTIPLICATION, "4.0", 10f)
    }

    @Test
    fun ct15_multipliesByZero() {
        performBinaryOperation("8", Operation.MULTIPLICATION, "0", 0f)
    }

    @Test
    fun ct16_multipliesWithNegativeNumbers() {
        performBinaryOperation("5", Operation.MULTIPLICATION, "-3", -15f)
    }

    @Test
    fun ct17_dividesIntegers() {
        performBinaryOperation("20", Operation.DIVISION, "4", 5f)
    }

    @Test
    fun ct18_dividesDecimals() {
        performBinaryOperation("7.5", Operation.DIVISION, "2.5", 3f)
    }

    @Ignore("Division by zero is not surfaced yet to the UI layer")
    @Test
    fun ct19_divisionByZeroShowsError() {
        // Pending UI handling
    }

    @Test
    fun ct20_supportsDivisionChain() {
        enterNumber("100")
        tapOperation(Operation.DIVISION)
        enterNumber("2")
        tapOperation(Operation.DIVISION)
        enterNumber("5")
        tapEquals()
        assertDisplayApprox(10f)
    }

    @Ignore("Modulo operator is not available in the UI")
    @Test
    fun ct21_moduloShowsRemainder() {
        // Pending modulo button implementation
    }

    @Ignore("Modulo operator is not available in the UI")
    @Test
    fun ct22_moduloHandlesDivisibleNumbers() {
        // Pending modulo button implementation
    }

    @Ignore("Modulo operator is not available in the UI")
    @Test
    fun ct23_moduloHandlesDecimals() {
        // Pending modulo button implementation
    }

    @Test
    fun ct24_acClearsCurrentValue() {
        enterNumber("58")
        tapSpecial(Operation.AC)
        assertDisplayEquals("0")
    }

    @Test
    fun ct25_acClearsPendingOperation() {
        enterNumber("5")
        tapOperation(Operation.ADDITION)
        enterNumber("3")
        tapSpecial(Operation.AC)
        enterNumber("2")
        tapOperation(Operation.ADDITION)
        enterNumber("2")
        tapEquals()
        assertDisplayApprox(4f)
    }

    @Test
    fun ct26_acAfterCompleteCalculation() {
        performBinaryOperation("5", Operation.ADDITION, "3", 8f)
        tapSpecial(Operation.AC)
        assertDisplayEquals("0")
    }

    @Test
    fun ct27_resultVisibleAfterOperation() {
        performBinaryOperation("5", Operation.ADDITION, "3", 8f)
        assertDisplayApprox(8f)
    }

    @Test
    fun ct28_handlesMultipleSequentialOperations() {
        performBinaryOperation("5", Operation.ADDITION, "3", 8f)
        enterNumber("10")
        tapOperation(Operation.SUBTRACTION)
        enterNumber("2")
        tapEquals()
        assertDisplayApprox(8f)
    }

    @Test
    fun ct29_limitsHistoryToRecentResults() {
        repeat(3) {
            tapSpecial(Operation.AC)
            performBinaryOperation("5", Operation.ADDITION, "3", 8f)
        }
        assertDisplayApprox(8f)
    }

    @Test
    fun ct30_formatsLargeNumbers() {
        enterNumber("99999")
        tapOperation(Operation.MULTIPLICATION)
        enterNumber("2")
        tapEquals()
        assertDisplayEquals("199998.0")
    }

    @Test
    fun ct31_acClearsAfterHistory() {
        performBinaryOperation("9", Operation.ADDITION, "1", 10f)
        tapSpecial(Operation.AC)
        assertDisplayEquals("0")
    }

    @Test
    fun ct32_canChangeOperatorBeforeCompletion() {
        enterNumber("5")
        tapOperation(Operation.ADDITION)
        tapOperation(Operation.SUBTRACTION)
        enterNumber("3")
        tapEquals()
        assertDisplayApprox(2f)
    }

    @Test
    fun ct33_ignoresLeadingZeros() {
        tapDigit('0')
        tapDigit('0')
        tapDigit('5')
        assertDisplayEquals("5")
    }

    @Test
    fun ct34_canReusePreviousResult() {
        performBinaryOperation("5", Operation.ADDITION, "3", 8f)
        tapOperation(Operation.MULTIPLICATION)
        enterNumber("2")
        tapEquals()
        assertDisplayApprox(16f)
    }

    @Test
    fun ct35_equalsWithoutOperationKeepsValue() {
        enterNumber("999999999999")
        tapEquals()
        assertDisplayEquals("999999999999")
    }

    @Test
    fun ct36_togglesSign() {
        enterNumber("5")
        tapSpecial(Operation.PLUS_MINUS)
        assertDisplayEquals("-5")
        tapSpecial(Operation.PLUS_MINUS)
        assertDisplayEquals("5")
        tapSpecial(Operation.AC)
        tapSpecial(Operation.PLUS_MINUS)
        assertDisplayEquals("0")
    }

    @Test
    fun ct36_additional_percentage() {
        enterNumber("50")
        tapSpecial(Operation.PERCENTAGE)
        assertDisplayApprox(0.5f)
    }

    @Test
    fun ct37_reportsOverflowAsInfinity() {
        enterNumber("999999999999")
        repeat(3) {
            tapOperation(Operation.MULTIPLICATION)
            enterNumber("999999999999")
            tapEquals()
        }
        val actual = readDisplay().toFloat()
        assertTrue(actual.isInfinite())
    }

    @Test
    fun ct38_handlesUnderflowForSmallResults() {
        enterNumber("1")
        tapOperation(Operation.DIVISION)
        enterNumber("999999999999")
        tapEquals()
        val actual = readDisplay().toFloat()
        assertTrue(actual > 0f && actual < 0.001f)
    }

    private fun performBinaryOperation(first: String, operation: Operation, second: String, expected: Float) {
        enterNumber(first)
        tapOperation(operation)
        enterNumber(second)
        tapEquals()
        assertDisplayApprox(expected)
    }

    private fun enterNumber(value: String) {
        val sanitized = value.replace(" ", "")
        val isNegative = sanitized.startsWith("-")
        val content = if (isNegative) sanitized.drop(1) else sanitized
        content.forEach { char ->
            when {
                char.isDigit() -> tapDigit(char)
                char == '.' || char == ',' -> tapSpecial(Operation.COMMA)
            }
        }
        if (isNegative) {
            tapSpecial(Operation.PLUS_MINUS)
        }
    }

    private fun tapDigit(digit: Char) {
        require(digit.isDigit()) { "Digit expected, received: $digit" }
        composeRule.onNodeWithTag(TestTags.number(digit.toString())).performClick()
    }

    private fun tapOperation(operation: Operation) {
        composeRule.onNodeWithTag(TestTags.operator(operation)).performClick()
    }

    private fun tapSpecial(operation: Operation) {
        composeRule.onNodeWithTag(TestTags.special(operation)).performClick()
    }

    private fun tapEquals() {
        tapOperation(Operation.EQUALS)
    }

    private fun assertDisplayEquals(expected: String) {
        composeRule.onNodeWithTag(TestTags.DISPLAY).assertTextEquals(expected)
    }

    private fun assertDisplayApprox(expected: Float, delta: Float = 0.001f) {
        val actual = readDisplay().toFloat()
        assertEquals(expected, actual, delta)
    }

    private fun readDisplay(): String {
        val node = composeRule.onNodeWithTag(TestTags.DISPLAY).fetchSemanticsNode()
        node.config.getOrNull(SemanticsProperties.EditableText)?.let { editable ->
            return editable.text
        }
        node.config.getOrNull(SemanticsProperties.Text)?.let { texts ->
            if (texts.isNotEmpty()) {
                return texts.joinToString(separator = "") { it.text }
            }
        }
        return ""
    }
}
