package com.lanchonete.model;

import com.lanchonete.enums.TipoIngrediente;

/**
 * Classe que representa um ingrediente premium
 * Herda de Ingrediente mas com qualidade superior
 */
public class IngredientePremium extends Ingrediente {
    
    private static final double MULTIPLICADOR_PREMIUM = 1.3; // 30% mais caro
    
    public IngredientePremium(TipoIngrediente tipo, int quantidade) {
        super(tipo, quantidade);
    }
    
    @Override
    public double getPrecoTotal() {
        return super.getPrecoTotal() * MULTIPLICADOR_PREMIUM;
    }
    
    @Override
    public String toString() {
        String base = super.toString();
        return base + " (Premium)";
    }
} 