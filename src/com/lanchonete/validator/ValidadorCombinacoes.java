package com.lanchonete.validator;

import com.lanchonete.model.Ingrediente;
import com.lanchonete.enums.TipoIngrediente;
import com.lanchonete.enums.TipoMassa;
import com.lanchonete.enums.Tamanho;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Validador de combinações de ingredientes
 * Implementa regras de negócio para combinações válidas/inválidas
 */
public class ValidadorCombinacoes {
    
    // Combinações que não podem existir juntas
    private static final Set<Set<TipoIngrediente>> COMBINACOES_PROIBIDAS = new HashSet<>();
    
    // Ingredientes que têm limite por produto
    private static final int MAX_PROTEINAS_POR_PRODUTO = 3;
    private static final int MAX_QUEIJOS_POR_PRODUTO = 2;
    
    static {
        // Exemplo: Gorgonzola não combina com Cheddar (questão de sabor)
        Set<TipoIngrediente> combinacao1 = new HashSet<>();
        combinacao1.add(TipoIngrediente.GORGONZOLA);
        combinacao1.add(TipoIngrediente.CHEDDAR);
        COMBINACOES_PROIBIDAS.add(combinacao1);
        
        // Exemplo: Molho especial não combina com outros molhos
        Set<TipoIngrediente> combinacao2 = new HashSet<>();
        combinacao2.add(TipoIngrediente.MOLHO_ESPECIAL);
        combinacao2.add(TipoIngrediente.BARBECUE);
        COMBINACOES_PROIBIDAS.add(combinacao2);
        
        Set<TipoIngrediente> combinacao3 = new HashSet<>();
        combinacao3.add(TipoIngrediente.MOLHO_ESPECIAL);
        combinacao3.add(TipoIngrediente.KETCHUP);
        COMBINACOES_PROIBIDAS.add(combinacao3);
    }
    
    /**
     * Valida se uma lista de ingredientes é uma combinação válida
     */
    public static void validarCombinacao(List<Ingrediente> ingredientes) {
        validarCombinacaoProibida(ingredientes);
        validarLimitePorCategoria(ingredientes);
        validarQuantidadesTotais(ingredientes);
    }
    
    /**
     * Valida se os ingredientes para um lanche são apropriados
     */
    public static void validarLanche(Tamanho tamanho, TipoMassa tipoPao, List<Ingrediente> ingredientes) {
        validarCombinacao(ingredientes);
        
        // Validações específicas para lanches
        if (tipoPao == TipoMassa.CIABATTA && tamanho == Tamanho.PEQUENO) {
            throw new IllegalStateException("Ciabatta não disponível para lanche pequeno");
        }
        
        // Lanche pequeno tem limite de ingredientes
        if (tamanho == Tamanho.PEQUENO && ingredientes.size() > 4) {
            throw new IllegalStateException("Lanche pequeno permite no máximo 4 tipos de ingredientes");
        }
    }
    
    /**
     * Valida se os ingredientes para uma pizza são apropriados
     */
    public static void validarPizza(Tamanho tamanho, TipoMassa tipoMassa, List<Ingrediente> ingredientes) {
        validarCombinacao(ingredientes);
        
        // Validações específicas para pizzas
        if (tipoMassa == TipoMassa.BORDA_RECHEADA && tamanho == Tamanho.PEQUENO) {
            throw new IllegalStateException("Borda recheada não disponível para pizza pequena");
        }
        
        // Pizza deve ter pelo menos uma proteína
        boolean temProteina = ingredientes.stream()
                .anyMatch(ing -> ing.getTipo().getCategoria() == TipoIngrediente.Categoria.PROTEINA);
        
        if (!temProteina && !ingredientes.isEmpty()) {
            throw new IllegalStateException("Pizza deve ter pelo menos uma proteína");
        }
    }
    
    private static void validarCombinacaoProibida(List<Ingrediente> ingredientes) {
        Set<TipoIngrediente> tiposPresentes = new HashSet<>();
        
        for (Ingrediente ingrediente : ingredientes) {
            tiposPresentes.add(ingrediente.getTipo());
        }
        
        for (Set<TipoIngrediente> combinacaoProibida : COMBINACOES_PROIBIDAS) {
            if (tiposPresentes.containsAll(combinacaoProibida)) {
                throw new IllegalStateException(
                    "Combinação não permitida: " + combinacaoProibida.toString()
                );
            }
        }
    }
    
    private static void validarLimitePorCategoria(List<Ingrediente> ingredientes) {
        long countProteinas = ingredientes.stream()
                .filter(ing -> ing.getTipo().getCategoria() == TipoIngrediente.Categoria.PROTEINA)
                .count();
        
        long countQueijos = ingredientes.stream()
                .filter(ing -> ing.getTipo().getCategoria() == TipoIngrediente.Categoria.QUEIJO)
                .count();
        
        if (countProteinas > MAX_PROTEINAS_POR_PRODUTO) {
            throw new IllegalStateException(
                "Máximo de " + MAX_PROTEINAS_POR_PRODUTO + " tipos de proteína por produto"
            );
        }
        
        if (countQueijos > MAX_QUEIJOS_POR_PRODUTO) {
            throw new IllegalStateException(
                "Máximo de " + MAX_QUEIJOS_POR_PRODUTO + " tipos de queijo por produto"
            );
        }
    }
    
    private static void validarQuantidadesTotais(List<Ingrediente> ingredientes) {
        int totalQuantidade = ingredientes.stream()
                .mapToInt(Ingrediente::getQuantidade)
                .sum();
        
        if (totalQuantidade > 15) {
            throw new IllegalStateException(
                "Quantidade total de ingredientes não pode exceder 15 unidades"
            );
        }
    }
} 