package com.lanchonete.enums;

/**
 * Enum que define os tamanhos disponíveis para lanches e pizzas
 */
public enum Tamanho {
    PEQUENO("Pequeno", 1.0),
    MEDIO("Médio", 1.5),
    GRANDE("Grande", 2.0);
    
    private final String nome;
    private final double multiplicadorPreco;
    
    Tamanho(String nome, double multiplicadorPreco) {
        this.nome = nome;
        this.multiplicadorPreco = multiplicadorPreco;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getMultiplicadorPreco() {
        return multiplicadorPreco;
    }
    
    @Override
    public String toString() {
        return nome;
    }
} 