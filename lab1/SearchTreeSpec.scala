import org.scalatest._
import Lab1._

class SearchTreeSpec extends FlatSpec {

  // Have a few trees pre-made to test against, both valid and invalid

  val t1 = Node(Empty, 2, Empty)
  val t2 = Node(t1, 4, Empty)
  val t3 = Node(Empty, 4, t2)
  val t4 = Node(t1, 4, Node(Empty, 4, Node(Empty, 5, Empty)))
  val t5 = Node(Empty, 2, t1)
  val t6 = Node(Empty, 4, Empty)
  val t7 = Node(Empty, 2, Node(Empty, 4, Empty))
  val t8 = Node(Node(Empty, 1, Empty), 2, Node(Empty, 3, Empty))
  val t9 = Node(Empty, 2, Node(Empty, 3, Empty))
  val t10 = Node(Node(Empty, 1, Empty), 2, Empty)
  val t11 = Node(Node(Empty, 1, Empty), 3, Empty)

  // repOk

  "repOk" should "ensure that a SearchTree is properly ordered" in {
    assert(repOk(Empty))
    assert(repOk(t1))
    assert(repOk(t2))
    assert(!repOk(t3))
    assert(repOk(t4))
    assert(repOk(t5))
  }

  // insertion

  "insert" should "insert numbers as leaves in SearchTrees at the proper position" in {
    assert(insert(Empty, 2) === t1)
    assert(insert(t1, 2) === t5)
    assert(insert(t6, 2) === t2)
    assert(insert(t1, 4) === t7)
  }

  // Some more testing code that uses the Scala List libray.
  // The function 'treeFromList' inserts all the elements in the list 
  // into the tree, using foldLeft

  def treeFromList(l: List[Int]): SearchTree = 
    l.foldLeft(Empty: SearchTree)(insert)

  "insert" should "work in a foldLeft to build a list list from a tree" in {
    // verify `treeFromList` works properly by checking
    // the invariant with `repOk`
    val list = List(0,2,4,3,1,2)
    assert(repOk(treeFromList(list)) === true)
  }

  // deleteMin

  "deleteMin" should "remove the smallest value from a tree, and provide the resulting tree" in {
    assert(deleteMin(t7) === (t6, 2))
    assert(deleteMin(t1) === (Empty, 2))
    assert(deleteMin(t5) === (t1, 2))
    assert(deleteMin(t4) === (Node(Empty, 4, Node(Empty, 4, Node(Empty, 5, Empty))), 2))
  }

  // delete

  "delete" should "remove a given value from a tree, if present" in {
    assert(delete(t1, 2) === Empty)
    assert(delete(t5, 2) === t1)
    assert(delete(t8, 1) === t9)
    assert(delete(t8, 3) === t10)
    assert(delete(t8, 2) === t11)
    assert(delete(t8, 4) === t8)
    // use treeFromList to build a tree, then delete
    //throw new UnsupportedOperationException
    val list = List(1,2,3,4,5)
    //println(treeFromList(list))
    assert(delete(treeFromList(list),3) === (Node(Empty, 1 ,Node(Empty, 2, Node(Empty, 4, Node(Empty, 5, Empty))))))
  }

}
