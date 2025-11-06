package com.lanchonete.model;

import com.lanchonete.enums.TipoIngrediente;

/**
 * Classe que representa um ingrediente no pedido
 */
public class Ingrediente {
    private final TipoIngrediente tipo;
    private final int quantidade;
    
    public Ingrediente(TipoIngrediente tipo, int quantidade) {
        this.tipo = tipo;
        this.quantidade = quantidade;
    }
    
    public Ingrediente(TipoIngrediente tipo) {
        this(tipo, 1);
    }
    
    public TipoIngrediente getTipo() {
        return tipo;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public double getPrecoTotal() {
        return tipo.getPreco() * quantidade;
    }
    
    @Override
    public String toString() {
        if (quantidade > 1) {
            return quantidade + "x " + tipo.getNome();
        }
        return tipo.getNome();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingrediente that = (Ingrediente) obj;
        return tipo == that.tipo;
    }
    
    @Override
    public int hashCode() {
        return tipo.hashCode();
    }
} 