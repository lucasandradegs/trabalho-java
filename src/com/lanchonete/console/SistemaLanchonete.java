package com.lanchonete.console;

import com.lanchonete.model.*;
import com.lanchonete.enums.*;
import com.lanchonete.factory.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Sistema principal da lanchonete com interface console interativa
 * Implementa todos os requisitos de interface de usuário
 */
public class SistemaLanchonete {
    private static IngredienteFactory factory;
    private static List<Object> pedidosRealizados = new ArrayList<>();
    
    public static void main(String[] args) {
        MenuConsole.exibirTitulo("🍕🥪 SISTEMA DE PEDIDOS - LANCHONETE BUILDER 🥪🍕");
        
        while (true) {
            try {
                String[] opcoesPrincipais = {
                    "🥪 Montar um Lanche",
                    "🍕 Montar uma Pizza", 
                    "📋 Ver Pedidos Realizados",
                    "🚪 Sair"
                };
                
                int opcao = MenuConsole.exibirMenuOpcoes("MENU PRINCIPAL", opcoesPrincipais);
                
                switch (opcao) {
                    case 1:
                        montarLanche();
                        break;
                    case 2:
                        montarPizza();
                        break;
                    case 3:
                        exibirPedidosRealizados();
                        break;
                    case 4:
                        System.out.println("\n🎉 Obrigado por usar nosso sistema!");
                        System.out.println("👋 Volte sempre!");
                        MenuConsole.fechar();
                        return;
                }
                
            } catch (Exception e) {
                MenuConsole.exibirErro("Erro inesperado: " + e.getMessage());
                MenuConsole.pausar();
            }
        }
    }
    
    /**
     * Processo completo de montagem de lanche
     */
    private static void montarLanche() {
        MenuConsole.limparTela();
        MenuConsole.exibirTitulo("🥪 MONTAGEM DE LANCHE");
        
        try {
            // 1. Escolher tipo de ingredientes
            escolherTipoIngredientes();
            
            // 2. Escolher tamanho
            Tamanho tamanho = escolherTamanho();
            
            // 3. Escolher tipo de pão
            TipoMassa tipoPao = escolherTipoPao();
            
            // 4. Montar ingredientes
            List<Ingrediente> ingredientes = montarIngredientes("lanche", false);
            
            // 5. Construir lanche usando Builder
            Lanche.Builder builder = new Lanche.Builder()
                .comTamanho(tamanho)
                .comTipoPao(tipoPao)
                .comPrecoBase(15.0);
            
            // Adicionar ingredientes ao builder
            for (Ingrediente ingrediente : ingredientes) {
                builder.adicionarIngrediente(ingrediente);
            }
            
            Lanche lancheCompleto = builder.build();
            
            // 6. Exibir resumo e confirmar
            exibirResumo("🥪 SEU LANCHE", lancheCompleto);
            
            if (MenuConsole.perguntarContinuar("✅ Confirmar pedido?")) {
                pedidosRealizados.add(lancheCompleto);
                MenuConsole.exibirSucesso("Lanche adicionado aos pedidos!");
            } else {
                MenuConsole.exibirErro("Pedido cancelado.");
            }
            
        } catch (IllegalStateException e) {
            MenuConsole.exibirErro("Configuração inválida: " + e.getMessage());
            MenuConsole.exibirErro("Tente novamente com outras opções.");
        }
        
        MenuConsole.pausar();
    }
    
    /**
     * Processo completo de montagem de pizza
     */
    private static void montarPizza() {
        MenuConsole.limparTela();
        MenuConsole.exibirTitulo("🍕 MONTAGEM DE PIZZA");
        
        try {
            // 1. Escolher tipo de ingredientes
            escolherTipoIngredientes();
            
            // 2. Escolher tamanho
            Tamanho tamanho = escolherTamanho();
            
            // 3. Escolher tipo de massa
            TipoMassa tipoMassa = escolherTipoMassa();
            
            // 4. Escolher sabor da pizza
            SaborPizza sabor = escolherSaborPizza();
            
            // 5. Adicionar ingredientes do sabor
            List<Ingrediente> ingredientes = criarIngredientesSabor(sabor);
            
            // 6. Opção de ingredientes extras
            if (MenuConsole.perguntarContinuar("🧄 Adicionar ingredientes extras?")) {
                List<Ingrediente> extras = montarIngredientes("pizza", true);
                ingredientes.addAll(extras);
            }
            
            // 5. Construir pizza usando Builder
            Pizza.Builder builderPizza = new Pizza.Builder()
                .comTamanho(tamanho)
                .comTipoMassa(tipoMassa)
                .comPrecoBase(20.0);
            
            // Adicionar ingredientes ao builder
            for (Ingrediente ingrediente : ingredientes) {
                builderPizza.adicionarIngrediente(ingrediente);
            }
            
            Pizza pizzaCompleta = builderPizza.build();
            
            // 6. Exibir resumo e confirmar
            exibirResumo("🍕 SUA PIZZA", pizzaCompleta);
            
            if (MenuConsole.perguntarContinuar("✅ Confirmar pedido?")) {
                pedidosRealizados.add(pizzaCompleta);
                MenuConsole.exibirSucesso("Pizza adicionada aos pedidos!");
            } else {
                MenuConsole.exibirErro("Pedido cancelado.");
            }
            
        } catch (IllegalStateException e) {
            MenuConsole.exibirErro("Configuração inválida: " + e.getMessage());
            MenuConsole.exibirErro("Tente novamente com outras opções.");
        }
        
        MenuConsole.pausar();
    }
    
    /**
     * Permite escolher o tipo de ingredientes (Factory Method)
     */
    private static void escolherTipoIngredientes() {
        String[] tiposFactory = {
            "🥗 Ingredientes Padrão (preço normal)",
            "💎 Ingredientes Premium (+30% no preço)",
            "🎯 Ingredientes Promocionais (-15% no preço, mín. 2 unidades)"
        };
        
        int opcao = MenuConsole.exibirMenuOpcoes("ESCOLHA O TIPO DE INGREDIENTES", tiposFactory);
        
        switch (opcao) {
            case 1:
                factory = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PADRAO);
                System.out.println("✅ Ingredientes padrão selecionados");
                break;
            case 2:
                factory = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PREMIUM);
                System.out.println("✅ Ingredientes premium selecionados (+30% preço)");
                break;
            case 3:
                factory = IngredienteFactory.obterFactory(IngredienteFactory.TipoFactory.PROMOCIONAL);
                System.out.println("✅ Ingredientes promocionais selecionados (-15% preço)");
                break;
        }
    }
    
    /**
     * Menu para escolher tamanho
     */
    private static Tamanho escolherTamanho() {
        Tamanho[] tamanhos = Tamanho.values();
        MenuConsole.exibirLista("ESCOLHA O TAMANHO", tamanhos);
        
        int opcao = MenuConsole.lerOpcao(1, tamanhos.length);
        Tamanho tamanhoEscolhido = tamanhos[opcao - 1];
        
        System.out.println("✅ Tamanho selecionado: " + tamanhoEscolhido.getNome());
        return tamanhoEscolhido;
    }
    
    /**
     * Menu para escolher tipo de pão (para lanches)
     */
    private static TipoMassa escolherTipoPao() {
        TipoMassa[] paes = {
            TipoMassa.PAO_TRADICIONAL,
            TipoMassa.PAO_INTEGRAL,
            TipoMassa.PAO_AUSTRALIANO,
            TipoMassa.CIABATTA
        };
        
        MenuConsole.exibirLista("ESCOLHA O TIPO DE PÃO", paes);
        int opcao = MenuConsole.lerOpcao(1, paes.length);
        TipoMassa paoEscolhido = paes[opcao - 1];
        
        System.out.println("✅ Pão selecionado: " + paoEscolhido.getNome());
        return paoEscolhido;
    }
    
    /**
     * Menu para escolher tipo de massa (para pizzas)
     */
    private static TipoMassa escolherTipoMassa() {
        TipoMassa[] massas = {
            TipoMassa.FINA,
            TipoMassa.GROSSA,
            TipoMassa.BORDA_RECHEADA
        };
        
        MenuConsole.exibirLista("ESCOLHA O TIPO DE MASSA", massas);
        int opcao = MenuConsole.lerOpcao(1, massas.length);
        TipoMassa massaEscolhida = massas[opcao - 1];
        
        System.out.println("✅ Massa selecionada: " + massaEscolhida.getNome());
        return massaEscolhida;
    }
    
    /**
     * Menu para escolher sabor da pizza
     */
    private static SaborPizza escolherSaborPizza() {
        SaborPizza[] sabores = SaborPizza.values();
        
        System.out.println("\n🍕 ESCOLHA O SABOR DA PIZZA");
        System.out.println("-".repeat(40));
        
        for (int i = 0; i < sabores.length; i++) {
            System.out.println((i + 1) + ". " + sabores[i].toString());
        }
        
        int opcao = MenuConsole.lerOpcao(1, sabores.length);
        SaborPizza saborEscolhido = sabores[opcao - 1];
        
        System.out.println("✅ Sabor selecionado: " + saborEscolhido.getNome());
        return saborEscolhido;
    }
    
    /**
     * Cria ingredientes baseados no sabor da pizza
     */
    private static List<Ingrediente> criarIngredientesSabor(SaborPizza sabor) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        
        System.out.println("\n🧄 Adicionando ingredientes do sabor " + sabor.getNome() + "...");
        
        for (TipoIngrediente tipo : sabor.getIngredientes()) {
            try {
                Ingrediente ingrediente = factory.criarIngrediente(tipo, 1);
                ingredientes.add(ingrediente);
                System.out.println("✅ " + ingrediente.toString());
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ " + tipo.getNome() + " não disponível no tipo selecionado");
            }
        }
        
        return ingredientes;
    }
    
    /**
     * Processo de montagem de ingredientes por categoria
     */
    private static List<Ingrediente> montarIngredientes(String tipoProduto, boolean saoExtras) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        
        if (saoExtras) {
            System.out.println("\n🧄 Agora vamos adicionar ingredientes extras ao seu " + tipoProduto + "!");
            System.out.println("💡 Dica: Cada porção extra equivale a aproximadamente 50g");
        } else {
            System.out.println("\n🧄 Agora vamos montar os ingredientes do seu " + tipoProduto + "!");
            System.out.println("💡 Dica: Para lanches, cada porção equivale a aproximadamente 50g");
        }
        
        // Proteínas
        adicionarIngredientesPorCategoria(ingredientes, TipoIngrediente.Categoria.PROTEINA, "PROTEÍNAS");
        
        // Queijos
        adicionarIngredientesPorCategoria(ingredientes, TipoIngrediente.Categoria.QUEIJO, "QUEIJOS");
        
        // Vegetais
        adicionarIngredientesPorCategoria(ingredientes, TipoIngrediente.Categoria.VEGETAL, "VEGETAIS");
        
        // Molhos
        adicionarIngredientesPorCategoria(ingredientes, TipoIngrediente.Categoria.MOLHO, "MOLHOS");
        
        return ingredientes;
    }
    
    /**
     * Adiciona ingredientes de uma categoria específica
     */
    private static void adicionarIngredientesPorCategoria(List<Ingrediente> ingredientes, 
                                                         TipoIngrediente.Categoria categoria, 
                                                         String nomeCategoria) {
        
        // Filtrar ingredientes por categoria
        TipoIngrediente[] todosIngredientes = TipoIngrediente.values();
        List<TipoIngrediente> ingredientesCategoria = new ArrayList<>();
        
        for (TipoIngrediente tipo : todosIngredientes) {
            if (tipo.getCategoria() == categoria) {
                ingredientesCategoria.add(tipo);
            }
        }
        
        if (MenuConsole.perguntarContinuar("🧄 Adicionar " + nomeCategoria.toLowerCase() + "?")) {
            while (true) {
                MenuConsole.exibirLista("ESCOLHA " + nomeCategoria, 
                                      ingredientesCategoria.toArray(new TipoIngrediente[0]));
                
                int opcao = MenuConsole.lerOpcao(1, ingredientesCategoria.size());
                TipoIngrediente tipoEscolhido = ingredientesCategoria.get(opcao - 1);
                
                int quantidade = MenuConsole.lerQuantidade();
                
                try {
                    Ingrediente ingrediente = factory.criarIngrediente(tipoEscolhido, quantidade);
                    ingredientes.add(ingrediente);
                    
                    System.out.println("✅ Adicionado: " + ingrediente.toString());
                    
                } catch (IllegalArgumentException e) {
                    MenuConsole.exibirErro("Não foi possível adicionar: " + e.getMessage());
                }
                
                if (!MenuConsole.perguntarContinuar("Adicionar mais " + nomeCategoria.toLowerCase() + "?")) {
                    break;
                }
            }
        }
    }
    
    /**
     * Exibe resumo do produto construído
     */
    private static void exibirResumo(String titulo, Object produto) {
        MenuConsole.limparTela();
        MenuConsole.exibirTitulo(titulo);
        System.out.println(produto.toString());
    }
    
    /**
     * Exibe histórico de pedidos realizados
     */
    private static void exibirPedidosRealizados() {
        MenuConsole.limparTela();
        MenuConsole.exibirTitulo("📋 PEDIDOS REALIZADOS");
        
        if (pedidosRealizados.isEmpty()) {
            System.out.println("❌ Nenhum pedido realizado ainda.");
        } else {
            for (int i = 0; i < pedidosRealizados.size(); i++) {
                System.out.println("\n📦 PEDIDO #" + (i + 1));
                System.out.println("-".repeat(40));
                System.out.println(pedidosRealizados.get(i).toString());
            }
            
            double totalPedidos = 0;
            for (Object pedido : pedidosRealizados) {
                if (pedido instanceof Lanche) {
                    totalPedidos += ((Lanche) pedido).calcularPrecoTotal();
                } else if (pedido instanceof Pizza) {
                    totalPedidos += ((Pizza) pedido).calcularPrecoTotal();
                }
            }
            
            System.out.println("\n💰 VALOR TOTAL DOS PEDIDOS: R$ " + String.format("%.2f", totalPedidos));
        }
        
        MenuConsole.pausar();
    }
} 