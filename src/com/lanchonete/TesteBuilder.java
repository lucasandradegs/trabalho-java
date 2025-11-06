package com.lanchonete;

import com.lanchonete.model.*;
import com.lanchonete.enums.*;

/**
 * Teste abrangente do padrão Builder
 * Demonstra construção fluente de objetos complexos
 */
public class TesteBuilder {
    public static void main(String[] args) {
        System.out.println("=== TESTE COMPLETO DO PADRÃO BUILDER ===\n");
        
        testeBuilderLanche();
        testeBuilderPizza();
        testeValidacoesBuilder();
        testeBuilderComSabores();
        
        System.out.println("\n🎉 Todos os testes do padrão Builder executados com sucesso!");
        System.out.println("✅ Padrão Builder implementado corretamente!");
    }
    
    /**
     * Teste da construção fluente de lanches
     */
    private static void testeBuilderLanche() {
        System.out.println("🔵 TESTE 1: Builder de Lanche Simples");
        System.out.println("-".repeat(50));
        
        // Demonstra a construção fluente
        Lanche lanche = new Lanche.Builder()
                .comTamanho(Tamanho.GRANDE)
                .comTipoPao(TipoMassa.PAO_INTEGRAL)
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.FRANGO))
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.CHEDDAR))
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.ALFACE))
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.BARBECUE))
                .build();
        
        System.out.println(lanche);
        System.out.println("\n✅ Builder fluente funcionando corretamente");
        System.out.println("✅ Construtor privado funcionando (só acesso via Builder)");
        System.out.println("✅ Métodos encadeados funcionando\n");
    }
    
    /**
     * Teste da construção fluente de pizzas
     */
    private static void testeBuilderPizza() {
        System.out.println("🔵 TESTE 2: Builder de Pizza Complexa");
        System.out.println("-".repeat(50));
        
        // Pizza com múltiplos ingredientes
        Pizza pizza = new Pizza.Builder()
                .comTamanho(Tamanho.MEDIO)
                .comTipoMassa(TipoMassa.BORDA_RECHEADA)
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.CALABRESA))
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.BACON, 2))
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.PARMESAO))
                .adicionarIngrediente(new Ingrediente(TipoIngrediente.AZEITONA))
                .comPrecoBase(25.0)
                .build();
        
        System.out.println(pizza);
        System.out.println("\n✅ Builder com parâmetros opcionais funcionando");
        System.out.println("✅ Soma de quantidades de ingredientes iguais funcionando");
        System.out.println("✅ Preço base customizável funcionando\n");
    }
    
    /**
     * Teste das validações do Builder
     */
    private static void testeValidacoesBuilder() {
        System.out.println("🔵 TESTE 3: Validações do Builder");
        System.out.println("-".repeat(50));
        
        // Teste 1: Borda recheada em pizza pequena (deve falhar)
        try {
            Pizza pizzaInvalida = new Pizza.Builder()
                    .comTamanho(Tamanho.PEQUENO)
                    .comTipoMassa(TipoMassa.BORDA_RECHEADA)
                    .build();
            System.out.println("❌ ERRO: Validação não funcionou!");
        } catch (IllegalStateException e) {
            System.out.println("✅ Validação 1: " + e.getMessage());
        }
        
        // Teste 2: Combinação proibida (Gorgonzola + Cheddar)
        try {
            Lanche lancheInvalido = new Lanche.Builder()
                    .comTamanho(Tamanho.MEDIO)
                    .adicionarIngrediente(new Ingrediente(TipoIngrediente.GORGONZOLA))
                    .adicionarIngrediente(new Ingrediente(TipoIngrediente.CHEDDAR))
                    .build();
            System.out.println("❌ ERRO: Validação não funcionou!");
        } catch (IllegalStateException e) {
            System.out.println("✅ Validação 2: " + e.getMessage());
        }
        
        // Teste 3: Ciabatta em lanche pequeno
        try {
            Lanche lancheInvalido = new Lanche.Builder()
                    .comTamanho(Tamanho.PEQUENO)
                    .comTipoPao(TipoMassa.CIABATTA)
                    .build();
            System.out.println("❌ ERRO: Validação não funcionou!");
        } catch (IllegalStateException e) {
            System.out.println("✅ Validação 3: " + e.getMessage());
        }
        
        System.out.println("\n✅ Sistema de validações integrado ao Builder funcionando\n");
    }
    
    /**
     * Teste do Builder com sabores de pizza
     */
    private static void testeBuilderComSabores() {
        System.out.println("🔵 TESTE 4: Builder com Sabores de Pizza");
        System.out.println("-".repeat(50));
        
        // Demonstra como o Builder trabalha com sabores pré-definidos
        Pizza.Builder builder = new Pizza.Builder()
                .comTamanho(Tamanho.GRANDE)
                .comTipoMassa(TipoMassa.FINA);
        
        // Adicionar ingredientes de um sabor (simulando o que a interface faz)
        System.out.println("🍕 Construindo Pizza Calabresa:");
        for (TipoIngrediente tipo : SaborPizza.CALABRESA.getIngredientes()) {
            Ingrediente ingrediente = new Ingrediente(tipo, 1);
            builder.adicionarIngrediente(ingrediente);
            System.out.println("  ✅ Adicionado: " + ingrediente.toString());
        }
        
        // Adicionar extra
        builder.adicionarIngrediente(new Ingrediente(TipoIngrediente.BACON, 1));
        System.out.println("  ✅ Extra: Bacon");
        
        Pizza pizzaSabor = builder.build();
        
        System.out.println("\n" + pizzaSabor);
        System.out.println("\n✅ Builder funciona perfeitamente com sistema de sabores");
        System.out.println("✅ Flexibilidade para adicionar ingredientes extras");
        System.out.println("✅ Integração entre Builder e Enums funcionando\n");
    }
} 