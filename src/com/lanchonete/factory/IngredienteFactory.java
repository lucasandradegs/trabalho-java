package com.lanchonete.factory;

import com.lanchonete.model.Ingrediente;
import com.lanchonete.enums.TipoIngrediente;

/**
 * Factory Method para criação de ingredientes
 * Implementa o padrão Factory Method como segundo padrão GoF obrigatório
 */
public abstract class IngredienteFactory {
    
    /**
     * Método factory abstrato - cada subclasse implementa sua estratégia
     */
    public abstract Ingrediente criarIngrediente(TipoIngrediente tipo, int quantidade);
    
    /**
     * Método factory com quantidade padrão
     */
    public Ingrediente criarIngrediente(TipoIngrediente tipo) {
        return criarIngrediente(tipo, 1);
    }
    
    /**
     * Método template que pode incluir validações comuns
     */
    public final Ingrediente criarIngredienteValidado(TipoIngrediente tipo, int quantidade) {
        validarQuantidade(quantidade);
        validarTipo(tipo);
        return criarIngrediente(tipo, quantidade);
    }
    
    /**
     * Validações comuns para todos os tipos de ingredientes
     */
    protected void validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        if (quantidade > 10) {
            throw new IllegalArgumentException("Quantidade máxima de 10 unidades por ingrediente");
        }
    }
    
    protected void validarTipo(TipoIngrediente tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de ingrediente não pode ser nulo");
        }
    }
    
    /**
     * Factory Method estático para obter a factory apropriada
     */
    public static IngredienteFactory obterFactory(TipoFactory tipoFactory) {
        switch (tipoFactory) {
            case PADRAO:
                return new IngredienteFactoryPadrao();
            case PREMIUM:
                return new IngredienteFactoryPremium();
            case PROMOCIONAL:
                return new IngredienteFactoryPromocional();
            default:
                throw new IllegalArgumentException("Tipo de factory não reconhecido");
        }
    }
    
    /**
     * Enum para definir tipos de factory disponíveis
     */
    public enum TipoFactory {
        PADRAO,
        PREMIUM,
        PROMOCIONAL
    }
} 