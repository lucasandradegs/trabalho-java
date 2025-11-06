package com.lanchonete.model;

import com.lanchonete.enums.Tamanho;
import com.lanchonete.enums.TipoMassa;
import com.lanchonete.validator.ValidadorCombinacoes;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa uma pizza customizada
 * Utiliza o padrão Builder para construção
 */
public class Pizza {
    private final Tamanho tamanho;
    private final TipoMassa tipoMassa;
    private final List<Ingrediente> ingredientes;
    private final double precoBase;
    
    // Construtor privado - só pode ser chamado pelo Builder
    private Pizza(Builder builder) {
        this.tamanho = builder.tamanho;
        this.tipoMassa = builder.tipoMassa;
        this.ingredientes = new ArrayList<>(builder.ingredientes);
        this.precoBase = builder.precoBase;
    }
    
    public Tamanho getTamanho() {
        return tamanho;
    }
    
    public TipoMassa getTipoMassa() {
        return tipoMassa;
    }
    
    public List<Ingrediente> getIngredientes() {
        return new ArrayList<>(ingredientes);
    }
    
    public double getPrecoBase() {
        return precoBase;
    }
    
    /**
     * Calcula o preço total da pizza
     */
    public double calcularPrecoTotal() {
        double precoIngredientes = ingredientes.stream()
                .mapToDouble(Ingrediente::getPrecoTotal)
                .sum();
        
        double precoTotal = (precoBase + precoIngredientes + tipoMassa.getPrecoAdicional()) 
                           * tamanho.getMultiplicadorPreco();
        
        return Math.round(precoTotal * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🍕 PIZZA ").append(tamanho.getNome().toUpperCase()).append("\n");
        sb.append("Massa: ").append(tipoMassa.getNome()).append("\n");
        sb.append("Ingredientes:\n");
        
        if (ingredientes.isEmpty()) {
            sb.append("  - Apenas queijo e molho de tomate\n");
        } else {
            sb.append("  - Queijo e molho de tomate (base)\n");
            for (Ingrediente ingrediente : ingredientes) {
                sb.append("  - ").append(ingrediente.toString()).append("\n");
            }
        }
        
        sb.append("Preço Total: R$ ").append(String.format("%.2f", calcularPrecoTotal()));
        return sb.toString();
    }
    
    /**
     * Classe Builder interna estática para construir pizzas
     */
    public static class Builder {
        private Tamanho tamanho = Tamanho.MEDIO; // Padrão
        private TipoMassa tipoMassa = TipoMassa.FINA; // Padrão
        private List<Ingrediente> ingredientes = new ArrayList<>();
        private double precoBase = 20.0; // Preço base da pizza
        
        public Builder comTamanho(Tamanho tamanho) {
            this.tamanho = tamanho;
            return this;
        }
        
        public Builder comTipoMassa(TipoMassa tipoMassa) {
            this.tipoMassa = tipoMassa;
            return this;
        }
        
        public Builder adicionarIngrediente(Ingrediente ingrediente) {
            // Verifica se já existe o mesmo tipo de ingrediente
            for (int i = 0; i < ingredientes.size(); i++) {
                if (ingredientes.get(i).equals(ingrediente)) {
                    // Substitui por um com quantidade somada
                    Ingrediente existente = ingredientes.get(i);
                    Ingrediente novo = new Ingrediente(
                        existente.getTipo(), 
                        existente.getQuantidade() + ingrediente.getQuantidade()
                    );
                    ingredientes.set(i, novo);
                    return this;
                }
            }
            ingredientes.add(ingrediente);
            return this;
        }
        
        public Builder comPrecoBase(double precoBase) {
            this.precoBase = precoBase;
            return this;
        }
        
        /**
         * Valida as configurações e constrói a pizza
         */
        public Pizza build() {
            validarConfiguracao();
            return new Pizza(this);
        }
        
        private void validarConfiguracao() {
            if (tamanho == null) {
                throw new IllegalStateException("Tamanho deve ser especificado");
            }
            if (tipoMassa == null) {
                throw new IllegalStateException("Tipo de massa deve ser especificado");
            }
            if (precoBase < 0) {
                throw new IllegalStateException("Preço base não pode ser negativo");
            }
            
            // Usar o validador de combinações (que já inclui a validação de borda recheada)
            ValidadorCombinacoes.validarPizza(tamanho, tipoMassa, ingredientes);
        }
    }
} 