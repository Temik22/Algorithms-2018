package lesson3

import java.lang.IllegalArgumentException
import java.util.SortedSet
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }


    private fun setNode(right: Boolean, ancestor: Node<T>, current: Node<T>) {
        if (right) ancestor.right = current
        else ancestor.left = current
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        /**
         * labor intensity: O(logN)
         * resource intensity: O(logN)
         */
        if (!this.contains(element)) return false
        var current = root ?: return false
        var ancestor = root ?: return false
        var inRightDirection = true
        while (current.value != element) {
            ancestor = current
            if (element > current.value) {
                current = current.right ?: return false
                inRightDirection = true
            } else if (element < current.value) {
                current = current.left ?: return false
                inRightDirection = false
            }
        }
        when {
            current.left == null && current.right == null -> {
                when {
                    current == root -> root = null
                    inRightDirection -> ancestor.right = null
                    else -> ancestor.left = null
                }
            }
            current.left == null -> {
                if (current == root) root = current.right
                else {
                    val right = current.right ?: return false
                    setNode(inRightDirection, ancestor, right)
                }
            }
            current.right == null -> {
                if (current == root) root = current.left
                else {
                    val left = current.left ?: return false
                    setNode(inRightDirection, ancestor, left)
                }
            }
            else -> {
                var minimum = current.right ?: return false
                var ancestorMin = current.right ?: return false
                while (minimum.left != null) {
                    ancestorMin = minimum
                    minimum = minimum.left ?: return false
                }
                when {
                    current == root && ancestorMin == minimum -> {
                        val rootLeft = root!!.left
                        root = minimum
                        minimum.left = rootLeft
                    }
                    current == root && ancestorMin != minimum -> {
                        ancestorMin.left = minimum.right
                        root = minimum
                        minimum.left = current.left
                        minimum.right = current.right
                    }
                    ancestorMin == minimum -> setNode(inRightDirection, ancestor, minimum)
                    else -> {
                        ancestorMin.left = minimum.right
                        minimum.right = current.right
                        minimum.left = current.left
                        setNode(inRightDirection, ancestor, minimum)
                    }
                }
                minimum.left = current.left
            }
        }
        size--
        return true
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private fun findNext(): Node<T>? {
            /**
             * labor intensity: O(logN)
             * resource intensity: O(logN)
             */
            if (size == 0) return null
            val currentNode = current ?: return find(first())
            if (currentNode.value == last()) return null
            if (currentNode.right != null) {
                var successor = currentNode.right ?: throw IllegalArgumentException()
                while (successor.left != null)
                    successor = successor.left ?: throw IllegalArgumentException()
                return successor
            } else {
                var successor = root ?: throw IllegalArgumentException()
                var ancestor = root ?: throw IllegalArgumentException()
                while (ancestor != currentNode) {
                    if (currentNode.value < ancestor.value) {
                        successor = ancestor
                        ancestor = ancestor.left ?: return null
                    } else ancestor = ancestor.right ?: return null
                }
                return successor
            }
        }

        override fun hasNext(): Boolean = findNext() != null

        override fun next(): T {
            current = findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            /**
             * labor intensity: O(logN)
             * resource intensity: O(logN)
             */
            val it = current ?: throw IllegalArgumentException()
            var ancestor = root ?: throw IllegalArgumentException()
            var successor = root ?: throw IllegalArgumentException()
            var inRightDirection = false
            while (successor != current) {
                ancestor = successor
                if (successor.value < it.value) {
                    successor = successor.right ?: throw IllegalArgumentException()
                    inRightDirection = true
                } else {
                    successor = successor.left ?: throw IllegalArgumentException()
                    inRightDirection = false
                }
            }
            when {
                it.left == null && it.right == null -> {
                    when {
                        current == root -> root = null
                        inRightDirection -> ancestor.right = null
                        else -> ancestor.left = null
                    }
                }
                it.left == null -> {
                    when {
                        current == root -> root = it.right
                        inRightDirection -> ancestor.right = it.right
                        else -> ancestor.left = it.right
                    }
                }
                it.right == null -> {
                    when {
                        current == root -> root = it.left
                        inRightDirection -> ancestor.right = it.left
                        else -> ancestor.left = it.left
                    }
                }
                else -> {
                    var min = it.right ?: return
                    var ancestorMin = min
                    while (min.left != null) {
                        ancestorMin = min
                        min = min.left ?: return
                    }
                    when {
                        it == root && ancestorMin == min -> {
                            val rootLeft = root!!.left
                            root = min
                            min.left = rootLeft
                        }
                        it == root && ancestorMin != min -> {
                            ancestorMin.left = min.right
                            root = min
                            min.left = it.left
                            min.right = it.right
                        }
                        ancestorMin == min -> setNode(inRightDirection, ancestor, min)
                        else -> {
                            ancestorMin.left = min.right
                            min.right = it.right
                            min.left = it.left
                            setNode(inRightDirection, ancestor, min)
                        }
                    }
                    min.left = it.left
                }
            }
            size--
            current = findNext()
        }
    }


    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
