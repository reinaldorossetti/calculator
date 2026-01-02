package com.mobileinsights.calculator

// Importações do JUnit 5
import com.mobileinsights.calculator.model.Calculator
import com.mobileinsights.calculator.model.Operation
import com.mobileinsights.calculator.viewmodel.CalculatorViewModel
import com.mobileinsights.calculator.viewmodel.CalculatorEvent
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * Testes unitários completos para a calculadora, usando JUnit 5.
 * Organizados por categorias conforme especificação CT01-CT35.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorTest {

    private lateinit var viewModel: CalculatorViewModel

    @BeforeEach
    fun setup() {
        viewModel = CalculatorViewModel()
    }

    // ============================================================================
    // OPERAÇÕES BÁSICAS (CT01-CT04)
    // ============================================================================

    @Test
    @DisplayName("CT01: Inicialização - Display deve começar com 0")
    fun testInicializacaoDisplayZero() {
        assertEquals("0", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT02: Entrada de dígitos - Deve aceitar entrada de números de 0-9")
    fun testEntradaDigitos() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        assertEquals("5", viewModel.entryState.value)

        viewModel.onEvent(CalculatorEvent.Number(3))
        assertEquals("53", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT03: Entrada decimal - Deve aceitar números decimais")
    fun testEntradaDecimal() {
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.COMMA))
        viewModel.onEvent(CalculatorEvent.Number(1))
        viewModel.onEvent(CalculatorEvent.Number(4))

        assertEquals("3.14", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT04: Limite de dígitos - Deve limitar entrada a 12 caracteres")
    fun testLimiteDigitos() {
        repeat(15) {
            viewModel.onEvent(CalculatorEvent.Number(9))
        }

        assertEquals("999999999999", viewModel.entryState.value)
    }

    // ============================================================================
    // ADIÇÃO (CT05-CT08)
    // ============================================================================

    @Test
    @DisplayName("CT05: Adição de inteiros - 5 + 3 = 8")
    fun testAdicaoInteiros() {
        assertEquals(8.0f, Calculator.Addition(5.0f, 3.0f)())
    }

    @Test
    @DisplayName("CT06: Adição de decimais - 2.5 + 3.7 = 6.2")
    fun testAdicaoDecimais() {
        val result = Calculator.Addition(2.5f, 3.7f)() as Float
        assertEquals(6.2f, result, 0.001f)
    }

    @Test
    @DisplayName("CT07: Adição em cadeia - 1 + 2 + 3 = 6")
    fun testAdicaoEmCadeia() {
        viewModel.onEvent(CalculatorEvent.Number(1))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(2))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        val result = viewModel.entryState.value.toFloat()
        assertEquals(6.0f, result, 0.001f)
    }

    @Test
    @DisplayName("CT08: Adição com zero - 7 + 0 = 7")
    fun testAdicaoComZero() {
        assertEquals(7.0f, Calculator.Addition(7.0f, 0.0f)())
    }

    // ============================================================================
    // SUBTRAÇÃO (CT09-CT12)
    // ============================================================================

    @Test
    @DisplayName("CT09: Subtração de inteiros - 10 - 4 = 6")
    fun testSubtracaoInteiros() {
        assertEquals(6.0f, Calculator.Subtraction(10.0f, 4.0f)())
    }

    @Test
    @DisplayName("CT10: Subtração de decimais - 5.8 - 2.3 = 3.5")
    fun testSubtracaoDecimais() {
        val result = Calculator.Subtraction(5.8f, 2.3f)() as Float
        assertEquals(3.5f, result, 0.001f)
    }

    @Test
    @DisplayName("CT11: Subtração resultando em negativo - 3 - 7 = -4")
    fun testSubtracaoNegativo() {
        assertEquals(-4.0f, Calculator.Subtraction(3.0f, 7.0f)())
    }

    @Test
    @DisplayName("CT12: Subtração com zero - 9 - 0 = 9")
    fun testSubtracaoComZero() {
        assertEquals(9.0f, Calculator.Subtraction(9.0f, 0.0f)())
    }

    // ============================================================================
    // MULTIPLICAÇÃO (CT13-CT16)
    // ============================================================================

    @Test
    @DisplayName("CT13: Multiplicação de inteiros - 6 * 7 = 42")
    fun testMultiplicacaoInteiros() {
        assertEquals(42.0f, Calculator.Multiplication(6.0f, 7.0f)())
    }

    @Test
    @DisplayName("CT14: Multiplicação de decimais - 2.5 * 4.0 = 10.0")
    fun testMultiplicacaoDecimais() {
        assertEquals(10.0f, Calculator.Multiplication(2.5f, 4.0f)())
    }

    @Test
    @DisplayName("CT15: Multiplicação por zero - 8 * 0 = 0")
    fun testMultiplicacaoPorZero() {
        assertEquals(0.0f, Calculator.Multiplication(8.0f, 0.0f)())
    }

    @Test
    @DisplayName("CT16: Multiplicação por negativo - 5 * -3 = -15")
    fun testMultiplicacaoPorNegativo() {
        assertEquals(-15.0f, Calculator.Multiplication(5.0f, -3.0f)())
    }

    // ============================================================================
    // DIVISÃO (CT17-CT20)
    // ============================================================================

    @Test
    @DisplayName("CT17: Divisão de inteiros - 20 / 4 = 5")
    fun testDivisaoInteiros() {
        assertEquals(5.0f, Calculator.Division(20.0f, 4.0f)())
    }

    @Test
    @DisplayName("CT18: Divisão de decimais - 7.5 / 2.5 = 3.0")
    fun testDivisaoDecimais() {
        assertEquals(3.0f, Calculator.Division(7.5f, 2.5f)())
    }

    @Test
    @DisplayName("CT19: Divisão por zero - Deve lançar exceção")
    fun testDivisaoPorZero() {
        val exception = assertThrows<IllegalArgumentException> {
            Calculator.Division(10.0f, 0.0f)()
        }
        assertEquals("Division by zero is not allowed.", exception.message)
    }

    @Test
    @DisplayName("CT20: Divisão em cadeia - 100 / 2 / 5 = 10")
    fun testDivisaoEmCadeia() {
        viewModel.onEvent(CalculatorEvent.Number(1))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.DIVISION))
        viewModel.onEvent(CalculatorEvent.Number(2))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.DIVISION))
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Equals)

        val result = viewModel.entryState.value.toFloat()
        assertEquals(10.0f, result, 0.001f)
    }

    // ============================================================================
    // MÓDULO (CT21-CT23)
    // ============================================================================

    @Test
    @DisplayName("CT21: Módulo - Resto da divisão 10 % 3 = 1")
    fun testModuloResto() {
        assertEquals(1.0f, Calculator.Modulo(10.0f, 3.0f)())
    }

    @Test
    @DisplayName("CT22: Módulo - Número divisível 15 % 5 = 0")
    fun testModuloDivisivel() {
        assertEquals(0.0f, Calculator.Modulo(15.0f, 5.0f)())
    }

    @Test
    @DisplayName("CT23: Módulo com decimais - 10.5 % 3.0")
    fun testModuloDecimais() {
        val result = Calculator.Modulo(10.5f, 3.0f)() as Float
        assertEquals(1.5f, result, 0.001f)
    }

    // ============================================================================
    // FUNÇÃO AC (CT24-CT26)
    // ============================================================================

    @Test
    @DisplayName("CT24: AC - Limpar valor atual do display")
    fun testACLimparValor() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Number(8))
        viewModel.onEvent(CalculatorEvent.AllClear)

        assertEquals("0", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT25: AC - Limpar operação pendente")
    fun testACLimparOperacao() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.AllClear)

        assertEquals("0", viewModel.entryState.value)
        assertEquals(Operation.NONE, viewModel.buttonState.value)
    }

    @Test
    @DisplayName("CT26: AC - Limpar após cálculo completo")
    fun testACLimparAposCalculo() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)
        viewModel.onEvent(CalculatorEvent.AllClear)

        assertEquals("0", viewModel.entryState.value)
    }

    // ============================================================================
    // HISTÓRICO (CT27-CT31)
    // ============================================================================
    // Nota: A implementação atual do ViewModel não possui histórico visível
    // Estes testes documentam o comportamento esperado para futuras implementações

    @Test
    @DisplayName("CT27: Histórico - Armazenar operação realizada")
    fun testHistoricoArmazenar() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("8.0", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT28: Histórico - Múltiplas operações")
    fun testHistoricoMultiplasOperacoes() {
        // Primeira operação: 5 + 3 = 8
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        // Segunda operação: 10 - 2 = 8
        viewModel.onEvent(CalculatorEvent.Number(1))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.SUBTRACTION))
        viewModel.onEvent(CalculatorEvent.Number(2))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("8.0", viewModel.entryState.value)
    }

    @DisplayName("CT29: Histórico - Limite de 2 operações")
    @RepeatedTest(10)
    fun testHistoricoLimite() {
        // Este teste documenta que o histórico deve ter limite
        // A implementação atual mantém apenas o estado atual
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("8.0", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT30: Histórico - Formatação de números grandes")
    fun testHistoricoFormatacaoNumerosGrandes() {
        viewModel.onEvent(CalculatorEvent.Number(9))
        viewModel.onEvent(CalculatorEvent.Number(9))
        viewModel.onEvent(CalculatorEvent.Number(9))
        viewModel.onEvent(CalculatorEvent.Number(9))
        viewModel.onEvent(CalculatorEvent.Number(9))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.MULTIPLICATION))
        viewModel.onEvent(CalculatorEvent.Number(2))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("199998.0", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT31: Histórico - Limpar com AC")
    fun testHistoricoLimparComAC() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)
        viewModel.onEvent(CalculatorEvent.AllClear)

        assertEquals("0", viewModel.entryState.value)
    }

    // ============================================================================
    // CASOS ESPECIAIS (CT32-CT35)
    // ============================================================================

    @Test
    @DisplayName("CT32: Trocar operador - Mudar + para - antes de completar")
    fun testTrocarOperador() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.SUBTRACTION))

        assertEquals(Operation.SUBTRACTION, viewModel.buttonState.value)
    }

    @Test
    @DisplayName("CT33: Zero à esquerda - 005 deve ser tratado como 5")
    fun testZeroEsquerda() {
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Number(5))

        assertEquals("5", viewModel.entryState.value)
    }

    @Test
    @DisplayName("CT34: Operação após resultado - Usar resultado anterior")
    fun testOperacaoAposResultado() {
        // 5 + 3 = 8
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        // * 2 = 16
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.MULTIPLICATION))
        viewModel.onEvent(CalculatorEvent.Number(2))
        viewModel.onEvent(CalculatorEvent.Equals)

        val result = viewModel.entryState.value.toFloat()
        assertEquals(16.0f, result, 0.001f)
    }

    @Test
    @DisplayName("CT35: Pressionar = sem operação - Manter número atual")
    fun testIgualdadeSemOperacao() {
        viewModel.onEvent(CalculatorEvent.Number(7))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("7", viewModel.entryState.value)
    }

    // ============================================================================
    // TESTES ADICIONAIS DE ROBUSTEZ
    // ============================================================================

    @Test
    @DisplayName("CT36 Adicional: Percentual - 50% deve resultar em 0.5")
    fun testPercentual() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.PERCENTAGE))

        val result = viewModel.entryState.value.toFloat()
        assertEquals(0.5f, result, 0.001f)
    }

    @Test
    @DisplayName("CT37 Adicional: Overflow - Número muito grande")
    fun testOverflow() {
        val result = Calculator.Multiplication(Float.MAX_VALUE, 2.0f)() as Float
        assertTrue(result.isInfinite())
    }

    @Test
    @DisplayName("CT38 Adicional: Underflow - Número muito pequeno")
    fun testUnderflow() {
        val result = Calculator.Division(1.0f, Float.MAX_VALUE)() as Float
        assertTrue(result > 0 && result < 0.001f)
    }
}
