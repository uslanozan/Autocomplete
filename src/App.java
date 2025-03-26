public class App {
    public static void main(String[] args) throws Exception {

        Trie trie = new Trie();
        /*
        trie.insert("ender");
        trie.insert("end");
        System.out.println(trie.countWords());
        trie.displayTrie();
        trie.delete("ender");
        trie.displayTrie();
        trie.delete("e");
        trie.displayTrie();
        System.out.println(trie.countWords());
         */

         trie.insert("apple");
         trie.insert("apt");
         trie.insert("app");
         trie.insert("banana");
         trie.insert("ban");
         trie.insert("banito");
         trie.insert("banner");
         trie.displayTrie();
         System.out.println("----------");
         //trie.suggestWords("ban");
         System.out.println(trie.suggestWords("ban"));
         for (String word : trie.suggestWords("ap")) {
            System.out.println(word);
         }


    }
}
