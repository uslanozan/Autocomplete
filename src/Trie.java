import java.util.ArrayList;

public class Trie {
    private TrieNode root;

    public Trie() {
        this.root = new TrieNode(null);
    }

    public void insert(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char nodes = word.charAt(i);
            // int index = nodes - 'a';
            // a harfinin ASCII değeri 97 dir. nodes'daki ASCII değerini çıkararak aslında
            // a-z arasındaki indexini buluruz
            int index = nodes - 97;

            //System.out.println(nodes + " has index of " + index);

            if (current.children[index] == null) {
                current.children[index] = new TrieNode(current);
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    // Trie içinde o kelime var mı diye kontrol et
    public boolean search(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a'; // Harfi dizi indeksine çevir

            // Eğer harf Trie içinde yoksa, kelime yoktur
            if (current.children[index] == null) {
                return false;
            }

            current = current.children[index]; // Sonraki düğüme geç
        }

        return current.isEndOfWord; // Kelimenin tam olup olmadığını kontrol et
    }

    public boolean hasChild(TrieNode node){

        // bir tane bile çocuğu null değilse true dönecek
        for (int i = 0; i < node.children.length; i++) {
            if(node.children[i] != null){
                return true;
            }    
        }
        return false;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;

        // Ön ekin her harfini kontrol et
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';

            // Eğer harf Trie'de yoksa, ön ek de yoktur
            if (current.children[index] == null) {
                return false;
            }

            current = current.children[index]; // Sonraki düğüme geç
        }

        // Ön ek bulundu
        return true;
    }

    public void delete(String word) {
        if (!search(word)) {
            return;
        }

        TrieNode current = root;
        TrieNode lastWord = null;

        // Ön ekin her harfini kontrol et
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a'; // - 97
            current = current.children[index]; // Sonraki düğüme geç
            if (current.isEndOfWord){

                if(hasChild(current)){
                    current.isEndOfWord = false;
                    return;
                }
                lastWord = current;
            }
                
        }
        current.isEndOfWord = false;
        TrieNode parent;
        while (true) {
            if (current == lastWord)
                break;
            parent = current.parent;
            current.children = null;
            current = null;
            current = parent;
        }

    }

    public int countWords() {
        //return countRecursive(root);
        int wordNum = 0;
        TrieNode node = root;
        int index = 0;

        while (true) {
            if (node.children[index] != null) {// alt seviyeye in
                node.index = index;
                node = node.children[index];
                index = 0;
                if (node.isEndOfWord) {
                    wordNum = wordNum + 1;
                }
                continue;
            }
            index++;
            while (index > node.children.length-1) { // üst seviyeye çık
                if (node == root)
                    return wordNum;
                node = node.parent;
                index = node.index+1;
            }
        }
    }

    // Trie içindeki kelimeleri yazdıran fonksiyon
    public void displayTrie() {
        if(root == null){
            System.out.println("Tree is empty!");
            return;
        }
        TrieNode node;
        node = root;
        int index = 0;

        String str = "";
        while (true) {
            if (node.children[index] != null) {// alt seviyeye in
                node.index = index;
                str += (char) (index + 'a'); // İndeksi tekrar harfe çevir
                node = node.children[index];
                index = 0;
                if (node.isEndOfWord) {
                    System.out.println(str);
                }
                continue;
            }
            index++;
            while (index > node.children.length-1) {
                if (node == root)
                    return;
                node = node.parent;
                index = node.index+1;
                if (str.length() > 0) {
                    str = str.substring(0, str.length() - 1);// son karakteri sil
                }
            }
        }
    }

    public ArrayList<String> suggestWords(String prefix){
        TrieNode current = root;
        int path = 0;
        char c;
        ArrayList<String> suggestedWords = new ArrayList<String>();

        for (int i = 0; i < prefix.length(); i++) {
            c = prefix.charAt(i); // prefixin her bir harfi
            path = c - 'a';

            if(current.children[path] != null){ // Yani bir sonraki harfi var
                current = current.children[path];
            }else{
                return suggestedWords;
            }
        }

        if(hasChild(current) == false) return suggestedWords;


        path = 0; // Pathi 0 lama sebebimiz prefixin son harfinden itibaren başlamasın diye
        // prefixin sonundayız artık önerilere başlayabiliriz
        while(true){

            if (current.children[path] != null) {// alt seviyeye in
                current.index = path;
                prefix += (char) (path + 'a'); // İndeksi tekrar harfe çevir
                current = current.children[path];
                path = 0;
                if (current.isEndOfWord) {
                    //System.out.println(prefix);
                    suggestedWords.add(prefix);
                }
                continue;
            }
            path++;
            while (path > current.children.length-1) {
                if (current == root)
                    return suggestedWords;
                current = current.parent;
                path = current.index+1;
                if (prefix.length() > 0) {
                    prefix = prefix.substring(0, prefix.length() - 1);// son karakteri sil
                }
            }
        }
    }

    public void cleanTree(){
        int wordNum = 0, nodeNum = 0,path = 0;
        TrieNode current = root;
        
        while(true){
            if(current.children[path] != null){ // En dibe inmesi için
                current.index = path;
                current = current.children[path];
                path = 0;
                if(current.isEndOfWord){
                    wordNum++ ;
                }
            }else{
                path ++;
                while(path > current.children.length-1){
                    if(current == root){
                        root=null;
                        System.out.println("Bütün tree temizlendi. Node sayısı: "+ nodeNum + " Word sayısı: " + wordNum);
                        return;
                    }
                    current = current.parent;
                    path = current.index + 1;
                    current.children[current.index] = null;
                    nodeNum ++;
                }
            }
        }
    }



}