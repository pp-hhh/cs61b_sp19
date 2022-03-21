import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private node root;

    private class node{
        private node left, right;
        private K key;
        private V value;
        private int size; //number of nodes in subtree

        public node(K k, V v, int size){
            key = k;
            value = v;
            this.size = size;
        }
    }

    public BSTMap(){
    }

    @Override
    public void clear(){
        root = null;
    }

    @Override
    public boolean containsKey(K key){
        if(key == null){
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    @Override
    public V get(K key){
        return get(root, key);
    }

    private V get(node n, K k){
        if(k == null){
            throw new IllegalArgumentException();
        }
        if(n == null){
            return null;
        }
        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            return get(n.left, k);
        }else if(cmp > 0){
            return get(n.right, k);
        }else {
            return n.value;
        }
    }

    @Override
    public int size(){ return size(root); }

    private int size(node x){
        if(x == null){
            return 0;
        }else{
            return x.size;
        }
    }

    @Override
    public void put(K key, V value){
        if(key == null){
            throw new IllegalArgumentException();
        }
        root = put(root, key, value);
    }

    private node put(node x, K key, V value){
        if(x == null){
            return new node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.left = put(x.left,key,value);
        }else if(cmp > 0){
            x.right = put(x.right, key, value);
        }else{
            x.value = value;
        }
        x.size = size(x.right) + size(x.left) + 1;
        return x;
    }

    @Override
    public Set<K> keySet(){
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key){
        if (!containsKey(key)){
            return null;
        }
        V toRemove = get(key);
        root = remove(root, key);
        return toRemove;
    }

    @Override
    public V remove(K key, V value){
        if(!containsKey(key)){
            return null;
        }
        if(!get(key).equals(value)){
            return null;
        }
        root = remove(root, key);
        return value;
    }

    private node remove(node x, K key){
        if(x == null){
            return null;
        }
        int cmp = key.compareTo(x.key);

        if(cmp < 0){
            x.left = remove(x.left,key);
        }else if(cmp > 0){
            x.right = remove(x.right, key);
        }else{
            if(x.right == null){
                return x.left;
            }
            if(x.left == null){
                return x.right;
            }
            node tmp = x;
            x = min(tmp.right);
            x.right = deleteMin(tmp.right);
            x.left = tmp.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private node min(node x){
        if(x.left == null){
            return x;
        }else{
            return min(x.left);
        }
    }

    private node deleteMin(node x){
        if(x.left == null){
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = size(x.right) + size(x.left) + 1;
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder(){
        printInOrder(root);
    }

    private void printInOrder(node x){
        if(x == null){
            return;
        }
        printInOrder(x.left);
        System.out.println(x.value);
        printInOrder(x.right);
    }
}
