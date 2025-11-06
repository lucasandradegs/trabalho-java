package com.lanchonete.model;

import com.lanchonete.enums.TipoIngrediente;

/**
 * Classe que representa um ingrediente promocional
 * Herda de Ingrediente mas com desconto aplicado
 */
public class IngredientePromocional extends Ingrediente {
    
    private final double percentualDesconto;
    
    public IngredientePromocional(TipoIngrediente tipo, int quantidade, double percentualDesconto) {
        super(tipo, quantidade);
        this.percentualDesconto = percentualDesconto;
    }
    
    public double getPercentualDesconto() {
        return percentualDesconto;
    }
    
    @Override
    public double getPrecoTotal() {
        return super.getPrecoTotal() * (1.0 - percentualDesconto);
    }
    
    @Override
    public String toString() {
        String base = super.toString();
        return base + String.format(" (-%d%% OFF)", (int)(percentualDesconto * 100));
    }
} 