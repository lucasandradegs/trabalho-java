package com.lanchonete.model;

import com.lanchonete.enums.Tamanho;
import com.lanchonete.enums.TipoMassa;
import com.lanchonete.validator.ValidadorCombinacoes;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa um lanche customizado
 * Utiliza o padrão Builder para construção
 */
public class Lanche {
    private final Tamanho tamanho;
    private final TipoMassa tipoPao;
    private final List<Ingrediente> ingredientes;
    private final double precoBase;
    
    // Construtor privado - só pode ser chamado pelo Builder
    private Lanche(Builder builder) {
        this.tamanho = builder.tamanho;
        this.tipoPao = builder.tipoPao;
        this.ingredientes = new ArrayList<>(builder.ingredientes);
        this.precoBase = builder.precoBase;
    }
    
    public Tamanho getTamanho() {
        return tamanho;
    }
    
    public TipoMassa getTipoPao() {
        return tipoPao;
    }
    
    public List<Ingrediente> getIngredientes() {
        return new ArrayList<>(ingredientes);
    }
    
    public double getPrecoBase() {
        return precoBase;
    }
    
    /**
     * Calcula o preço total do lanche
     */
    public double calcularPrecoTotal() {
        double precoIngredientes = ingredientes.stream()
                .mapToDouble(Ingrediente::getPrecoTotal)
                .sum();
        
        double precoTotal = (precoBase + precoIngredientes + tipoPao.getPrecoAdicional()) 
                           * tamanho.getMultiplicadorPreco();
        
        return Math.round(precoTotal * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🥪 LANCHE ").append(tamanho.getNome().toUpperCase()).append("\n");
        sb.append("Pão: ").append(tipoPao.getNome()).append("\n");
        sb.append("Ingredientes:\n");
        
        if (ingredientes.isEmpty()) {
            sb.append("  - Nenhum ingrediente adicional\n");
        } else {
            for (Ingrediente ingrediente : ingredientes) {
                sb.append("  - ").append(ingrediente.toString()).append("\n");
            }
        }
        
        sb.append("Preço Total: R$ ").append(String.format("%.2f", calcularPrecoTotal()));
        return sb.toString();
    }
    
    /**
     * Classe Builder interna estática para construir lanches
     */
    public static class Builder {
        private Tamanho tamanho = Tamanho.MEDIO; // Padrão
        private TipoMassa tipoPao = TipoMassa.PAO_TRADICIONAL; // Padrão
        private List<Ingrediente> ingredientes = new ArrayList<>();
        private double precoBase = 15.0; // Preço base do lanche
        
        public Builder comTamanho(Tamanho tamanho) {
            this.tamanho = tamanho;
            return this;
        }
        
        public Builder comTipoPao(TipoMassa tipoPao) {
            this.tipoPao = tipoPao;
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
         * Valida as configurações e constrói o lanche
         */
        public Lanche build() {
            validarConfiguracao();
            return new Lanche(this);
        }
        
        private void validarConfiguracao() {
            if (tamanho == null) {
                throw new IllegalStateException("Tamanho deve ser especificado");
            }
            if (tipoPao == null) {
                throw new IllegalStateException("Tipo de pão deve ser especificado");
            }
            if (precoBase < 0) {
                throw new IllegalStateException("Preço base não pode ser negativo");
            }
            
            // Usar o validador de combinações
            ValidadorCombinacoes.validarLanche(tamanho, tipoPao, ingredientes);
        }
    }
} 