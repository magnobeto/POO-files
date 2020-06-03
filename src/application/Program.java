
package application;

import entities.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Roberto
 */
public class Program {
    
    public static void main(String[] args){
        
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        boolean decision = false;
        
        while(decision == false){
            System.out.print("\nEntre com o caminho do diretorio: ");
            String path = sc.nextLine();
            searchFile(path);
            System.out.print("O arquivo que deseja abrir esta aqui?(s/n) ");
            String ch = sc.next().substring(0, 1).toLowerCase();
            sc.nextLine();
            decision = isThereMyFile(ch,decision);
        }
        
        System.out.print("Digite o endereco do arquivo: ");
        String path = sc.nextLine();
        List<Product> list = readFile(path);
        System.out.print("Digite o endereco destino: ");
        path = sc.nextLine();
        path = generateOutput(path,list);
		sc.close();
    }
    
    public static List<Product> readFile(String path){
        
        List<Product> list =  new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            
            String line = br.readLine();
            while(line != null){
                String[] productInfo = line.split(",");
                Product product = new Product(productInfo[0],Double.parseDouble(productInfo[1]),Integer.parseInt(productInfo[2]));
                list.add(product);
                line = br.readLine();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public static String generateOutput(String strPath, List<Product> list){
        
        boolean outDir = new File(strPath + "\\out").mkdir();
        String pathSumary =  strPath + "\\out\\summary.csv";
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(pathSumary,true))){
            
            for(Product p : list){
                bw.write(p.totalValue());
                bw.newLine();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return "\nDiretorio criado com sucesso!\n";
    }
    
    public static void searchFile(String path){
         
        for (File file : listAllDirectories(path)){
            System.out.println(file);
        }        
    }
    
    public static boolean isThereMyFile(String ch,boolean decision){
        
        if(ch.equals("s")){
                decision = true;
        }
        return decision;
    }
    
    public static List<File> listAllDirectories(String strPath){
        
        File path = new File(strPath);
        List<File> list = new ArrayList<>();
        
        File[] folders = path.listFiles(File::isDirectory);
        for (File folder : folders){
           list.add(folder);
        }
        
        File[] files = path.listFiles(File::isFile);
        for (File file : files){
            list.add(file);
        }
        
        return list;
    }
}
