package com.diaosichengxuyuan.dsa.datastructure.tree.impl;

/**
 * B+树 只考虑元素值不重复的情况 算法请参考本人写的：https://github.com/diaosichengxuyuan/DataStructureAlgorithm/issues/6
 *
 * @author liuhaipeng
 * @date 2018/11/16
 */
public class BPlusSortTree<E extends Comparable<E>> extends AbstractMultiwaySortTree<E> {

    private Node<E> root;

    private final int rank;

    private final int lower;

    private Node<E> head;

    private int size = 0;

    public BPlusSortTree(int rank) {
        if(rank < 0) {
            throw new IllegalArgumentException("参数必须大于0");
        }
        this.rank = rank;
        this.lower = (int)Math.ceil(rank / 2.0);
    }

    @Override
    public E find(E e) {
        if(e == null) {
            throw new NullPointerException();
        }

        if(root == null) {
            return null;
        }

        //找插入的节点
        Node<E> node = findLeafNode(e);
        if(node == null) {
            return null;
        }

        //二分插入
        Entry<E> entry = node.binaryAccurateSearch(e);
        if(entry == null) {
            return null;
        }

        return entry.element;
    }

    @Override
    public void insert(E e) {
        if(e == null) {
            throw new NullPointerException();
        }

        if(root == null) {
            root = new Node<>();
            Entry<E> entry = new Entry<>(e);
            root.entries[root.n++] = entry;
            entry.belongToNode = root;

            head = root;
            size++;

            return;
        }

        //大于根节点最大元素
        Node<E> currentNode = root;
        if(e.compareTo(currentNode.maxElement()) > 0) {
            while(!currentNode.isLeaf) {
                Entry<E> maxEntry = currentNode.maxEntry();
                maxEntry.element = e;
                currentNode = maxEntry.childNode;
            }
        }

        //二分查找插入节点
        while(!currentNode.isLeaf) {
            currentNode = currentNode.binarySearch(e).childNode;
        }

        //二分插入元素
        currentNode.binaryInsert(new Entry<>(e));

        //超过m
        while(currentNode.isOverflow()) {
            //如果调整根节点，先拷贝当前节点中的最大元素值到父节点形成新的根
            if(currentNode == root) {
                Node<E> oldRoot = root;
                root = new Node<>();
                root.isLeaf = false;

                Entry<E> entry = new Entry<>(oldRoot.maxElement());
                entry.belongToNode = root;
                entry.childNode = oldRoot;
                oldRoot.parentEntry = entry;
                root.entries[root.n++] = entry;
            }

            //以ceil((m+1)/2)元素拆分当前节点为两个节点
            Node<E> node2 = currentNode.split();
            Entry<E> entry = new Entry<>(node2.maxElement());
            entry.childNode = node2;
            node2.parentEntry = entry;
            currentNode.parentEntry.belongToNode.binaryInsert(entry);

            //组装Node之间的链
            if(currentNode.isLeaf) {
                if(currentNode == head) {
                    node2.rightBrother = currentNode;
                    currentNode.leftBrother = node2;
                    head = node2;
                } else {
                    Node<E> leftNode = currentNode.leftBrother;
                    leftNode.rightBrother = node2;
                    node2.leftBrother = leftNode;
                    currentNode.leftBrother = node2;
                    node2.rightBrother = currentNode;
                }
            }

            //递归判断父节点
            currentNode = currentNode.parentEntry.belongToNode;
        }

        size++;
    }

    @Override
    public void delete(E e) {
        if(e == null) {
            throw new NullPointerException();
        }

        if(root == null) {
            return;
        }

        //while循环找到要删除的元素所属的节点
        Node<E> node = findLeafNode(e);
        if(node == null) {
            return;
        }

        //用倒数第二大的元素替换掉父节点中的元素
        Node<E> currentNode = node;
        E currentElement = e;
        if(currentElement.compareTo(currentNode.maxElement()) == 0) {
            currentElement = currentNode.entries[currentNode.n - 2].element;
            Entry<E> currentEntry = currentNode.parentEntry;
            currentEntry.element = currentElement;
            while(currentEntry.belongToNode != root && currentEntry == currentEntry.belongToNode.maxEntry()) {
                currentEntry = currentEntry.belongToNode.parentEntry;
                currentEntry.element = currentElement;
            }
        }

        //在节点中删除元素
        node.binaryDelete(e);

        while(node != root && node.isInsufficient()) {
            //左兄弟借元素
            if(node.leftBrother != null && node.leftBrother.isEnough()) {
                Entry<E> maxEntry = node.leftBrother.maxEntry();
                node.leftBrother.entries[node.leftBrother.n - 1] = null;
                node.leftBrother.n--;
                System.arraycopy(node.entries, 0, node.entries, 1, node.n);
                node.n++;
                node.entries[0] = maxEntry;
                maxEntry.belongToNode = node;

                node.leftBrother.parentEntry.element = node.leftBrother.maxElement();
            }
            //右兄弟借元素
            else if(node.rightBrother != null && node.rightBrother.isEnough()) {
                Entry<E> minEntry = node.rightBrother.minEntry();
                System.arraycopy(node.rightBrother.entries, 1, node.rightBrother.entries, 0, node.rightBrother.n - 1);
                node.rightBrother.entries[node.rightBrother.n - 1] = null;
                node.rightBrother.n--;
                node.entries[node.n++] = minEntry;
                minEntry.belongToNode = node;

                node.parentEntry.element = node.maxElement();
            }
            //跟左兄弟合并
            else if(node.leftBrother != null) {
                System.arraycopy(node.entries, 0, node.entries, node.leftBrother.n, node.leftBrother.n);
                node.n = node.n + node.leftBrother.n;
                System.arraycopy(node.leftBrother.entries, 0, node.entries, 0, node.leftBrother.n);
                for(int i = 0; i < node.leftBrother.n; i++) {
                    node.entries[i].belongToNode = node;
                }

                node.leftBrother.parentEntry.belongToNode.binaryDelete(node.leftBrother.parentEntry.element);
                node.leftBrother.n = 0;
                node.leftBrother.entries = null;
                node.leftBrother.parentEntry = null;

                //修改链
                if(node.isLeaf) {
                    if(node.leftBrother == head) {
                        node.leftBrother.rightBrother = null;
                        node.leftBrother = null;
                        head = node;
                    } else {
                        Node<E> leftBrother = node.leftBrother;
                        Node<E> leftLeftBrother = node.leftBrother.leftBrother;
                        leftLeftBrother.rightBrother = node;
                        node.leftBrother = leftLeftBrother;
                        leftBrother.leftBrother = null;
                        leftBrother.rightBrother = null;
                    }

                    node = node.parentEntry.belongToNode;
                }
            }
            //跟右兄弟合并
            else {
                System.arraycopy(node.rightBrother.entries, 0, node.rightBrother.entries, node.n, node.n);
                node.rightBrother.n = node.rightBrother.n + node.n;
                System.arraycopy(node.entries, 0, node.rightBrother.entries, 0, node.n);
                for(int i = 0; i < node.n; i++) {
                    node.rightBrother.entries[i].belongToNode = node.rightBrother;
                }

                node.parentEntry.belongToNode.binaryDelete(node.parentEntry.element);
                node.n = 0;
                node.entries = null;
                node.parentEntry = null;

                //修改链
                if(node.isLeaf) {
                    if(node == head) {
                        node.rightBrother = head;
                        node.rightBrother.leftBrother = null;
                        node.rightBrother = null;
                    } else {
                        Node<E> leftBrother = node.leftBrother;
                        leftBrother.rightBrother = node.rightBrother;
                        node.rightBrother.leftBrother = leftBrother;
                        node.leftBrother = null;
                        node.rightBrother = null;
                    }
                }

                node = node.rightBrother.parentEntry.belongToNode;
            }
        }

        //可能存在问题，比如删除一个不存在的元素
        size--;
    }

    @Override
    public String levelPrint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String treeStructurePrint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        root = null;
        head = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String sortPrint() {
        StringBuilder builder = new StringBuilder();

        if(head == null) {
            return builder.toString();
        }

        Node<E> currentNode = head;
        do {
            for(int i = 0; i < currentNode.n; i++) {
                builder.append(currentNode.entries[i].element + " ");
            }
            currentNode = currentNode.rightBrother;
        } while(currentNode != null);

        return builder.toString().trim();
    }

    /**
     * 树节点
     */
    private class Node<E extends Comparable<E>> {

        /**
         * 元素个数
         */
        private int n;

        /**
         * 元素数组
         */
        private Entry<E>[] entries = new Entry[rank + 1];

        /**
         * 父元素
         */
        private Entry<E> parentEntry;

        /**
         * 是否为叶子
         */
        private boolean isLeaf = true;

        /**
         * 左兄弟
         */
        private Node<E> leftBrother;

        /**
         * 右兄弟
         */
        private Node<E> rightBrother;

        /**
         * 元素溢出
         */
        private boolean isOverflow() {
            return n > rank;
        }

        /**
         * 元素不足
         */
        private boolean isInsufficient() {
            return n < lower;
        }

        /**
         * 元素充足
         */
        private boolean isEnough() {
            return n > lower;
        }

        private Entry<E> maxEntry() {
            return entries[n - 1];
        }

        private Entry<E> minEntry() {
            return entries[0];
        }

        private E maxElement() {
            return entries[n - 1].element;
        }

        private E minElement() {
            return entries[0].element;
        }

        /**
         * 二分查找到属于哪个Entry范围
         */
        private Entry<E> binarySearch(E e) {
            if(e.compareTo(minElement()) < 0) {
                return entries[0];
            }

            if(e.compareTo(maxElement()) > 0) {
                return null;
            }

            int left = 0;
            int right = n - 1;

            while(left < right) {
                int mid = (int)Math.floor((left + right) / 2.0);
                Entry<E> midEntry = entries[mid];
                if(e.compareTo(midEntry.element) == 0) {
                    return midEntry;
                } else if(e.compareTo(midEntry.element) > 0) {
                    left = mid;
                } else {
                    right = mid;
                }

                //当最后出现left下标为i，right=i+1时，如果要查找的element位于它两个之间，mid一直等于i+1不会结束
                if(right - left == 1) {
                    return entries[right];
                }
            }

            return null;
        }

        /**
         * 二分精确查找对应的元素
         */
        private Entry<E> binaryAccurateSearch(E e) {
            if(e.compareTo(minElement()) < 0) {
                return null;
            }

            if(e.compareTo(maxElement()) > 0) {
                return null;
            }

            int left = 0;
            int right = n - 1;

            Entry<E> entry = null;
            while(left < right) {
                int mid = (int)Math.floor((left + right) / 2.0);
                Entry<E> midEntry = entries[mid];
                if(e.compareTo(midEntry.element) == 0) {
                    entry = midEntry;
                    break;
                } else if(e.compareTo(midEntry.element) > 0) {
                    left = mid;
                } else {
                    right = mid;
                }

                //当最后出现left下标为i，right=i+1时，如果要查找的element位于它两个之间，mid一直等于i+1不会结束
                if(right - left == 1) {
                    if(e.compareTo(entries[left].element) == 0) {
                        entry = entries[left];
                    } else if(e.compareTo(entries[right].element) == 0) {
                        entry = entries[right];
                    }
                    break;
                }
            }

            return entry;
        }

        /**
         * 二分删除
         */
        private void binaryDelete(E e) {
            if(e.compareTo(minElement()) < 0) {
                return;
            }

            if(e.compareTo(maxElement()) > 0) {
                return;
            }

            int left = 0;
            int right = n - 1;

            int deleteIndex = -1;
            while(left < right) {
                int mid = (int)Math.floor((left + right) / 2.0);
                Entry<E> midEntry = entries[mid];
                if(e.compareTo(midEntry.element) == 0) {
                    deleteIndex = mid;
                    break;
                } else if(e.compareTo(midEntry.element) > 0) {
                    left = mid;
                } else {
                    right = mid;
                }

                //当最后出现left下标为i，right=i+1时，如果要查找的element位于它两个之间，mid一直等于i+1不会结束
                if(right - left == 1) {
                    if(e.compareTo(entries[left].element) == 0) {
                        deleteIndex = left;
                    } else if(e.compareTo(entries[right].element) == 0) {
                        deleteIndex = right;
                    }
                    break;
                }
            }

            if(deleteIndex != -1) {
                System.arraycopy(entries, deleteIndex + 1, entries, deleteIndex, n - (deleteIndex + 1));
                entries[n - 1] = null;
                n--;
            }
        }

        /**
         * 二分插入
         */
        private void binaryInsert(Entry<E> entry) {
            if(entry.element.compareTo(minElement()) < 0) {
                move(0, entry);
                return;
            }

            if(entry.element.compareTo(maxElement()) > 0) {
                entries[n++] = entry;
                entry.belongToNode = this;
                return;
            }

            int left = 0;
            int right = n - 1;

            while(left < right) {
                int mid = (int)Math.floor((left + right) / 2.0);
                Entry<E> midEntry = entries[mid];
                if(entry.element.compareTo(midEntry.element) == 0) {
                    return;
                } else if(entry.element.compareTo(midEntry.element) > 0) {
                    left = mid;
                } else {
                    right = mid;
                }

                //当最后出现left下标为i，right=i+1时，如果要查找的element位于它两个之间，mid一直等于i+1不会结束
                if(right - left == 1) {
                    move(right, entry);
                    return;
                }
            }
        }

        private void move(int index, Entry<E> entry) {
            System.arraycopy(entries, index, entries, index + 1, n - index);
            entries[index] = entry;
            n++;
            entry.belongToNode = this;
        }

        /**
         * 当前Node{3,5,7,9,10}，将Node拆分为Node1{3,5,7}和Node2{9,10}，Node2替换掉当前Node，Node1返回
         */
        private Node<E> split() {
            Node<E> node1 = new Node<>();
            node1.isLeaf = this.isLeaf;
            //下标要减1
            int midIndex = (int)Math.ceil((rank + 1) / 2.0) - 1;
            System.arraycopy(this.entries, 0, node1.entries, 0, midIndex + 1);
            node1.n = midIndex + 1;
            //node1的孩子
            for(int i = 0; i <= midIndex; i++) {
                node1.entries[i].belongToNode = node1;
            }
            //node2的清理
            System.arraycopy(this.entries, midIndex + 1, this.entries, 0, n - (midIndex + 1));
            for(int i = n - (midIndex + 1); i < n; i++) {
                this.entries[i] = null;
            }
            this.n = n - (midIndex + 1);

            return node1;
        }
    }

    /**
     * 元素
     */
    private class Entry<E extends Comparable<E>> {

        /**
         * 元素值
         */
        private E element;

        /**
         * 当前元素所属的节点
         */
        private Node<E> belongToNode;

        /**
         * 孩子节点
         */
        private Node<E> childNode;

        private Entry(E element) {
            this.element = element;
        }
    }

    private Node<E> findLeafNode(E e) {
        Node<E> currentNode = root;
        while(!currentNode.isLeaf) {
            currentNode = currentNode.binarySearch(e).childNode;
        }

        return currentNode;
    }
}
