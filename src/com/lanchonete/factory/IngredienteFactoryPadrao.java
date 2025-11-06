package com.lanchonete.factory;

import com.lanchonete.model.Ingrediente;
import com.lanchonete.enums.TipoIngrediente;

/**
 * Factory concreta para ingredientes padrão
 * Implementa a criação básica sem modificações
 */
public class IngredienteFactoryPadrao extends IngredienteFactory {
    
    @Override
    public Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade) {
        return new Ingrediente(tipo, quantidade);
    }
} 