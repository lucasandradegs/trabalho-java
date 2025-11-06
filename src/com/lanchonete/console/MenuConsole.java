package com.lanchonete.console;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe utilitária para gerenciar interações do console
 */
public class MenuConsole {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Exibe título com formatação
     */
    public static void exibirTitulo(String titulo) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + titulo);
        System.out.println("=".repeat(60));
    }
    
    /**
     * Exibe menu de opções e retorna a escolha do usuário
     */
    public static int exibirMenuOpcoes(String titulo, String[] opcoes) {
        System.out.println("\n📋 " + titulo);
        System.out.println("-".repeat(40));
        
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + ". " + opcoes[i]);
        }
        
        return lerOpcao(1, opcoes.length);
    }
    
    /**
     * Lê uma opção válida do usuário
     */
    public static int lerOpcao(int min, int max) {
        while (true) {
            try {
                System.out.print("\n👉 Escolha uma opção (" + min + "-" + max + "): ");
                int opcao = Integer.parseInt(scanner.nextLine().trim());
                
                if (opcao >= min && opcao <= max) {
                    return opcao;
                }
                
                System.out.println("❌ Opção inválida! Digite um número entre " + min + " e " + max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite apenas números!");
            }
        }
    }
    
    /**
     * Lê quantidade de ingredientes
     */
    public static int lerQuantidade() {
        while (true) {
            try {
                System.out.print("   Quantas porções (1-10): ");
                int quantidade = Integer.parseInt(scanner.nextLine().trim());
                
                if (quantidade >= 1 && quantidade <= 10) {
                    return quantidade;
                }
                
                System.out.println("❌ Quantidade deve ser entre 1 e 10 porções!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite apenas números!");
            }
        }
    }
    
    /**
     * Pergunta se o usuário quer continuar
     */
    public static boolean perguntarContinuar(String pergunta) {
        while (true) {
            System.out.print("\n" + pergunta + " (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            
            if (resposta.equals("s") || resposta.equals("sim")) {
                return true;
            }
            if (resposta.equals("n") || resposta.equals("nao") || resposta.equals("não")) {
                return false;
            }
            
            System.out.println("❌ Digite 's' para sim ou 'n' para não");
        }
    }
    
    /**
     * Exibe lista numerada de itens
     */
    public static <T> void exibirLista(String titulo, T[] itens) {
        System.out.println("\n📋 " + titulo);
        System.out.println("-".repeat(40));
        
        for (int i = 0; i < itens.length; i++) {
            System.out.println((i + 1) + ". " + itens[i]);
        }
    }
    
    /**
     * Pausa para o usuário ler
     */
    public static void pausar() {
        System.out.print("\n📍 Pressione ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Limpa a tela (simulado)
     */
    public static void limparTela() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
    
    /**
     * Exibe mensagem de sucesso
     */
    public static void exibirSucesso(String mensagem) {
        System.out.println("\n✅ " + mensagem);
    }
    
    /**
     * Exibe mensagem de erro
     */
    public static void exibirErro(String mensagem) {
        System.out.println("\n❌ " + mensagem);
    }
    
    /**
     * Fecha o scanner (só chamar no final da aplicação)
     */
    public static void fechar() {
        scanner.close();
    }
} 