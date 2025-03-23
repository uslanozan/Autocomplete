public class Trie {
    private TrieNode root;

    public Trie() { this.root = new TrieNode(); }

    public void insert(String word){
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char nodes = word.charAt(i);
            //int index = nodes - 'a';
            // a harfinin ASCII değeri 97 dir. nodes'daki ASCII değerini çıkararak aslında a-z arasındaki indexini buluruz
            int index = nodes - 97;

            System.out.println(nodes + " has index of " + index);

            if(current.children[index] == null){
                current.children[index] = new TrieNode();
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
        deleteRecursive(root, word, 0);
    }
    
    private boolean deleteRecursive(TrieNode node, String word, int depth) {
        if (node == null) {
            return false; // Kelime bulunamadı
        }
    
        // Kelimenin son harfine ulaşıldığında
        if (depth == word.length()) {
            if (!node.isEndOfWord) {
                return false; // Kelime Trie'de değil
            }
    
            node.isEndOfWord = false; // Kelimeyi sil (artık kelime sonu değil)
    
            // Eğer düğümün çocuğu yoksa, bu düğüm de silinebilir
            return hasNoChildren(node);
        }
    
        // Kelimenin bir sonraki harfine geç
        int index = word.charAt(depth) - 'a';
        boolean shouldDeleteCurrentNode = deleteRecursive(node.children[index], word, depth + 1);
    
        // Eğer çocuk düğüm silinmeli ise, bu düğümü de sil
        if (shouldDeleteCurrentNode) {
            node.children[index] = null; // Çocuk düğümü sil
    
            // Eğer bu düğümün başka çocuğu yoksa ve kelime sonu değilse, bu düğüm de silinebilir
            return hasNoChildren(node) && !node.isEndOfWord;
        }
    
        return false; // Bu düğüm silinmemeli
    }
    
    private boolean hasNoChildren(TrieNode node) {
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                return false; // Çocuk düğüm var
            }
        }
        return true; // Çocuk düğüm yok
    }

    public int countWords(){
        return countRecursive(root);
    }

    private int countRecursive(TrieNode node) {
        if (node == null) return 0; // Eğer düğüm boşsa sayma
    
        int count = node.isEndOfWord ? 1 : 0; // Eğer kelime sonuysa sayacı 1 artır
    
        // Tüm çocuk düğümler için recursive çağrı yap
        for (int i = 0; i < 26; i++) {
            count += countRecursive(node.children[i]); 
        }
    
        return count; // Toplam kelime sayısını döndür
    }
    

    // Trie içindeki kelimeleri yazdıran fonksiyon
    public void displayTrie() {
        displayHelper(root, "");
    }

    // Yardımcı rekürsif fonksiyon
    private void displayHelper(TrieNode node, String word) {
        if (node.isEndOfWord) {
            System.out.println(word); // Eğer kelimenin sonundaysak, ekrana yazdır
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char ch = (char) (i + 'a'); // İndeksi tekrar harfe çevir
                displayHelper(node.children[i], word + ch); // Yeni harfi ekleyerek devam et
            }
        }
    }


    
}