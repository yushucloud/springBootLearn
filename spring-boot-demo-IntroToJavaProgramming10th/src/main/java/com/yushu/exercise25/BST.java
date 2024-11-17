//package com.yushu.exercise25;//
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class BST<E extends Comparable<E>> extends AbstractTree<E> implements Cloneable {
//    protected BST$TreeNode<E> root;
//    protected int size = 0;
//
//    public BST() {
//    }
//
//    public BST(E[] objects) {
//        for(int i = 0; i < objects.length; ++i) {
//            this.insert(objects[i]);
//        }
//
//    }
//
//    public boolean search(E e) {
//        BST$TreeNode<E> current = this.root;
//
//        while(current != null) {
//            if (e.compareTo(current.element) < 0) {
//                current = current.left;
//            } else {
//                if (e.compareTo(current.element) <= 0) {
//                    return true;
//                }
//
//                current = current.right;
//            }
//        }
//
//        return false;
//    }
//
//    public boolean insert(E e) {
//        if (this.root == null) {
//            this.root = this.createNewNode(e);
//        } else {
//            BST$TreeNode<E> parent = null;
//            BST$TreeNode<E> current = this.root;
//
//            while(current != null) {
//                if (e.compareTo(current.element) < 0) {
//                    parent = current;
//                    current = current.left;
//                } else {
//                    if (e.compareTo(current.element) <= 0) {
//                        return false;
//                    }
//
//                    parent = current;
//                    current = current.right;
//                }
//            }
//
//            if (e.compareTo(parent.element) < 0) {
//                parent.left = this.createNewNode(e);
//            } else {
//                parent.right = this.createNewNode(e);
//            }
//        }
//
//        ++this.size;
//        return true;
//    }
//
//    protected BST$TreeNode<E> createNewNode(E e) {
//        return new BST$TreeNode(e);
//    }
//
//    public void inorder() {
//        this.inorder(this.root);
//    }
//
//    protected void inorder(BST$TreeNode<E> root) {
//        if (root != null) {
//            this.inorder(root.left);
//            System.out.print(root.element + " ");
//            this.inorder(root.right);
//        }
//    }
//
//    public void postorder() {
//        this.postorder(this.root);
//    }
//
//    protected void postorder(BST$TreeNode<E> root) {
//        if (root != null) {
//            this.postorder(root.left);
//            this.postorder(root.right);
//            System.out.print(root.element + " ");
//        }
//    }
//
//    public void preorder() {
//        this.preorder(this.root);
//    }
//
//    protected void preorder(BST$TreeNode<E> root) {
//        if (root != null) {
//            System.out.print(root.element + " ");
//            this.preorder(root.left);
//            this.preorder(root.right);
//        }
//    }
//
//    public int getSize() {
//        return this.size;
//    }
//
//    public BST$TreeNode<E> getRoot() {
//        return this.root;
//    }
//
//    public ArrayList<BST$TreeNode<E>> path(E e) {
//        ArrayList<BST$TreeNode<E>> list = new ArrayList();
//        BST$TreeNode<E> current = this.root;
//
//        while(current != null) {
//            list.add(current);
//            if (e.compareTo(current.element) < 0) {
//                current = current.left;
//            } else {
//                if (e.compareTo(current.element) <= 0) {
//                    break;
//                }
//
//                current = current.right;
//            }
//        }
//
//        return list;
//    }
//
//    public boolean delete(E e) {
//        BST$TreeNode<E> parent = null;
//        BST$TreeNode<E> current = this.root;
//
//        while(current != null) {
//            if (e.compareTo(current.element) < 0) {
//                parent = current;
//                current = current.left;
//            } else {
//                if (e.compareTo(current.element) <= 0) {
//                    break;
//                }
//
//                parent = current;
//                current = current.right;
//            }
//        }
//
//        if (current == null) {
//            return false;
//        } else {
//            if (current.left == null) {
//                if (parent == null) {
//                    this.root = current.right;
//                } else if (e.compareTo(parent.element) < 0) {
//                    parent.left = current.right;
//                } else {
//                    parent.right = current.right;
//                }
//            } else {
//                BST$TreeNode<E> parentOfRightMost = current;
//
//                BST$TreeNode rightMost;
//                for(rightMost = current.left; rightMost.right != null; rightMost = rightMost.right) {
//                    parentOfRightMost = rightMost;
//                }
//
//                current.element = rightMost.element;
//                if (parentOfRightMost.right == rightMost) {
//                    parentOfRightMost.right = rightMost.left;
//                } else {
//                    parentOfRightMost.left = rightMost.left;
//                }
//            }
//
//            --this.size;
//            return true;
//        }
//    }
//
//    public Iterator iterator() {
//        return this.inorderIterator();
//    }
//
//    public Iterator inorderIterator() {
//        return new BST$InorderIterator(this);
//    }
//
//    public void clear() {
//        this.root = null;
//        this.size = 0;
//    }
//
//    public Object clone() {
//        BST<E> tree1 = new BST();
//        this.copy(this.root, tree1);
//        return tree1;
//    }
//
//    private void copy(BST$TreeNode<E> root, BST<E> tree) {
//        if (root != null) {
//            tree.insert(root.element);
//            this.copy(root.left, tree);
//            this.copy(root.right, tree);
//        }
//
//    }
//}
