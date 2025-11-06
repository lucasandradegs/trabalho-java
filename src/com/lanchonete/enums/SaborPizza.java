package com.lanchonete.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum que define sabores pré-definidos de pizza
 */
public enum SaborPizza {
    MARGHERITA("Margherita", "Molho de tomate, mussarela, manjericão", 
               Arrays.asList(TipoIngrediente.MUSSARELA)),
    
    CALABRESA("Calabresa", "Calabresa, cebola, mussarela", 
              Arrays.asList(TipoIngrediente.CALABRESA, TipoIngrediente.CEBOLA, TipoIngrediente.MUSSARELA)),
    
    PORTUGUESA("Portuguesa", "Presunto, ovos, cebola, azeitona, mussarela", 
               Arrays.asList(TipoIngrediente.PRESUNTO, TipoIngrediente.CEBOLA, TipoIngrediente.AZEITONA, TipoIngrediente.MUSSARELA)),
    
    FRANGO_CATUPIRY("Frango com Catupiry", "Frango desfiado, catupiry", 
                    Arrays.asList(TipoIngrediente.FRANGO, TipoIngrediente.MUSSARELA)),
    
    BACON("Bacon", "Bacon, mussarela", 
          Arrays.asList(TipoIngrediente.BACON, TipoIngrediente.MUSSARELA)),
    
    QUATRO_QUEIJOS("Quatro Queijos", "Mussarela, cheddar, parmesão, provolone", 
                   Arrays.asList(TipoIngrediente.MUSSARELA, TipoIngrediente.CHEDDAR, 
                                TipoIngrediente.PARMESAO, TipoIngrediente.MUSSARELA)),
    
    VEGETARIANA("Vegetariana", "Tomate, pimentão, cebola, azeitona, mussarela", 
                Arrays.asList(TipoIngrediente.TOMATE, TipoIngrediente.PIMENTAO, 
                             TipoIngrediente.CEBOLA, TipoIngrediente.AZEITONA, TipoIngrediente.MUSSARELA));
    
    private final String nome;
    private final String descricao;
    private final List<TipoIngrediente> ingredientes;
    
    SaborPizza(String nome, String descricao, List<TipoIngrediente> ingredientes) {
        this.nome = nome;
        this.descricao = descricao;
        this.ingredientes = ingredientes;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public List<TipoIngrediente> getIngredientes() {
        return ingredientes;
    }
    
    @Override
    public String toString() {
        return nome + " - " + descricao;
    }
} 