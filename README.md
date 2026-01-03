# Calculadora Android 🧮

Simples calculadora desenvolvida para o curso de Automação Básica com Robot Framework para Mobile.

## Sumário 📑

- [Sobre o Projeto](#sobre-o-projeto) ℹ️
- [Pré-requisitos](#pré-requisitos) ⚙️
- [Instalação](#instalação) 💻
- [Execução](#execução) ▶️
- [Estrutura do Projeto](#estrutura-do-projeto) 🗂️
- [Arquitetura Utilizada](#arquitetura-utilizada) 🏗️
- [Referências](#referências) 📚

## Sobre o Projeto ℹ️

Este projeto é um clone de calculadora para Android, desenvolvido com Kotlin e Jetpack Compose. O objetivo é servir como base para automação de testes mobile utilizando Robot Framework.

## Pré-requisitos ⚙️

- **Android Studio** (recomendado: versão Hedgehog ou superior)
- **JDK 17** ou superior
- **Gradle** (o wrapper já está incluso no projeto)
- **Emulador Android** ou dispositivo físico
- **Conexão à internet** para baixar dependências

## Instalação 💻

Clone o repositório:

```sh
git clone https://github.com/seu-usuario/calculator.git
cd calculator
```

Abra o projeto no **Android Studio**:

1. Clique em `File > Open...` e selecione a pasta do projeto.
2. Aguarde o download das dependências.

## Execução ▶️

Para rodar o projeto:

- No Android Studio, clique em **Run** (`Shift + F10`) ou selecione um dispositivo/emulador e clique no botão de execução.
- Alternativamente, via terminal:

```sh
./gradlew assembleDebug
./gradlew installDebug
./gradlew clean test --stacktrace
```

### Por que uma nova versão?
A quarta versão do JUnit foi lançada há mais de 10 anos atrás, e melhorias eram necessárias tanto para acompanhar a evolução da linguagem, como para atender as expectativas sobre testes que aumentam a cada ano. Alguns dos pontos importantes para a decisão de se construir uma nova versão foram:

Alto acoplamento entre o internals do JUnit com ferramentas de IDE, dificultando o acolhimento de novidades.
Necessidade de modularização.
Java 8 e Java 9.
Um melhor modelo de extensão que elimine as limitações e complexidades de Rules e Runners.
Houve uma grande movimentação da comunidade e uma arrecadação de fundos para possibilitar o inicio do desenvolvimento da nova versão, inicialmente batizada de JUnit Lambda, posteriormente se tornou JUnit 5.

### Modularização - Testes Unitários/Unidade
JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage

Diferente de outras versões, JUnit 5 é composto por diferentes módulos distribuídos em 3 projetos:

JUnit Platform: São elementos estruturais para a execução dos testes na JVM. Fornece API para execução dos testes pela linha de comando e também plugins para Gradle e Maven, além de prover Runner baseado no JUnit 4, para que seja possível rodar qualquer TestEngine na plataforma.
JUnit Jupiter: É o JUnit 5 de fato, aqui está definido o modelo de programação e de extensão, ou seja, aqui estão todas as anotações, classes e também as novidades do JUnit 5.
JUnit Vintage: Fornece uma TestEngine para rodar testes baseados em JUnit 3 e JUnit 4.

### Repeated Tests
Particularmente essa foi uma das novidades que mais gostei. Geralmente na rotina de manutenção de nossa suíte de testes, nos deparamos com os chamados flaky test, são aqueles testes que as vezes rodam com sucesso e outras falham. Para dar manutenção e resolver esses testes é muito trabalhoso, por que você deve rodar o teste muitas vezes para verificar se não está mais intermitente, e rodar manualmente pela IDE é sempre algo trabalhoso.

Disabling Tests
No JUnit 4 tinhamos a anotação @Ignore para que na hora da execução os testes anotados pudessem ser ignorados, agora no JUnit 5 temos a anotação @Disabled que substitui o @Ignore.

## Estrutura do Projeto 🗂️

```
calculator/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── mobileinsights/
│   │   │   │           ├── calculator/
│   │   │   │           │   ├── model/
│   │   │   │           │   │   └── Calculator.kt      # Lógica dos cálculos e operações
│   │   │   │           │   ├── ui/
│   │   │   │           │   │   ├── CalculatorScreen.kt # Tela principal da calculadora (Jetpack Compose)
│   │   │   │           │   │   └── components/
│   │   │   │           │   │       └── ButtonPad.kt    # Componentes reutilizáveis da interface
│   │   │   │           │   └── CalculatorViewModel.kt  # ViewModel para lógica de UI e estado
│   │   │   └── res/
│   │   │       ├── layout/                             # Layouts XML (se houver)
│   │   │       └── values/                             # Strings, temas, dimensões
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── ...
```

### Detalhamento das Pastas 📝

- **model/**  
  Contém a lógica das operações matemáticas.  
  - `Calculator.kt`: Implementa as operações (adição, subtração, multiplicação, divisão) e pode armazenar os últimos resultados.

- **ui/**  
  Contém toda a interface do usuário usando Jetpack Compose.  
  - `CalculatorScreen.kt`: Tela principal da calculadora.
  - `components/`: Componentes reutilizáveis, como botões e display.
    - `ButtonPad.kt`: Grade de botões da calculadora.

- **CalculatorViewModel.kt**  
  Gerencia o estado da calculadora e integra a lógica do model com a interface.

## Arquitetura Utilizada 🏗️

O projeto utiliza a arquitetura **MVVM (Model-View-ViewModel)**, recomendada para projetos Android modernos com Jetpack Compose.

- **Model:** Regras de negócio e operações matemáticas (`model/Calculator.kt`).
- **View:** Interface do usuário (`ui/CalculatorScreen.kt`, `ui/components/ButtonPad.kt`).
- **ViewModel:** Gerenciamento de estado e lógica de interação (`CalculatorViewModel.kt`).

### Exemplo de ViewModel

````kotlin
class CalculatorViewModel : ViewModel() {
    private val _results = mutableStateListOf<Float>()
    val results: List<Float> get() = _results

    fun addResult(result: Float) {
        if (_results.size >= 3) _results.removeAt(0)
        _results.add(result)
    }
}
````
## Referências:

- [Artigo Medium: Building an iPhone Calculator Clone on Android with Kotlin and Jetpack Compose](https://www.mobileinsights.dev/building-an-iphone-calculator-clone-on-android-with-kotlin-and-jetpack-compose-87e74bfb1bad)
- [Utilizando JUnit 5 no Android](https://medium.com/android-dev-br/utilizando-junit-5-no-android-82d752708985)