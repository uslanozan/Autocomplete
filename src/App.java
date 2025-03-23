public class App {
    public static void main(String[] args) throws Exception {

        Trie trie = new Trie();
        trie.insert("merhaba");
        trie.insert("cat");
        trie.insert("caterpillar");
        trie.insert("car");
        trie.insert("zort");
        trie.insert("allah");
        trie.displayTrie();
        
    }
}
