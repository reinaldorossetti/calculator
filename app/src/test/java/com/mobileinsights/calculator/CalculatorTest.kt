package com.mobileinsights.calculator

// Importações do JUnit 5
import com.mobileinsights.calculator.model.Calculator
import com.mobileinsights.calculator.model.Operation
import com.mobileinsights.calculator.viewmodel.CalculatorViewModel
import com.mobileinsights.calculator.viewmodel.CalculatorEvent
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * Testes unitários completos para a calculadora, usando JUnit 5.
 * Organizados por categorias conforme especificação CT01-CT38.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Epic("Calculadora")
@Feature("Operações Matemáticas e Interface")
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
    @Story("Operações Básicas")
    @Description("CT01: Inicialização - Display deve começar com 0")
    @DisplayName("CT01: Inicialização - Display deve começar com 0")
    fun testInicializacaoDisplayZero() {
        assertEquals("0", viewModel.entryState.value)
    }

    @Test
    @Story("Operações Básicas")
    @Description("CT02: Entrada de dígitos - Deve aceitar entrada de números de 0-9")
    @DisplayName("CT02: Entrada de dígitos - Deve aceitar entrada de números de 0-9")
    fun testEntradaDigitos() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        assertEquals("5", viewModel.entryState.value)

        viewModel.onEvent(CalculatorEvent.Number(3))
        assertEquals("53", viewModel.entryState.value)
    }

    @Test
    @Story("Operações Básicas")
    @Description("CT03: Entrada decimal - Deve aceitar números decimais")
    @DisplayName("CT03: Entrada decimal - Deve aceitar números decimais")
    fun testEntradaDecimal() {
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.COMMA))
        viewModel.onEvent(CalculatorEvent.Number(1))
        viewModel.onEvent(CalculatorEvent.Number(4))

        assertEquals("3.14", viewModel.entryState.value)
    }

    @Test
    @Story("Operações Básicas")
    @Description("CT04: Limite de dígitos - Deve limitar entrada a 12 caracteres")
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
    @Story("Adição")
    @Description("CT05: Adição de inteiros - 5 + 3 = 8")
    @DisplayName("CT05: Adição de inteiros - 5 + 3 = 8")
    fun testAdicaoInteiros() {
        assertEquals(8.0f, Calculator.Addition(5.0f, 3.0f)())
    }

    @Test
    @Story("Adição")
    @Description("CT06: Adição de decimais - 2.5 + 3.7 = 6.2")
    @DisplayName("CT06: Adição de decimais - 2.5 + 3.7 = 6.2")
    fun testAdicaoDecimais() {
        val result = Calculator.Addition(2.5f, 3.7f)() as Float
        assertEquals(6.2f, result, 0.001f)
    }

    @Test
    @Story("Adição")
    @Description("CT07: Adição em cadeia - 1 + 2 + 3 = 6")
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
    @Story("Adição")
    @Description("CT08: Adição com zero - 7 + 0 = 7")
    @DisplayName("CT08: Adição com zero - 7 + 0 = 7")
    fun testAdicaoComZero() {
        assertEquals(7.0f, Calculator.Addition(7.0f, 0.0f)())
    }

    // ============================================================================
    // SUBTRAÇÃO (CT09-CT12)
    // ============================================================================

    @Test
    @Story("Subtração")
    @Description("CT09: Subtração de inteiros - 10 - 4 = 6")
    @DisplayName("CT09: Subtração de inteiros - 10 - 4 = 6")
    fun testSubtracaoInteiros() {
        assertEquals(6.0f, Calculator.Subtraction(10.0f, 4.0f)())
    }

    @Test
    @Story("Subtração")
    @Description("CT10: Subtração de decimais - 5.8 - 2.3 = 3.5")
    @DisplayName("CT10: Subtração de decimais - 5.8 - 2.3 = 3.5")
    fun testSubtracaoDecimais() {
        val result = Calculator.Subtraction(5.8f, 2.3f)() as Float
        assertEquals(3.5f, result, 0.001f)
    }

    @Test
    @Story("Subtração")
    @Description("CT11: Subtração resultando em negativo - 3 - 7 = -4")
    @DisplayName("CT11: Subtração resultando em negativo - 3 - 7 = -4")
    fun testSubtracaoNegativo() {
        assertEquals(-4.0f, Calculator.Subtraction(3.0f, 7.0f)())
    }

    @Test
    @Story("Subtração")
    @Description("CT12: Subtração com zero - 9 - 0 = 9")
    @DisplayName("CT12: Subtração com zero - 9 - 0 = 9")
    fun testSubtracaoComZero() {
        assertEquals(9.0f, Calculator.Subtraction(9.0f, 0.0f)())
    }

    // ============================================================================
    // MULTIPLICAÇÃO (CT13-CT16)
    // ============================================================================

    @Test
    @Story("Multiplicação")
    @Description("CT13: Multiplicação de inteiros - 6 * 7 = 42")
    @DisplayName("CT13: Multiplicação de inteiros - 6 * 7 = 42")
    fun testMultiplicacaoInteiros() {
        assertEquals(42.0f, Calculator.Multiplication(6.0f, 7.0f)())
    }

    @Test
    @Story("Multiplicação")
    @Description("CT14: Multiplicação de decimais - 2.5 * 4.0 = 10.0")
    @DisplayName("CT14: Multiplicação de decimais - 2.5 * 4.0 = 10.0")
    fun testMultiplicacaoDecimais() {
        assertEquals(10.0f, Calculator.Multiplication(2.5f, 4.0f)())
    }

    @Test
    @Story("Multiplicação")
    @Description("CT15: Multiplicação por zero - 8 * 0 = 0")
    @DisplayName("CT15: Multiplicação por zero - 8 * 0 = 0")
    fun testMultiplicacaoPorZero() {
        assertEquals(0.0f, Calculator.Multiplication(8.0f, 0.0f)())
    }

    @Test
    @Story("Multiplicação")
    @Description("CT16: Multiplicação por negativo - 5 * -3 = -15")
    @DisplayName("CT16: Multiplicação por negativo - 5 * -3 = -15")
    fun testMultiplicacaoPorNegativo() {
        assertEquals(-15.0f, Calculator.Multiplication(5.0f, -3.0f)())
    }

    // ============================================================================
    // DIVISÃO (CT17-CT20)
    // ============================================================================

    @Test
    @Story("Divisão")
    @Description("CT17: Divisão de inteiros - 20 / 4 = 5")
    @DisplayName("CT17: Divisão de inteiros - 20 / 4 = 5")
    fun testDivisaoInteiros() {
        assertEquals(5.0f, Calculator.Division(20.0f, 4.0f)())
    }

    @Test
    @Story("Divisão")
    @Description("CT18: Divisão de decimais - 7.5 / 2.5 = 3.0")
    @DisplayName("CT18: Divisão de decimais - 7.5 / 2.5 = 3.0")
    fun testDivisaoDecimais() {
        assertEquals(3.0f, Calculator.Division(7.5f, 2.5f)())
    }

    @Test
    @Story("Divisão")
    @Description("CT19: Divisão por zero - Deve lançar exceção")
    @DisplayName("CT19: Divisão por zero - Deve lançar exceção")
    fun testDivisaoPorZero() {
        val exception = assertThrows<IllegalArgumentException> {
            Calculator.Division(10.0f, 0.0f)()
        }
        assertEquals("Division by zero is not allowed.", exception.message)
    }

    @Test
    @Story("Divisão")
    @Description("CT20: Divisão em cadeia - 100 / 2 / 5 = 10")
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
    @Story("Módulo")
    @Description("CT21: Módulo - Resto da divisão 10 % 3 = 1")
    @DisplayName("CT21: Módulo - Resto da divisão 10 % 3 = 1")
    fun testModuloResto() {
        assertEquals(1.0f, Calculator.Modulo(10.0f, 3.0f)())
    }

    @Test
    @Story("Módulo")
    @Description("CT22: Módulo - Número divisível 15 % 5 = 0")
    @DisplayName("CT22: Módulo - Número divisível 15 % 5 = 0")
    fun testModuloDivisivel() {
        assertEquals(0.0f, Calculator.Modulo(15.0f, 5.0f)())
    }

    @Test
    @Story("Módulo")
    @Description("CT23: Módulo com decimais - 10.5 % 3.0")
    @DisplayName("CT23: Módulo com decimais - 10.5 % 3.0")
    fun testModuloDecimais() {
        val result = Calculator.Modulo(10.5f, 3.0f)() as Float
        assertEquals(1.5f, result, 0.001f)
    }

    // ============================================================================
    // FUNÇÃO AC (CT24-CT26)
    // ============================================================================

    @Test
    @Story("Função AC")
    @Description("CT24: AC - Limpar valor atual do display")
    @DisplayName("CT24: AC - Limpar valor atual do display")
    fun testACLimparValor() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Number(8))
        viewModel.onEvent(CalculatorEvent.AllClear)

        assertEquals("0", viewModel.entryState.value)
    }

    @Test
    @Story("Função AC")
    @Description("CT25: AC - Limpar operação pendente")
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
    @Story("Função AC")
    @Description("CT26: AC - Limpar após cálculo completo")
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
    @Story("Histórico")
    @Description("CT27: Histórico - Armazenar operação realizada")
    @DisplayName("CT27: Histórico - Armazenar operação realizada")
    fun testHistoricoArmazenar() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("8.0", viewModel.entryState.value)
    }

    @Test
    @Story("Histórico")
    @Description("CT28: Histórico - Múltiplas operações")
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

    @Story("Histórico")
    @Description("CT29: Histórico - Limite de 2 operações")
    @DisplayName("CT29: Histórico - Limite de 2 operações")
    @RepeatedTest(3)
    fun testHistoricoLimite() {
        // Este teste documenta que o histórico deve ter limite
        // TODO: A implementação atual mantém apenas o estado atual, teste precisa ser melhorado
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Number(3))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("8.0", viewModel.entryState.value)
    }

    @Test
    @Story("Histórico")
    @Description("CT30: Histórico - Formatação de números grandes")
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
    @Story("Histórico")
    @Description("CT31: Histórico - Limpar com AC")
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
    @Story("Casos Especiais")
    @Description("CT32: Trocar operador - Mudar + para - antes de completar")
    @DisplayName("CT32: Trocar operador - Mudar + para - antes de completar")
    fun testTrocarOperador() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.ADDITION))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.SUBTRACTION))

        assertEquals(Operation.SUBTRACTION, viewModel.buttonState.value)
    }

    @Test
    @Story("Casos Especiais")
    @Description("CT33: Zero à esquerda - 005 deve ser tratado como 5")
    @DisplayName("CT33: Zero à esquerda - 005 deve ser tratado como 5")
    fun testZeroEsquerda() {
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Number(5))

        assertEquals("5", viewModel.entryState.value)
    }

    @Test
    @Story("Casos Especiais")
    @Description("CT34: Operação após resultado - Usar resultado anterior")
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
    @Story("Casos Especiais")
    @Description("CT35: Passando o valor maximo de range permitido - Long: De -9.223.372.036.854.775.808 a 9.223.372.036.854.775.807 (64 bits).")
    @DisplayName("CT35: Passando o valor maximo de range permitido - Long: De -9.223.372.036.854.775.808 a 9.223.372.036.854.775.807 (64 bits).")
    fun testIgualdadeSemOperacao() {
        viewModel.onEvent(CalculatorEvent.Number(9223372036854775807))
        viewModel.onEvent(CalculatorEvent.Equals)

        assertEquals("9223372036854775807", viewModel.entryState.value)
    }

    @Test
    @Story("Casos Especiais")
    @Description("CT36 Adicional: Teste de inversão de sinal (+/-)")
    @DisplayName("CT36 Adicional: Teste de inversão de sinal")
    fun testToggleSign() {
        // Teste com número positivo
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.PLUS_MINUS))
        assertEquals("-5", viewModel.entryState.value)

        // Teste com número negativo
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.PLUS_MINUS))
        assertEquals("5", viewModel.entryState.value)

        // Teste com zero (não deve mudar)
        viewModel.onEvent(CalculatorEvent.AllClear)
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.PLUS_MINUS))
        assertEquals("0", viewModel.entryState.value)
    }

    // ============================================================================
    // TESTES ADICIONAIS DE ROBUSTEZ
    // ============================================================================

    @Test
    @Story("Robustez")
    @Description("CT36 Adicional: Percentual - 50% deve resultar em 0.5")
    @DisplayName("CT36 Adicional: Percentual - 50% deve resultar em 0.5")
    fun testPercentual() {
        viewModel.onEvent(CalculatorEvent.Number(5))
        viewModel.onEvent(CalculatorEvent.Number(0))
        viewModel.onEvent(CalculatorEvent.Calculation(Operation.PERCENTAGE))

        val result = viewModel.entryState.value.toFloat()
        assertEquals(0.5f, result, 0.001f)
    }

    // Deve ter um valor de limite na calculadora.

    @Test
    @Story("Robustez")
    @Description("CT37 Adicional: Overflow - Número muito grande")
    @DisplayName("CT37 Adicional: Overflow - Número muito grande")
    fun testOverflow() {
        val result = Calculator.Multiplication(Float.MAX_VALUE, 2.0f)() as Float
        assertTrue(result.isInfinite())
    }

    @Test
    @Story("Robustez")
    @Description("CT38 Adicional: Underflow - Número muito pequeno")
    @DisplayName("CT38 Adicional: Underflow - Número muito pequeno")
    fun testUnderflow() {
        val result = Calculator.Division(1.0f, Float.MAX_VALUE)() as Float
        assertTrue(result > 0 && result < 0.001f)
    }

}
