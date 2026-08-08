class Trie {

    private static class TrieNode {
        private final TrieNode[] children = new TrieNode[26];
        private boolean isEnd = false;
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }
    
    // Inserts a word into the trie
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }
    
    // Returns true if the word is in the trie
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd;
    }
    
    // Returns true if there is any word in the trie that starts with the given prefix
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    // Helper method to traverse the trie for a given string
    private TrieNode searchPrefix(String str) {
        TrieNode node = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */