package com.lanchonete.factory;

import com.lanchonete.model.Ingrediente;
import com.lanchonete.model.IngredientePromocional;
import com.lanchonete.enums.TipoIngrediente;

/**
 * Factory concreta para ingredientes promocionais
 * Cria ingredientes com desconto aplicado
 */
public class IngredienteFactoryPromocional extends IngredienteFactory {
    
    private static final double DESCONTO_PROMOCIONAL = 0.15; // 15% de desconto
    
    @Override
    public Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade) {
        validarPromocao(quantidade);
        return new IngredientePromocional(tipo, quantidade, DESCONTO_PROMOCIONAL);
    }
    
    /**
     * Validações específicas para promoções
     */
    private void validarPromocao(int quantidade) {
        // Promoção só válida para quantidade >= 2
        if (quantidade < 2) {
            throw new IllegalArgumentException(
                "Promoção válida apenas para quantidade mínima de 2 ingredientes"
            );
        }
    }
} 