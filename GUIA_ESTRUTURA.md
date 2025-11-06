# 📚 Guia Completo da Estrutura do Sistema de Lanchonete

## 🎯 **Visão Geral**

Este projeto implementa um **Sistema de Pedidos de Lanchonete** usando **padrões de projeto** em Java. É como um sistema de uma pizzaria/lanchonete onde você pode montar seu pedido escolhendo ingredientes, tamanhos, etc.

### 🧩 **Padrões de Projeto Utilizados:**
1. **Builder** - Para construir objetos complexos (lanches/pizzas) passo a passo
2. **Factory Method** - Para criar diferentes tipos de ingredientes (padrão/premium/promocional)
3. **Strategy** - Para diferentes estratégias de validação

---

## 📁 **Estrutura de Pastas e Arquivos**

```
src/com/lanchonete/
├── 📂 enums/              # Definições de opções fixas
│   ├── Tamanho.java       # Pequeno, Médio, Grande
│   ├── TipoMassa.java     # Tipos de pão e massa
│   ├── TipoIngrediente.java # Todos os ingredientes disponíveis
│   └── SaborPizza.java    # Sabores pré-definidos de pizza
│
├── 📂 model/              # Classes principais do sistema
│   ├── Ingrediente.java   # Representa um ingrediente
│   ├── IngredientePremium.java # Ingrediente mais caro
│   ├── IngredientePromocional.java # Ingrediente com desconto
│   ├── Lanche.java        # Classe principal do lanche
│   └── Pizza.java         # Classe principal da pizza
│
├── 📂 factory/            # Padrão Factory Method
│   ├── IngredienteFactory.java # Interface das factories
│   ├── IngredienteFactoryPadrao.java # Cria ingredientes normais
│   ├── IngredienteFactoryPremium.java # Cria ingredientes premium
│   └── IngredienteFactoryPromocional.java # Cria ingredientes com desconto
│
├── 📂 validator/          # Sistema de validações
│   └── ValidadorCombinacoes.java # Regras de negócio
│
├── 📂 console/            # Interface do usuário
│   ├── MenuConsole.java   # Utilitários para menus
│   └── SistemaLanchonete.java # Sistema principal interativo
│
└── 📂 testes/             # Arquivos de teste
    ├── TesteBuilder.java     # Testa padrão Builder
    ├── TesteFactoryMethod.java # Testa padrão Factory
    └── TesteInterface.java   # Testa sistema completo
```

---

## 🏗️ **1. PADRÃO BUILDER - Como Construir Objetos Complexos**

### 📋 **O que é o Pattern Builder?**
Imagine que você quer montar um lanche. Em vez de ter que especificar tudo de uma vez, o Builder permite que você vá construindo **passo a passo**.

### 📍 **Arquivos Responsáveis:**
- `model/Lanche.java` - Classe principal do lanche
- `model/Pizza.java` - Classe principal da pizza

### 🔧 **Como Funciona:**

#### **Estrutura da Classe Lanche:**
```java
public class Lanche {
    // 🔒 CONSTRUTOR PRIVADO - só o Builder pode criar
    private Lanche(Builder builder) { ... }
    
    // 🏗️ CLASSE BUILDER INTERNA
    public static class Builder {
        // Métodos para configurar o lanche
        public Builder comTamanho(Tamanho tamanho) { ... }
        public Builder comTipoPao(TipoMassa tipoPao) { ... }
        public Builder adicionarIngrediente(Ingrediente ingrediente) { ... }
        
        // Método final que constrói o lanche
        public Lanche build() { ... }
    }
}
```

#### **Exemplo de Uso:**
```java
// ✨ CONSTRUÇÃO FLUENTE (métodos encadeados)
Lanche meuLanche = new Lanche.Builder()
    .comTamanho(Tamanho.GRANDE)              // 1º passo
    .comTipoPao(TipoMassa.PAO_INTEGRAL)      // 2º passo  
    .adicionarIngrediente(ingrediente1)      // 3º passo
    .adicionarIngrediente(ingrediente2)      // 4º passo
    .build();                                // FINALIZAR
```

### ✅ **Vantagens do Builder:**
- **Legível**: Fica claro o que cada passo faz
- **Flexível**: Pode adicionar ingredientes na ordem que quiser
- **Seguro**: Valida antes de criar o objeto
- **Fluente**: Métodos encadeados são fáceis de ler

---

## 🏭 **2. PADRÃO FACTORY METHOD - Como Criar Diferentes Tipos**

### 📋 **O que é o Pattern Factory Method?**
Imagine que você tem 3 fornecedores de ingredientes: **Normal**, **Premium** e **Promocional**. Cada um tem suas regras. O Factory Method decide qual fornecedor usar.

### 📍 **Arquivos Responsáveis:**
- `factory/IngredienteFactory.java` - Classe abstrata (modelo)
- `factory/IngredienteFactoryPadrao.java` - Cria ingredientes normais
- `factory/IngredienteFactoryPremium.java` - Cria ingredientes premium (+30% preço)
- `factory/IngredienteFactoryPromocional.java` - Cria ingredientes com desconto (-15%)

### 🔧 **Como Funciona:**

#### **Hierarquia das Classes:**
```java
// 🏭 CLASSE MÃE (abstrata)
abstract class IngredienteFactory {
    // Método que as filhas devem implementar
    public abstract Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade);
    
    // Método para escolher qual factory usar
    public static IngredienteFactory obterFactory(TipoFactory tipo) {
        switch (tipo) {
            case PADRAO: return new IngredienteFactoryPadrao();
            case PREMIUM: return new IngredienteFactoryPremium();
            case PROMOCIONAL: return new IngredienteFactoryPromocional();
        }
    }
}

// 🏭 FACTORIES FILHAS (implementações específicas)
class IngredienteFactoryPadrao extends IngredienteFactory {
    public Ingrediente criarIngrediente(...) {
        return new Ingrediente(tipo, quantidade); // Normal
    }
}

class IngredienteFactoryPremium extends IngredienteFactory {
    public Ingrediente criarIngrediente(...) {
        return new IngredientePremium(tipo, quantidade); // +30% preço
    }
}
```

#### **Exemplo de Uso:**
```java
// 1️⃣ Escolher qual factory usar
IngredienteFactory factory = IngredienteFactory.obterFactory(TipoFactory.PREMIUM);

// 2️⃣ Criar ingrediente usando a factory escolhida
Ingrediente bacon = factory.criarIngrediente(TipoIngrediente.BACON, 2);
// Resultado: "2x Bacon (Premium)" com preço 30% maior
```

### ✅ **Vantagens do Factory Method:**
- **Flexível**: Fácil trocar entre tipos (padrão/premium/promocional)
- **Extensível**: Pode adicionar novos tipos sem alterar código existente
- **Encapsulado**: Cliente não precisa saber como criar cada tipo

---

## 📊 **3. SISTEMA DE ENUMS - Definindo Opções Fixas**

### 📋 **O que são Enums?**
Enums são como **listas de opções fixas**. Em vez de usar strings que podem ter erros de digitação, usamos enums para garantir que só existam as opções válidas.

### 📍 **Arquivos e Suas Responsabilidades:**

#### **`enums/Tamanho.java`**
```java
public enum Tamanho {
    PEQUENO("Pequeno", 1.0),    // multiplicador de preço = 1x
    MEDIO("Médio", 1.5),        // multiplicador de preço = 1.5x
    GRANDE("Grande", 2.0);      // multiplicador de preço = 2x
}
```

#### **`enums/TipoMassa.java`**
```java
public enum TipoMassa {
    // Para pizzas
    FINA("Massa Fina", 0.0),           // sem custo extra
    GROSSA("Massa Grossa", 2.0),       // +R$ 2,00
    BORDA_RECHEADA("Borda Recheada", 5.0), // +R$ 5,00
    
    // Para lanches
    PAO_TRADICIONAL("Pão Tradicional", 0.0),
    PAO_INTEGRAL("Pão Integral", 1.5),
    // ... outros tipos
}
```

#### **`enums/TipoIngrediente.java`**
```java
public enum TipoIngrediente {
    // Proteínas
    FRANGO("Frango", 8.0, Categoria.PROTEINA),
    BACON("Bacon", 6.0, Categoria.PROTEINA),
    
    // Queijos  
    CHEDDAR("Cheddar", 5.0, Categoria.QUEIJO),
    MUSSARELA("Mussarela", 4.0, Categoria.QUEIJO),
    
    // ... outros ingredientes organizados por categoria
}
```

#### **`enums/SaborPizza.java`**
```java
public enum SaborPizza {
    CALABRESA("Calabresa", "Calabresa, cebola, mussarela", 
              Arrays.asList(TipoIngrediente.CALABRESA, 
                           TipoIngrediente.CEBOLA, 
                           TipoIngrediente.MUSSARELA)),
    
    MARGHERITA("Margherita", "Molho de tomate, mussarela, manjericão",
               Arrays.asList(TipoIngrediente.MUSSARELA)),
    // ... outros sabores com seus ingredientes
}
```

---

## 🛡️ **4. SISTEMA DE VALIDAÇÕES**

### 📋 **O que são Validações?**
São **regras de negócio** que impedem configurações inválidas. Por exemplo: "Pizza pequena não pode ter borda recheada".

### 📍 **Arquivo Responsável:**
- `validator/ValidadorCombinacoes.java`

### 🔧 **Tipos de Validações Implementadas:**

#### **1. Validações de Combinação:**
```java
// ❌ Combinações proibidas
private static final Set<Set<TipoIngrediente>> COMBINACOES_PROIBIDAS;

// Exemplo: Gorgonzola + Cheddar não podem estar juntos
Set<TipoIngrediente> combinacao1 = new HashSet<>();
combinacao1.add(TipoIngrediente.GORGONZOLA);
combinacao1.add(TipoIngrediente.CHEDDAR);
COMBINACOES_PROIBIDAS.add(combinacao1);
```

#### **2. Validações de Quantidade:**
```java
// Máximo 3 proteínas por produto
private static final int MAX_PROTEINAS_POR_PRODUTO = 3;

// Máximo 15 ingredientes totais
if (totalQuantidade > 15) {
    throw new IllegalStateException("Muitos ingredientes!");
}
```

#### **3. Validações Específicas:**
```java
// Para Pizza
public static void validarPizza(Tamanho tamanho, TipoMassa massa, List<Ingrediente> ingredientes) {
    // Borda recheada só para pizza média/grande
    if (massa == BORDA_RECHEADA && tamanho == PEQUENO) {
        throw new IllegalStateException("Borda recheada não disponível para pizza pequena");
    }
    
    // Pizza deve ter pelo menos uma proteína
    boolean temProteina = ingredientes.stream()
        .anyMatch(ing -> ing.getTipo().getCategoria() == PROTEINA);
    if (!temProteina) {
        throw new IllegalStateException("Pizza deve ter pelo menos uma proteína");
    }
}
```

---

## 🖥️ **5. INTERFACE DE CONSOLE**

### 📋 **O que é a Interface de Console?**
É o **menu interativo** onde o usuário interage com o sistema através do terminal/console.

### 📍 **Arquivos Responsáveis:**
- `console/MenuConsole.java` - Utilitários para menus
- `console/SistemaLanchonete.java` - Sistema principal

### 🔧 **Como Funciona:**

#### **`MenuConsole.java` - Utilidades:**
```java
public class MenuConsole {
    // Exibir menu com opções numeradas
    public static int exibirMenuOpcoes(String titulo, String[] opcoes) { ... }
    
    // Ler uma opção válida do usuário
    public static int lerOpcao(int min, int max) { ... }
    
    // Perguntar sim/não
    public static boolean perguntarContinuar(String pergunta) { ... }
    
    // Ler quantidade com validação
    public static int lerQuantidade() { ... }
}
```

#### **`SistemaLanchonete.java` - Fluxo Principal:**
```java
public class SistemaLanchonete {
    public static void main(String[] args) {
        while (true) {
            // 1. Mostrar menu principal
            String[] opcoes = {"Montar Lanche", "Montar Pizza", "Ver Pedidos", "Sair"};
            int opcao = MenuConsole.exibirMenuOpcoes("MENU PRINCIPAL", opcoes);
            
            // 2. Executar ação escolhida
            switch (opcao) {
                case 1: montarLanche(); break;
                case 2: montarPizza(); break;
                case 3: exibirPedidos(); break;
                case 4: return; // Sair
            }
        }
    }
    
    private static void montarPizza() {
        // 1. Escolher tipo de ingredientes (Factory Method)
        // 2. Escolher tamanho
        // 3. Escolher massa
        // 4. Escolher sabor (ingredientes automáticos)
        // 5. Adicionar extras (opcional)
        // 6. Construir com Builder
        // 7. Confirmar pedido
    }
}
```

---

## 💰 **6. SISTEMA DE CÁLCULO DE PREÇOS**

### 📋 **Como o Preço é Calculado?**

```java
public double calcularPrecoTotal() {
    // 1. Somar preços de todos os ingredientes
    double precoIngredientes = ingredientes.stream()
        .mapToDouble(Ingrediente::getPrecoTotal)
        .sum();
    
    // 2. Fórmula final
    double precoTotal = (precoBase + precoIngredientes + massaPao.getPrecoAdicional()) 
                       * tamanho.getMultiplicadorPreco();
    
    // 3. Arredondar para 2 casas decimais
    return Math.round(precoTotal * 100.0) / 100.0;
}
```

### 💡 **Exemplo Prático:**
```
Pizza Grande Premium com Borda Recheada:
- Preço base pizza: R$ 20,00
- Borda recheada: +R$ 5,00
- Frango Premium: R$ 8,00 × 1,3 = R$ 10,40
- Bacon Premium: R$ 6,00 × 1,3 = R$ 7,80

Subtotal: R$ 20,00 + R$ 5,00 + R$ 10,40 + R$ 7,80 = R$ 43,20
Multiplicador Grande (2x): R$ 43,20 × 2 = R$ 86,40
```

---

## 🧪 **7. SISTEMA DE TESTES**

### 📍 **Arquivos de Teste:**

#### **`TesteBuilder.java`**
```java
// Testa se o padrão Builder funciona corretamente
- Construção fluente de lanches e pizzas
- Validações integradas ao Builder
- Integração com sistema de sabores
```

#### **`TesteFactoryMethod.java`**
```java
// Testa se o padrão Factory Method funciona
- Factory Padrão (preços normais)
- Factory Premium (+30% preço)
- Factory Promocional (-15% preço)
- Validações específicas de cada factory
- Integração Factory + Builder
```

#### **`TesteInterface.java`**
```java
// Testa o sistema completo
- Sistema de sabores de pizza
- Clareza nas quantidades (explicação em gramas)
- Fluxo completo do sistema
- Cálculo de preços
- Todas as validações integradas
- Instruções de uso
```

---

## 🚀 **8. COMO EXECUTAR O SISTEMA**

### 📦 **1. Compilar o Projeto:**
```bash
javac -d out -cp src src/com/lanchonete/enums/*.java \
                     src/com/lanchonete/model/*.java \
                     src/com/lanchonete/factory/*.java \
                     src/com/lanchonete/validator/*.java \
                     src/com/lanchonete/console/*.java \
                     src/com/lanchonete/*.java
```

### 🎮 **2. Executar Sistema Interativo:**
```bash
java -cp out com.lanchonete.console.SistemaLanchonete
```

### 🧪 **3. Executar Testes:**
```bash
java -cp out com.lanchonete.TesteBuilder
java -cp out com.lanchonete.TesteFactoryMethod
java -cp out com.lanchonete.TesteInterface
```

---

## 🎯 **RESUMO FINAL**

### **O que o Sistema Faz:**
1. **Permite montar lanches e pizzas** de forma interativa
2. **Escolher entre 3 tipos de ingredientes** (padrão/premium/promocional)
3. **Sabores pré-definidos para pizzas** (Calabresa, Margherita, etc.)
4. **Validações inteligentes** que impedem configurações inválidas
5. **Cálculo automático de preços** baseado em tamanho, ingredientes e tipo
6. **Histórico de pedidos** com valor total

### **Padrões de Projeto Implementados:**
- ✅ **Builder** - Construção fluente e segura de objetos complexos
- ✅ **Factory Method** - Criação de diferentes tipos de ingredientes
- ✅ **Strategy** - Diferentes estratégias de validação

### **Por que Usar Esses Padrões?**
- **Builder**: Evita construtores gigantes com dezenas de parâmetros
- **Factory Method**: Facilita adicionar novos tipos sem alterar código existente
- **Strategy**: Validações organizadas e reutilizáveis

**Este sistema demonstra como padrões de projeto tornam o código mais organizado, flexível e fácil de manter! 🏆** 