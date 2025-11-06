package com.lanchonete.enums;

/**
 * Enum que define os tipos de massa/pão disponíveis
 */
public enum TipoMassa {
    // Para pizzas
    FINA("Massa Fina", 0.0),
    GROSSA("Massa Grossa", 2.0),
    BORDA_RECHEADA("Borda Recheada", 5.0),
    
    // Para lanches
    PAO_TRADICIONAL("Pão Tradicional", 0.0),
    PAO_INTEGRAL("Pão Integral", 1.5),
    PAO_AUSTRALIANO("Pão Australiano", 3.0),
    CIABATTA("Ciabatta", 4.0);
    
    private final String nome;
    private final double precoAdicional;
    
    TipoMassa(String nome, double precoAdicional) {
        this.nome = nome;
        this.precoAdicional = precoAdicional;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getPrecoAdicional() {
        return precoAdicional;
    }
    
    @Override
    public String toString() {
        return nome;
    }
} 