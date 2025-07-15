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
```

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