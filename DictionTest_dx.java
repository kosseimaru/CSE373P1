package datastructures.concrete.dictionaries;

import datastructures.interfaces.IDictionary;

public class DictionTest {
    
    //dengxian: test
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        IDictionary<String,Integer> dict = new ArrayDictionary<>();
        dict.put("a", 1);
        dict.put("b", 2);
        dict.put("c", 3);
        System.out.println(dict.toString());
//        System.out.println(dict.containsKey("c"));
        dict.remove("d");
        System.out.println(dict.toString());
        
    }

}
