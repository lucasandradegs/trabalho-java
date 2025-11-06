package com.lanchonete.enums;

/**
 * Enum que define os tipos de ingredientes disponíveis
 */
public enum TipoIngrediente {
    // Proteínas
    FRANGO("Frango", 8.0, Categoria.PROTEINA),
    CARNE_BOVINA("Carne Bovina", 10.0, Categoria.PROTEINA),
    BACON("Bacon", 6.0, Categoria.PROTEINA),
    PRESUNTO("Presunto", 5.0, Categoria.PROTEINA),
    CALABRESA("Calabresa", 7.0, Categoria.PROTEINA),
    
    // Queijos
    MUSSARELA("Mussarela", 4.0, Categoria.QUEIJO),
    CHEDDAR("Cheddar", 5.0, Categoria.QUEIJO),
    PARMESAO("Parmesão", 6.0, Categoria.QUEIJO),
    GORGONZOLA("Gorgonzola", 7.0, Categoria.QUEIJO),
    
    // Vegetais
    TOMATE("Tomate", 2.0, Categoria.VEGETAL),
    ALFACE("Alface", 1.5, Categoria.VEGETAL),
    CEBOLA("Cebola", 1.0, Categoria.VEGETAL),
    PIMENTAO("Pimentão", 2.5, Categoria.VEGETAL),
    AZEITONA("Azeitona", 3.0, Categoria.VEGETAL),
    
    // Molhos
    BARBECUE("Barbecue", 2.0, Categoria.MOLHO),
    MOSTARDA("Mostarda", 1.0, Categoria.MOLHO),
    MAIONESE("Maionese", 1.0, Categoria.MOLHO),
    KETCHUP("Ketchup", 1.0, Categoria.MOLHO),
    MOLHO_ESPECIAL("Molho Especial da Casa", 3.0, Categoria.MOLHO);
    
    private final String nome;
    private final double preco;
    private final Categoria categoria;
    
    TipoIngrediente(String nome, double preco, Categoria categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
    public enum Categoria {
        PROTEINA, QUEIJO, VEGETAL, MOLHO
    }
} 