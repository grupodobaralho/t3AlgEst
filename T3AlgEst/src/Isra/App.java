package Isra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {
	
	private static GeneralTreeOfString arvore;
	private static LinkedListOfString lista;

	
	public static void main(String[] args) throws IOException{
		
		arvore = new GeneralTreeOfString();
		lista = new LinkedListOfString();
		
		/*================================================================================*/
		System.out.print("Carregando arquivo livro.txt... ");
		if(lerArquivo("livro.txt"))
			System.out.print("ok\n");
		else{
			System.out.println("Erro ao carregar arquivo livro.txt");
			return;
		}
		
		/*================================================================================*/
		System.out.println("Gerando a �rvore... ");
		if(arvore.montaArvore(lista)){
			System.out.print("ok\n");
			printaQuantidade();	
		}
		else
			throw new RuntimeException("Erro ao gerar a �rvore");
		System.out.println("Gerando o Sum�rio... ");		
		LinkedListOfString sumario = gerarSumario();
		if(sumario!=null)
			printaSumario(sumario);
		else
			throw new RuntimeException("Erro ao gerar o sum�rio");		

	
			
		/*
		LinkedListOfString lista1 = arvore.positionsPre();
		LinkedListOfString lista2 = arvore.positionsPos();
		LinkedListOfString lista3 = arvore.positionsWidth();
		LinkedListOfString lista4 = arvore.positions();
		
		System.out.println("Posi��es Pr�:");
		System.out.println(lista1.toString());
		System.out.println("Posi��es P�s:");
		System.out.println(lista2.toString());
		System.out.println("Posi��es Width:");
		System.out.println(lista3.toString());	
		System.out.println("Posi��es:");
		System.out.println(lista4.toString());
		
							
				Gerando o sum�rio... ok
				Imprimindo o livro para o arquivo livro_prod.txt... ok.
			*/
	
		
	}
	/* 1. Guardar a estrutura do livro em uma �rvore; */
	public static boolean lerArquivo(String arquivo) throws FileNotFoundException, IOException{
		
		try(BufferedReader in = new BufferedReader(new FileReader(arquivo))){ 				
			while(in.ready())  			
				lista.add(in.readLine());
			return true;
		}
	}
	
	/* 2. Gerar o sum�rio do livro, indicando cada cap�tulo, cada se��o e subse��o com os respectivos 
	n�meros de p�gina (assumindo que cada p�gina suporte 15 linhas de texto); */
	
	public static LinkedListOfString gerarSumario(){
		LinkedListOfString lista = arvore.positionsPre();
		if(lista==null) return null;
		LinkedListOfString sumario = new LinkedListOfString();
		int capitulo = 0, secao = 0, subsecao = 0;
		String linha = "", tipo = "", numeracao = "";
		for(int i = 0; i<lista.size();i++){
			linha = lista.get(i);
			tipo = linha.substring(0, 2);
			switch(tipo){
			case "C ":
				capitulo++;
				secao = 0;
				subsecao = 0;
				numeracao = String.valueOf(capitulo) + ". ";
				sumario.add(linha.replace(tipo, numeracao)); 
				break;
			case "S ":
				secao++;
				subsecao = 0;
				numeracao = String.valueOf(capitulo) + "."
							+ String.valueOf(secao) + ". ";
				sumario.add(linha.replace(tipo, numeracao));
				break;
			case "SS":
				subsecao++;
				numeracao = String.valueOf(capitulo) + "."
						+ String.valueOf(secao) + "."
						+ String.valueOf(subsecao) + ".";
			sumario.add(linha.replace(tipo, numeracao));
				break;
			default:
				break;
			}			
		}
		return sumario;		
	}
	public static void printaSumario(LinkedListOfString sumario){
		for(int i = 0; i<sumario.size(); i++){
			System.out.println(sumario.get(i));			
		}
	}
	
	
	/* 3. �Imprimir� (gravar) o livro em um arquivo texto de sa�da, observando o formato a seguir.*/
	
	
	/*Printa na tela a quantidade de capitulos, secoes, subsecoes e paragrafos presentes no livro*/
	public static void printaQuantidade(){
		int capitulos = 0, secoes = 0, subsecoes = 0, paragrafos = 0;
		for(int i = 0; i<lista.size();i++){
			switch(lista.get(i).substring(0, 2)){
				case "C ":
					capitulos++;
					break;
				case "S ":
					secoes++;
					break;
				case "SS":
					subsecoes++;
					break;
				case "P ":
					paragrafos++;
					break;
			}
		}			
	System.out.println("Capitulos...: " + capitulos
			+ "\nSe��es......: " + secoes
			+ "\nSubse��es...: " + subsecoes
			+ "\nPar�grafos..: " + paragrafos);			
	}	
}
