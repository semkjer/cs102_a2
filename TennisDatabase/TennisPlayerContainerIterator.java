


// Giuseppe Turini
// CS-102, Spring 2020
// Assignment 2

package TennisDatabase;

import java.util.Iterator;

// Class implementing an iterator for the TennisPlayersContainer class (a BST of tennis players).
public class TennisPlayerContainerIterator implements Iterator<TennisPlayerContainerNode> {

   private QueueArrayBased<TennisPlayerContainerNode> queue; // Internal queue to store the BST nodes in the same order they are visited.
   private TennisPlayerContainerNode bstRootNode; // Reference (entry point) to the hierarchy of nodes in the BST.
   
   // Constructor.
   TennisPlayerContainerIterator( TennisPlayerContainerNode currRoot ) {
      this.queue = new QueueArrayBased<TennisPlayerContainerNode>();
      this.bstRootNode = currRoot;
   }
   
   // Preorder traversal to fill up the internal queue (part A).
   public void setPreorder() {
      this.queue = new QueueArrayBased<TennisPlayerContainerNode>();
      setPreorderRec( this.bstRootNode ); // Perform preorder traversal on root node (recursively).
   }
   
   // Preorder traversal to fill up the internal queue (part B).
   private void setPreorderRec( TennisPlayerContainerNode currRoot ) {
      if( currRoot != null ) { // Check if current node exists.
         this.queue.enqueue( currRoot ); // Insert current node/player in internal queue.
         setPreorderRec( currRoot.getLeftChild() ); // Perform preorder traversal on left subtree.
         setPreorderRec( currRoot.getRightChild() ); // Perform preorder traversal on right subtree.
      }
   }
      
   // Postorder traversal to fill up the internal queue (part A).
   public void setPostorder() {
      this.queue = new QueueArrayBased<TennisPlayerContainerNode>();
      setPostorderRec( this.bstRootNode ); // Perform postorder traversal on root node (recursively).
   }
   
   // Postorder traversal to fill up the internal queue (part B).
   private void setPostorderRec( TennisPlayerContainerNode currRoot ) {
      if( currRoot != null ) { // Check if current node exists.
         setPostorderRec( currRoot.getLeftChild() ); // Perform postorder traversal on left subtree.
         setPostorderRec( currRoot.getRightChild() ); // Perform postorder traversal on right subtree.
         this.queue.enqueue( currRoot ); // Insert current node/player in internal queue.
      }
   }
   
   // Inorder traversal to fill up the internal queue (part A).
   public void setInorder() {
      this.queue = new QueueArrayBased<TennisPlayerContainerNode>();
      setInorderRec( this.bstRootNode ); // Perform inorder traversal on root node (recursively).
   }
   
   // Inorder traversal to fill up the internal queue (part B).
   private void setInorderRec( TennisPlayerContainerNode currRoot ) {
      if( currRoot != null ) { // Check if current node exists.
         setInorderRec( currRoot.getLeftChild() ); // Perform inorder traversal on left subtree.
         this.queue.enqueue( currRoot ); // Insert current node/player in internal queue.
         setInorderRec( currRoot.getRightChild() ); // Perform inorder traversal on right subtree.
      }
   }
   
   // Reverse-inorder traversal to fill up the internal queue (part A).
   public void setReverseInorder() {
      this.queue = new QueueArrayBased<TennisPlayerContainerNode>();
      setReverseInorderRec( this.bstRootNode ); // Perform reverse-inorder traversal on root node (recursively).
   }
   
   // Reverse-inorder traversal to fill up the internal queue (part B).
   private void setReverseInorderRec( TennisPlayerContainerNode currRoot ) {
      if( currRoot != null ) { // Check if current node exists.
         setReverseInorderRec( currRoot.getRightChild() ); // Perform reverse-inorder traversal on right subtree.
         this.queue.enqueue( currRoot ); // Insert current node/player in internal queue.
         setReverseInorderRec( currRoot.getLeftChild() ); // Perform reverse-inorder traversal on left subtree.
      }
   }
   
   // Method to check if the iterator can provide another item.
   public boolean hasNext() { return ! this.queue.isEmpty(); }
   
   // Method to extract the next item in the iteration.
   public TennisPlayerContainerNode next() { return this.queue.dequeue(); }
   
}


