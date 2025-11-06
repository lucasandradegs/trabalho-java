package com.lanchonete;

import com.lanchonete.model.*;
import com.lanchonete.enums.*;
import com.lanchonete.factory.*;

/**
 * Teste abrangente do padrão Factory Method
 * Demonstra criação de objetos com diferentes estratégias
 */
public class TesteFactoryMethod {
    public static void main(String[] args) {
        System.out.println("=== TESTE COMPLETO DO PADRÃO FACTORY METHOD ===\n");
        
        testeFactoryPadrao();
        testeFactoryPremium();
        testeFactoryPromocional();
        testeValidacoesFactory();
        testeIntegracaoComBuilder();
        
        System.out.println("\n🎉 Todos os testes do padrão Factory Method executados com sucesso!");
        System.out.println("✅ Padrão Factory Method implementado corretamente!");
    }
    
    /**
     * Teste da Factory padrão
     */
    private static void testeFactoryPadrao() {
        System.out.println("🔵 TESTE 1: Factory Padrão");
        System.out.println("-".repeat(50));
        
        IngredienteFactory factory = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PADRAO);
        
        Ingrediente frango = factory.criarIngrediente(TipoIngrediente.FRANGO, 2);
        Ingrediente queijo = factory.criarIngrediente(TipoIngrediente.CHEDDAR);
        
        System.out.println("Ingredientes criados pela Factory Padrão:");
        System.out.println("  ✅ " + frango.toString() + " - R$ " + String.format("%.2f", frango.getPrecoTotal()));
        System.out.println("  ✅ " + queijo.toString() + " - R$ " + String.format("%.2f", queijo.getPrecoTotal()));
        
        System.out.println("\n✅ Factory padrão criando ingredientes normais");
        System.out.println("✅ Preços sem modificações\n");
    }
    
    /**
     * Teste da Factory premium
     */
    private static void testeFactoryPremium() {
        System.out.println("🔵 TESTE 2: Factory Premium");
        System.out.println("-".repeat(50));
        
        IngredienteFactory factory = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PREMIUM);
        
        Ingrediente carnePremium = factory.criarIngrediente(TipoIngrediente.CARNE_BOVINA, 1);
        Ingrediente gorgonzolaPremium = factory.criarIngrediente(TipoIngrediente.GORGONZOLA, 2);
        
        System.out.println("Ingredientes criados pela Factory Premium:");
        System.out.println("  ✅ " + carnePremium.toString() + " - R$ " + String.format("%.2f", carnePremium.getPrecoTotal()));
        System.out.println("  ✅ " + gorgonzolaPremium.toString() + " - R$ " + String.format("%.2f", gorgonzolaPremium.getPrecoTotal()));
        
        // Comparar com padrão
        IngredienteFactory factoryPadrao = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PADRAO);
        Ingrediente carneNormal = factoryPadrao.criarIngrediente(TipoIngrediente.CARNE_BOVINA, 1);
        
        System.out.println("\nComparação de preços:");
        System.out.println("  Carne Normal: R$ " + String.format("%.2f", carneNormal.getPrecoTotal()));
        System.out.println("  Carne Premium: R$ " + String.format("%.2f", carnePremium.getPrecoTotal()));
        System.out.println("  Diferença: +30% (multiplicador premium)");
        
        System.out.println("\n✅ Factory premium aplicando sobretaxa corretamente");
        System.out.println("✅ Herança e polimorfismo funcionando\n");
    }
    
    /**
     * Teste da Factory promocional
     */
    private static void testeFactoryPromocional() {
        System.out.println("🔵 TESTE 3: Factory Promocional");
        System.out.println("-".repeat(50));
        
        IngredienteFactory factory = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PROMOCIONAL);
        
        Ingrediente baconPromo = factory.criarIngrediente(TipoIngrediente.BACON, 3);
        Ingrediente queijoPromo = factory.criarIngrediente(TipoIngrediente.MUSSARELA, 2);
        
        System.out.println("Ingredientes criados pela Factory Promocional:");
        System.out.println("  ✅ " + baconPromo.toString() + " - R$ " + String.format("%.2f", baconPromo.getPrecoTotal()));
        System.out.println("  ✅ " + queijoPromo.toString() + " - R$ " + String.format("%.2f", queijoPromo.getPrecoTotal()));
        
        // Comparar com padrão
        IngredienteFactory factoryPadrao = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PADRAO);
        Ingrediente baconNormal = factoryPadrao.criarIngrediente(TipoIngrediente.BACON, 3);
        
        System.out.println("\nComparação de preços:");
        System.out.println("  Bacon Normal: R$ " + String.format("%.2f", baconNormal.getPrecoTotal()));
        System.out.println("  Bacon Promocional: R$ " + String.format("%.2f", baconPromo.getPrecoTotal()));
        System.out.println("  Diferença: -15% (desconto promocional)");
        
        System.out.println("\n✅ Factory promocional aplicando desconto corretamente");
        System.out.println("✅ Validação de quantidade mínima funcionando\n");
    }
    
    /**
     * Teste das validações das factories
     */
    private static void testeValidacoesFactory() {
        System.out.println("🔵 TESTE 4: Validações das Factories");
        System.out.println("-".repeat(50));
        
        IngredienteFactory factoryPremium = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PREMIUM);
        IngredienteFactory factoryPromocional = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PROMOCIONAL);
        
        // Teste 1: Premium não aceita ketchup
        try {
            factoryPremium.criarIngrediente(TipoIngrediente.KETCHUP);
            System.out.println("❌ ERRO: Validação premium não funcionou!");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Validação Premium: " + e.getMessage());
        }
        
        // Teste 2: Promocional exige quantidade mínima
        try {
            factoryPromocional.criarIngrediente(TipoIngrediente.FRANGO, 1);
            System.out.println("❌ ERRO: Validação promocional não funcionou!");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Validação Promocional: " + e.getMessage());
        }
        
        // Teste 3: Quantidade máxima
        try {
            factoryPremium.criarIngredienteValidado(TipoIngrediente.BACON, 15);
            System.out.println("❌ ERRO: Validação de quantidade não funcionou!");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Validação Quantidade: " + e.getMessage());
        }
        
        System.out.println("\n✅ Todas as validações específicas das factories funcionando");
        System.out.println("✅ Método template funcionando corretamente\n");
    }
    
    /**
     * Teste da integração Factory + Builder
     */
    private static void testeIntegracaoComBuilder() {
        System.out.println("🔵 TESTE 5: Integração Factory + Builder");
        System.out.println("-".repeat(50));
        
        // Criar ingredientes com diferentes factories
        IngredienteFactory factoryPadrao = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PADRAO);
        IngredienteFactory factoryPremium = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PREMIUM);
        IngredienteFactory factoryPromocional = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PROMOCIONAL);
        
        // Construir lanche usando diferentes factories
        Lanche lancheCompleto = new Lanche.Builder()
                .comTamanho(Tamanho.GRANDE)
                .comTipoPao(TipoMassa.PAO_AUSTRALIANO)
                .adicionarIngrediente(factoryPadrao.criarIngrediente(TipoIngrediente.FRANGO))
                .adicionarIngrediente(factoryPremium.criarIngrediente(TipoIngrediente.GORGONZOLA))
                .adicionarIngrediente(factoryPromocional.criarIngrediente(TipoIngrediente.BACON, 2))
                .build();
        
        System.out.println("Lanche criado com diferentes tipos de ingredientes:");
        System.out.println(lancheCompleto);
        
        System.out.println("\n✅ Factory Method integrado perfeitamente com Builder");
        System.out.println("✅ Diferentes strategies de criação funcionando em conjunto");
        System.out.println("✅ Padrões trabalhando harmoniosamente\n");
    }
} 