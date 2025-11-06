package com.lanchonete.factory;

import com.lanchonete.model.Ingrediente;
import com.lanchonete.model.IngredientePremium;
import com.lanchonete.enums.TipoIngrediente;

/**
 * Factory concreta para ingredientes premium
 * Cria ingredientes com qualidade superior e validações específicas
 */
public class IngredienteFactoryPremium extends IngredienteFactory {
    
    @Override
    public Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade) {
        validarIngredientePremium(tipo);
        return new IngredientePremium(tipo, quantidade);
    }
    
    /**
     * Validações específicas para ingredientes premium
     */
    private void validarIngredientePremium(TipoIngrediente tipo) {
        // Alguns ingredientes não estão disponíveis na linha premium
        if (tipo == TipoIngrediente.KETCHUP || tipo == TipoIngrediente.MOSTARDA) {
            throw new IllegalArgumentException(
                "Ingrediente " + tipo.getNome() + " não disponível na linha premium"
            );
        }
    }
} 