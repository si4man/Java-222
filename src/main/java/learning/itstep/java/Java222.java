/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package learning.itstep.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author GA
 */
public class Java222 {

    public static void main(String[] args) {
        byte b; // 8 bit
        short s; // 16 bit
        int i; // 32 bit
        long l; // 64 bit
        b = -125;
        
        float f = 0.001f; // 32 bit
        double d = 1.5e-2; // 64 bit
        
        boolean bi = true;
        char c = 'Ї'; // 16 bit, UTF-16
        List<Integer> collection = new ArrayList<>();
        collection.add(10);
        collection.add(20);
        collection.add(30);
        collection.add(40);
        collection.add(50);

        Integer x = 10;
        Integer y = x;
        x = 20;   
        
        System.out.println("Hello!");
        System.out.println(y); // 10
        System.out.println("---------------------------------------");

        int[] arr = new int[10];
        for (int j = 0; j < arr.length; j++)
            arr[j] = j + 1;
        
        for(int k : collection)
            System.out.printf("%d ", k);
        
        System.out.println();
        
        System.out.println("---------------------------------------");

        String str1 = "Hello";
        String str2 = new String("Hello");
        if (str1 == str2) // operator == - reference-comparing
            System.out.println("str1 == str2");
        else
            System.out.println("str1 != str2");
        
        if (str1.equals(str2)) // esli uveren chto str1 != null
            System.out.println("str1 equals str2");
        else
            System.out.println("str1 !equals str2");
        
        if(Objects.equals(str1, str2)) //esli ne uveren chto kto-to != null
            System.out.println("str1 equals str2");
        else
            System.out.println("str1 !equals str2");
        
        System.out.println("---------------------------------------");

        str2 = "Hello";
                if (str1 == str2) // operator == - reference-comparing
            System.out.println("str1 == str2");
        else
            System.out.println("str1 != str2");
                
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j]);
            if (j < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
           
        
                System.out.println("---------------------------------------");
        System.out.println("---------------------------------------\n\n");

        int[][] m = {
            {1, 2},
            {3, 4}
        };

        int[] v = {5, 6};
        int[] r = new int[m.length];

        for (int ii = 0; ii < m.length; ii++) {
            int sum = 0;                 // вместо s
            for (int jj = 0; jj < m[ii].length; jj++) {
                sum += m[ii][jj] * v[jj];
            }
            r[ii] = sum;
        }

        for (int k = 0; k < r.length; k++) {
            System.out.print(r[k]);
            if (k < r.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

    }
}
/*
- Транслятор: компилирует в промежуточный код (для JRE)
- Парадигма: ООП 
- Поколение языкоа: 4GL
- Выходящий код: файл.java, промежуточный код: файл.сlass
- ! привязка к файловой системе
- пакет (package) имеет то же имя, что и путь для него (директории)
- Навзание класса совпадает с названием файла
*/