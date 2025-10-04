package com.mobileinsights.calculator

// Importações do JUnit 5
import com.mobileinsights.calculator.model.Calculator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.DisplayName

/**
 * Testes unitários locais para a lógica da calculadora, usando JUnit 5.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorTest {

    // Testes de Adição (ADD)
    @Test
    @DisplayName("Adição de dois números positivos")
    fun `addition with positive numbers should be correct`() {
        assertEquals(4.0f, Calculator.Addition(2.0f, 2.0f)())
    }

    @Test
    @DisplayName("Adição com valor mínimo (Float.MIN_VALUE)")
    fun `addition with min value should work correctly`() {
        // MIN_VALUE é o menor valor positivo, não o mais negativo.
        // O resultado será praticamente o mesmo que o segundo operando.
        assertEquals(5.0f, Calculator.Addition(Float.MIN_VALUE, 5.0f)())
    }

    // Testes de Subtração (SUBTRACT)
    @Test
    @DisplayName("Subtração de dois números")
    fun `subtraction should be correct`() {
        assertEquals(5.0f, Calculator.Subtraction(10.0f, 5.0f)())
    }

    @Test
    @DisplayName("Subtração de valor máximo deve funcionar")
    fun `subtraction from max value should work correctly`() {
        val expected = Float.MAX_VALUE - 100.0f
        assertEquals(expected, Calculator.Subtraction(Float.MAX_VALUE, 100.0f)())
    }

    // Testes de Multiplicação (MULTIPLY)
    @Test
    @DisplayName("Multiplicação de dois números")
    fun `multiplication should be correct`() {
        assertEquals(25.0f, Calculator.Multiplication(5.0f, 5.0f)())
    }

    @Test
    @DisplayName("Multiplicação por valor máximo deve resultar em infinito")
    fun `multiplication by max value should result in infinity`() {
        val result = Calculator.Multiplication(Float.MAX_VALUE, 2.0f)() as Float
        assertTrue(result.isInfinite(), "Multiplying Float.MAX_VALUE should result in Infinity")
    }

    // Testes de Divisão (DIVIDE)
    @Test
    @DisplayName("Divisão de dois números")
    fun `division should be correct`() {
        assertEquals(4.0f, Calculator.Division(20.0f, 5.0f)())
    }

    @Test
    @DisplayName("Divisão por zero deve lançar IllegalArgumentException")
    fun `division by zero should throw exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            Calculator.Division(10.0f, 0.0f)()
        }
        assertEquals("Division by zero is not allowed.", exception.message)
    }

    @Test
    @DisplayName("Divisão de valor máximo deve funcionar")
    fun `division of max value should work correctly`() {
        val expected = Float.MAX_VALUE / 2.0f
        assertEquals(expected, Calculator.Division(Float.MAX_VALUE, 2.0f)())
    }
}
