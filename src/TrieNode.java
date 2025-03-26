public class TrieNode {
    TrieNode[] children; // Çocuk düğümleri (a-z harfleri için 26 boyutlu dizi)
    boolean isEndOfWord; // Bu düğüm bir kelimenin sonu mu?
    TrieNode parent;
    int index;

    public TrieNode(TrieNode parentNode) {
        this.children = new TrieNode[26]; // Başlangıçta her harf için null
        this.isEndOfWord = false; // Başlangıçta kelime bitişi değil
        this.parent=parentNode;
        index=0;
    }

    
}
