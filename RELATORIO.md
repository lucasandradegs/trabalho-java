# 📊 Relatório Técnico - Sistema de Pedidos de Lanchonete

## 📋 **1. CONTEXTO DO PROJETO**

### **1.1 Problema a Resolver**

O projeto visa implementar um **Sistema de Pedidos para uma Lanchonete/Pizzaria** que permita aos clientes montarem seus pedidos de forma personalizada e flexível. O sistema precisa lidar com:

- **Produtos complexos** com múltiplas opções de configuração (tamanho, tipo de massa/pão, ingredientes)
- **Diferentes categorias de ingredientes** com preços e regras distintas
- **Validações de negócio** para evitar combinações inválidas
- **Cálculo dinâmico de preços** baseado em múltiplos fatores
- **Interface interativa** para facilitar a montagem dos pedidos

### **1.2 Desafios de Design**

1. **Construção de objetos complexos**: Lanches e pizzas têm muitas opções configuráveis (tamanho, massa, múltiplos ingredientes)
2. **Variação de tipos**: Ingredientes podem ser padrão, premium ou promocionais, cada um com regras próprias
3. **Validações complexas**: Regras de negócio que dependem de múltiplas condições
4. **Flexibilidade**: Sistema deve ser fácil de estender com novos tipos e sabores

---

## 🏗️ **2. PADRÕES DE PROJETO APLICADOS**

### **2.1 PADRÃO BUILDER**

#### **Por que foi escolhido?**

Aplicamos o padrão **Builder** na construção de `Lanche` e `Pizza` porque esses objetos possuem:
- Múltiplos atributos opcionais (tamanho, tipo de massa, lista de ingredientes)
- Necessidade de validação antes da criação
- Construção passo a passo para melhor legibilidade

**Sem o Builder**, teríamos construtores como:
```java
// ❌ Problema: Construtor com muitos parâmetros
public Lanche(Tamanho tamanho, TipoMassa pao, Ingrediente ing1, 
              Ingrediente ing2, Ingrediente ing3, Ingrediente ing4, 
              Ingrediente ing5, double precoBase) { ... }
```

**Com o Builder**, temos:
```java
// ✅ Solução: Construção fluente e legível
Lanche lanche = new Lanche.Builder()
    .comTamanho(Tamanho.GRANDE)
    .comTipoPao(TipoMassa.PAO_INTEGRAL)
    .adicionarIngrediente(ingrediente1)
    .adicionarIngrediente(ingrediente2)
    .build();
```

#### **Implementação no Projeto:**

```java
public class Lanche {
    // Atributos finais (imutáveis após construção)
    private final Tamanho tamanho;
    private final TipoMassa tipoPao;
    private final List<Ingrediente> ingredientes;
    
    // Construtor PRIVADO - só o Builder pode acessar
    private Lanche(Builder builder) {
        this.tamanho = builder.tamanho;
        this.tipoPao = builder.tipoPao;
        this.ingredientes = new ArrayList<>(builder.ingredientes);
    }
    
    // Builder interno estático
    public static class Builder {
        private Tamanho tamanho = Tamanho.MEDIO; // valor padrão
        private TipoMassa tipoPao = TipoMassa.PAO_TRADICIONAL;
        private List<Ingrediente> ingredientes = new ArrayList<>();
        
        // Métodos fluentes retornam 'this' para encadeamento
        public Builder comTamanho(Tamanho tamanho) {
            this.tamanho = tamanho;
            return this;
        }
        
        public Builder comTipoPao(TipoMassa tipoPao) {
            this.tipoPao = tipoPao;
            return this;
        }
        
        public Builder adicionarIngrediente(Ingrediente ingrediente) {
            this.ingredientes.add(ingrediente);
            return this;
        }
        
        // Método build() valida e constrói o objeto final
        public Lanche build() {
            validarConfiguracao();
            return new Lanche(this);
        }
        
        private void validarConfiguracao() {
            // Validações antes de criar o objeto
            ValidadorCombinacoes.validarLanche(tamanho, tipoPao, ingredientes);
        }
    }
}
```

#### **Benefícios Obtidos:**
- ✅ **Legibilidade**: Código auto-explicativo
- ✅ **Flexibilidade**: Fácil adicionar novos atributos opcionais
- ✅ **Imutabilidade**: Objetos finais são imutáveis (thread-safe)
- ✅ **Validação centralizada**: Todas as validações no método `build()`

---

### **2.2 PADRÃO FACTORY METHOD**

#### **Por que foi escolhido?**

Aplicamos o padrão **Factory Method** para criar ingredientes porque precisávamos de **diferentes estratégias de criação** sem que o código cliente precisasse conhecer os detalhes de implementação:

- **Ingredientes Padrão**: Preço normal
- **Ingredientes Premium**: Preço 30% maior, com restrições (sem ketchup/mostarda)
- **Ingredientes Promocionais**: Preço 15% menor, mínimo 2 unidades

**Problema sem Factory Method:**
```java
// ❌ Cliente precisa saber qual classe instanciar
if (tipoPremium) {
    ingrediente = new IngredientePremium(tipo, quantidade);
} else if (tipoPromocional) {
    ingrediente = new IngredientePromocional(tipo, quantidade, desconto);
} else {
    ingrediente = new Ingrediente(tipo, quantidade);
}
```

**Solução com Factory Method:**
```java
// ✅ Cliente só escolhe o tipo de factory
IngredienteFactory factory = IngredienteFactory.obterFactory(TipoFactory.PREMIUM);
Ingrediente ingrediente = factory.criarIngrediente(TipoIngrediente.BACON, 2);
// Resultado: Ingrediente premium criado automaticamente
```

#### **Implementação no Projeto:**

```java
// Classe abstrata (Factory Method)
public abstract class IngredienteFactory {
    
    // Método factory abstrato - cada subclasse implementa
    public abstract Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade);
    
    // Factory Method estático para obter a factory apropriada
    public static IngredienteFactory obterFactory(TipoFactory tipoFactory) {
        switch (tipoFactory) {
            case PADRAO:
                return new IngredienteFactoryPadrao();
            case PREMIUM:
                return new IngredienteFactoryPremium();
            case PROMOCIONAL:
                return new IngredienteFactoryPromocional();
            default:
                throw new IllegalArgumentException("Tipo não reconhecido");
        }
    }
}

// Factory Concreta 1: Ingredientes Padrão
public class IngredienteFactoryPadrao extends IngredienteFactory {
    @Override
    public Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade) {
        return new Ingrediente(tipo, quantidade);
    }
}

// Factory Concreta 2: Ingredientes Premium
public class IngredienteFactoryPremium extends IngredienteFactory {
    @Override
    public Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade) {
        validarIngredientePremium(tipo); // Validação específica
        return new IngredientePremium(tipo, quantidade); // +30% preço
    }
    
    private void validarIngredientePremium(TipoIngrediente tipo) {
        if (tipo == TipoIngrediente.KETCHUP || tipo == TipoIngrediente.MOSTARDA) {
            throw new IllegalArgumentException("Não disponível na linha premium");
        }
    }
}

// Factory Concreta 3: Ingredientes Promocionais
public class IngredienteFactoryPromocional extends IngredienteFactory {
    @Override
    public Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade) {
        if (quantidade < 2) {
            throw new IllegalArgumentException("Mínimo 2 unidades para promoção");
        }
        return new IngredientePromocional(tipo, quantidade, 0.15); // -15%
    }
}
```

#### **Hierarquia de Classes:**
```
IngredienteFactory (abstrata)
├── IngredienteFactoryPadrao → cria Ingrediente
├── IngredienteFactoryPremium → cria IngredientePremium (+30%)
└── IngredienteFactoryPromocional → cria IngredientePromocional (-15%)

Ingrediente (classe base)
├── IngredientePremium (herda e sobrescreve getPrecoTotal())
└── IngredientePromocional (herda e sobrescreve getPrecoTotal())
```

#### **Benefícios Obtidos:**
- ✅ **Desacoplamento**: Cliente não conhece classes concretas
- ✅ **Extensibilidade**: Fácil adicionar novos tipos (ex: IngredienteVIP)
- ✅ **Polimorfismo**: Todas as factories implementam a mesma interface
- ✅ **Encapsulamento**: Lógica de criação isolada nas factories

---

### **2.3 PADRÃO STRATEGY (Implícito nas Validações)**

#### **Por que foi escolhido?**

Aplicamos o padrão **Strategy** implicitamente no `ValidadorCombinacoes` para permitir **diferentes estratégias de validação** sem alterar o código do Builder:

- **Validações de Lanche**: Regras específicas para lanches
- **Validações de Pizza**: Regras específicas para pizzas
- **Validações Gerais**: Combinações proibidas, limites de quantidade

**Problema sem Strategy:**
```java
// ❌ Builder com toda lógica de validação embutida
public Lanche build() {
    if (tamanho == PEQUENO && tipoPao == CIABATTA) throw new Exception();
    if (ingredientes.size() > 15) throw new Exception();
    if (temGorgonzola && temCheddar) throw new Exception();
    // ... dezenas de validações misturadas
    return new Lanche(this);
}
```

**Solução com Strategy:**
```java
// ✅ Builder delega validações para estratégia específica
public Lanche build() {
    ValidadorCombinacoes.validarLanche(tamanho, tipoPao, ingredientes);
    return new Lanche(this);
}

public Pizza build() {
    ValidadorCombinacoes.validarPizza(tamanho, tipoMassa, ingredientes);
    return new Pizza(this);
}
```

#### **Implementação no Projeto:**

```java
public class ValidadorCombinacoes {
    
    // Estratégia geral: validações comuns
    public static void validarCombinacao(List<Ingrediente> ingredientes) {
        validarCombinacaoProibida(ingredientes);
        validarLimitePorCategoria(ingredientes);
        validarQuantidadesTotais(ingredientes);
    }
    
    // Estratégia específica para LANCHE
    public static void validarLanche(Tamanho tamanho, TipoMassa tipoPao, 
                                     List<Ingrediente> ingredientes) {
        validarCombinacao(ingredientes); // Validações gerais
        
        // Validações específicas de lanche
        if (tipoPao == TipoMassa.CIABATTA && tamanho == Tamanho.PEQUENO) {
            throw new IllegalStateException("Ciabatta não disponível para lanche pequeno");
        }
        
        if (tamanho == Tamanho.PEQUENO && ingredientes.size() > 4) {
            throw new IllegalStateException("Lanche pequeno: máximo 4 ingredientes");
        }
    }
    
    // Estratégia específica para PIZZA
    public static void validarPizza(Tamanho tamanho, TipoMassa tipoMassa, 
                                    List<Ingrediente> ingredientes) {
        validarCombinacao(ingredientes); // Validações gerais
        
        // Validações específicas de pizza
        if (tipoMassa == TipoMassa.BORDA_RECHEADA && tamanho == Tamanho.PEQUENO) {
            throw new IllegalStateException("Borda recheada: mínimo pizza média");
        }
        
        boolean temProteina = ingredientes.stream()
            .anyMatch(ing -> ing.getTipo().getCategoria() == PROTEINA);
        if (!temProteina && !ingredientes.isEmpty()) {
            throw new IllegalStateException("Pizza deve ter pelo menos uma proteína");
        }
    }
}
```

#### **Benefícios Obtidos:**
- ✅ **Separação de responsabilidades**: Validações isoladas do Builder
- ✅ **Reutilização**: Validações gerais compartilhadas
- ✅ **Manutenibilidade**: Fácil adicionar/modificar regras
- ✅ **Testabilidade**: Validações podem ser testadas independentemente

---

## 📐 **3. DIAGRAMAS UML**

### **3.1 Diagrama de Classes - Padrão Builder**

```
┌─────────────────────────────────────────────────────────────┐
│                          <<Product>>                         │
│                           Lanche                             │
├─────────────────────────────────────────────────────────────┤
│ - tamanho: Tamanho                                          │
│ - tipoPao: TipoMassa                                        │
│ - ingredientes: List<Ingrediente>                           │
│ - precoBase: double                                         │
├─────────────────────────────────────────────────────────────┤
│ - Lanche(builder: Builder)          [construtor privado]   │
│ + calcularPrecoTotal(): double                              │
│ + toString(): String                                        │
└─────────────────────────────────────────────────────────────┘
                            △
                            │ constrói
                            │
┌─────────────────────────────────────────────────────────────┐
│                    <<Builder>>                               │
│                   Lanche.Builder                             │
├─────────────────────────────────────────────────────────────┤
│ - tamanho: Tamanho                                          │
│ - tipoPao: TipoMassa                                        │
│ - ingredientes: List<Ingrediente>                           │
│ - precoBase: double                                         │
├─────────────────────────────────────────────────────────────┤
│ + comTamanho(tamanho: Tamanho): Builder                     │
│ + comTipoPao(tipoPao: TipoMassa): Builder                   │
│ + adicionarIngrediente(ingrediente: Ingrediente): Builder   │
│ + comPrecoBase(preco: double): Builder                      │
│ + build(): Lanche                                           │
│ - validarConfiguracao(): void                               │
└─────────────────────────────────────────────────────────────┘
```

### **3.2 Diagrama de Classes - Padrão Factory Method**

```
                    ┌────────────────────────────────┐
                    │   <<abstract>>                 │
                    │   IngredienteFactory           │
                    ├────────────────────────────────┤
                    │ + criarIngrediente(tipo, qtd)  │
                    │   : Ingrediente [abstract]     │
                    │ + obterFactory(tipo)           │
                    │   : IngredienteFactory [static]│
                    └────────────────────────────────┘
                                  △
                 ┌────────────────┼────────────────┐
                 │                │                │
    ┌────────────────────┐ ┌──────────────────┐ ┌─────────────────────────┐
    │FactoryPadrao       │ │FactoryPremium    │ │FactoryPromocional       │
    ├────────────────────┤ ├──────────────────┤ ├─────────────────────────┤
    │+criarIngrediente() │ │+criarIngrediente()│ │+criarIngrediente()      │
    │ :Ingrediente       │ │ :IngredientePremium│ │:IngredientePromocional │
    └────────────────────┘ └──────────────────┘ └─────────────────────────┘
              │                      │                        │
              │ cria                 │ cria                   │ cria
              ▼                      ▼                        ▼
    ┌────────────────────┐ ┌──────────────────┐ ┌─────────────────────────┐
    │  Ingrediente       │ │IngredientePremium│ │IngredientePromocional   │
    ├────────────────────┤ ├──────────────────┤ ├─────────────────────────┤
    │-tipo: TipoIngred.  │ │(herda Ingrediente)│ │(herda Ingrediente)      │
    │-quantidade: int    │ │+getPrecoTotal()  │ │-desconto: double        │
    ├────────────────────┤ │ :double          │ │+getPrecoTotal(): double │
    │+getPrecoTotal()    │ │  [+30% preço]    │ │  [-15% preço]           │
    │ :double            │ └──────────────────┘ └─────────────────────────┘
    └────────────────────┘
```

### **3.3 Diagrama de Sequência - Construção de Pizza com Builder e Factory**

```
Cliente          Builder          Factory          Validador         Pizza
  │                │                 │                 │               │
  │─new Builder()─>│                 │                 │               │
  │                │                 │                 │               │
  │─comTamanho()──>│                 │                 │               │
  │<───return this─│                 │                 │               │
  │                │                 │                 │               │
  │─comTipoMassa()>│                 │                 │               │
  │<───return this─│                 │                 │               │
  │                │                 │                 │               │
  │─────────────────────obterFactory(PREMIUM)────────>│               │
  │<──────────────────FactoryPremium─────────────────│               │
  │                │                 │                 │               │
  │─criarIngrediente(FRANGO, 1)────>│                 │               │
  │                │                 │─validar()       │               │
  │                │                 │─new Premium()   │               │
  │<──────────IngredientePremium────│                 │               │
  │                │                 │                 │               │
  │─addIngrediente(ingrediente)────>│                 │               │
  │<───return this─│                 │                 │               │
  │                │                 │                 │               │
  │─────build()───>│                 │                 │               │
  │                │─────────────validarPizza()──────>│               │
  │                │<────────────ok/exception─────────│               │
  │                │─────────────────────────new Pizza()─────────────>│
  │<───────────────────────────────Pizza─────────────────────────────│
```

### **3.4 Diagrama de Classes - Sistema Completo (Visão Geral)**

```
┌──────────────┐      usa      ┌──────────────────┐
│SistemaLanch. │──────────────>│  MenuConsole     │
│  (Main)      │               │  (Utilities)     │
└──────────────┘               └──────────────────┘
       │
       │ cria
       ▼
┌──────────────────────────────────────────────────────────┐
│                    Builder Pattern                        │
│  ┌────────────┐              ┌────────────┐              │
│  │  Lanche    │              │   Pizza    │              │
│  │  .Builder  │              │  .Builder  │              │
│  └────────────┘              └────────────┘              │
└──────────────────────────────────────────────────────────┘
       │                              │
       │ usa                          │ usa
       ▼                              ▼
┌──────────────────────────────────────────────────────────┐
│              Factory Method Pattern                       │
│  ┌──────────────────────────────────────────────┐        │
│  │        IngredienteFactory (abstract)         │        │
│  └──────────────────────────────────────────────┘        │
│         △              △              △                   │
│         │              │              │                   │
│  ┌──────────┐   ┌──────────┐   ┌──────────────┐        │
│  │ Padrão   │   │ Premium  │   │ Promocional  │        │
│  └──────────┘   └──────────┘   └──────────────┘        │
└──────────────────────────────────────────────────────────┘
       │
       │ usa
       ▼
┌──────────────────────────────────────────────────────────┐
│              Strategy Pattern (Validações)                │
│  ┌──────────────────────────────────────────────┐        │
│  │        ValidadorCombinacoes                  │        │
│  │  + validarLanche()                           │        │
│  │  + validarPizza()                            │        │
│  │  + validarCombinacao()                       │        │
│  └──────────────────────────────────────────────┘        │
└──────────────────────────────────────────────────────────┘
       │
       │ valida
       ▼
┌──────────────────────────────────────────────────────────┐
│                    Enums (Dados)                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────────┐          │
│  │ Tamanho  │  │TipoMassa │  │TipoIngrediente│          │
│  └──────────┘  └──────────┘  └──────────────┘          │
│                 ┌──────────────┐                         │
│                 │ SaborPizza   │                         │
│                 └──────────────┘                         │
└──────────────────────────────────────────────────────────┘
```

---

## 🎯 **4. JUSTIFICATIVA DOS PADRÕES**

### **4.1 Por que Builder?**

**Problema:** Lanches e pizzas têm muitas configurações opcionais. Usar construtores tradicionais resultaria em:
- Múltiplos construtores sobrecarregados
- Parâmetros em ordem fixa e confusa
- Dificuldade para adicionar novas opções

**Solução:** Builder permite:
- Construção passo a passo clara e legível
- Validação centralizada antes da criação
- Objetos imutáveis após construção
- Fácil extensão com novos atributos

### **4.2 Por que Factory Method?**

**Problema:** Precisávamos criar ingredientes com comportamentos diferentes (preços, validações) sem que o cliente conhecesse as classes concretas.

**Solução:** Factory Method permite:
- Desacoplar criação de uso
- Adicionar novos tipos facilmente (Open/Closed Principle)
- Encapsular lógica de criação complexa
- Polimorfismo na criação de objetos

### **4.3 Por que Strategy (Validações)?**

**Problema:** Diferentes produtos (lanche/pizza) têm regras de validação diferentes, e misturar tudo no Builder tornaria o código confuso.

**Solução:** Strategy permite:
- Separar lógica de validação da construção
- Reutilizar validações comuns
- Adicionar novas regras sem modificar o Builder
- Testar validações independentemente

---

## 📈 **5. RESULTADOS OBTIDOS**

### **5.1 Métricas de Qualidade**

| Métrica | Resultado |
|---------|-----------|
| **Linhas de código** | ~2.500 linhas |
| **Classes criadas** | 20 classes |
| **Padrões implementados** | 3 padrões GoF |
| **Cobertura de testes** | 3 suítes de teste completas |
| **Validações** | 11 tipos de validações |

### **5.2 Benefícios Alcançados**

✅ **Manutenibilidade**: Código organizado em pacotes lógicos  
✅ **Extensibilidade**: Fácil adicionar novos sabores, ingredientes ou tipos  
✅ **Testabilidade**: Cada componente pode ser testado isoladamente  
✅ **Legibilidade**: Código auto-explicativo com padrões bem aplicados  
✅ **Robustez**: Sistema de validações previne estados inválidos  

---

## 🏆 **6. CONCLUSÃO**

O projeto demonstra com sucesso a aplicação de **padrões de projeto GoF** para resolver problemas reais de design:

1. **Builder** resolve o problema de construção de objetos complexos
2. **Factory Method** resolve o problema de criação de famílias de objetos relacionados
3. **Strategy** resolve o problema de múltiplos algoritmos intercambiáveis

A combinação desses padrões resultou em um sistema:
- **Flexível**: Fácil adicionar novos recursos
- **Robusto**: Validações impedem estados inválidos
- **Manutenível**: Código organizado e desacoplado
- **Profissional**: Segue boas práticas de engenharia de software

O sistema está **completo e pronto para uso**, atendendo todos os requisitos solicitados e demonstrando domínio dos conceitos de padrões de projeto.

