package hash.server;

import java.util.HashMap;
import java.util.Map;

public class HashTable {
    private Map<String,String> hashTable = new HashMap<>();

    synchronized public int add(String key, String value){
        if (!hashTable.containsKey(key)) {
            hashTable.put(key,value);
            return 1; //sucesso
        }
        return 0; //fracasso
    }

    synchronized public String remove(String key){
        String value = hashTable.get(key);
        if(value == null)
            return null;

        hashTable.remove(key);
        return value;
    }

    synchronized public int update(String key, String value){
        if (hashTable.containsKey(key)){
            hashTable.replace(key,value);
            return 1;
        }else{
            return 0;
        }
    }

    synchronized public String read(String key){
        return hashTable.get(key);
    }

    synchronized public void showAll(){
        System.out.println("=================");
        System.out.println("Tabela atualizada:");
        for (String _key: hashTable.keySet()) {
            String key = _key;
            String value = hashTable.get(key);
            System.out.println(key + " " + value);
        }
        System.out.println("=================");
    }
}
