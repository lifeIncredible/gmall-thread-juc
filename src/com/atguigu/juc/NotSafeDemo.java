package com.atguigu.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1 ArrayList线程不安全故障现象
 *      java.util.ConcurrentModificationException(并发修改异常)
 * 为什么会报这个异常源码分析?
 *  报错后异常下面会显示
 *     ① at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909) 进入源码发现
           if (modCount != expectedModCount)
               throw new ConcurrentModificationException();

            modCount与期望的ModCount不相等所以抛了个并发修改异常


         ② at java.util.ArrayList$Itr.next(ArrayList.java:859)

             public E next() {
                 checkForComodification();
                 int i = cursor;
                 if (i >= size)
                 throw new NoSuchElementException();
                 Object[] elementData = ArrayList.this.elementData;
                 if (i >= elementData.length)
                     throw new ConcurrentModificationException();
                     cursor = i + 1;
                     return (E) elementData[lastRet = i];
             }

       ③ at java.util.AbstractCollection.toString(AbstractCollection.java:461)

                 public String toString() {
                     Iterator<E> it = iterator();
                     if (! it.hasNext())
                     return "[]";

                     StringBuilder sb = new StringBuilder();
                     sb.append('[');
                     for (;;) {
                         E e = it.next();
                         sb.append(e == this ? "(this Collection)" : e);
                         if (! it.hasNext())
                         return sb.append(']').toString();
                         sb.append(',').append(' ');
                     }
                 }

 *
 *  2 导致原因总结
 *         第一点：add方法上没加锁
 *         第二点：添加数据时toString遍历的时候读写不一致 if（modCount != expectedModCount）
 *  3 解决办法
 *        3.1 使用Vector()集合
 *        3.2 Collections.synchronizedList(new ArrayList<>());
 *        3.3 new CopyOnWriteArrayList<>(); 写时复制技术
 *
 *  4 优化建议
 *
 */
public class NotSafeDemo {
    public static void main(String[] args) {

        List<String> list = Collections.synchronizedList(new ArrayList<>()); //new ArrayList<>();//new CopyOnWriteArrayList<>(); //new Vector<>();
       for (int i = 1; i <=30 ; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
/*
*   为什么Vector集合安全?   因为他在添加数据时的方法上加锁了
     那为什么jdk1.2就有Vector集合了还要出ArrayList集合，虽然Vector集合安全了不会发生数据的错乱
     但是并发性下降。

      public synchronized boolean add(E e) {
            modCount++;
            ensureCapacityHelper(elementCount + 1);
            elementData[elementCount++] = e;
            return true;
        }



  为什么说ArrayList集合不安全？   因为它在向集合中添加数据时方法上面没加锁
  ArrayList与Vector正好相反，所以ArrayList性能好，但是不安全。

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }


   Collections.synchronizedList(集合);
  可以把一个不安全的集合包装成安全的集合


写时复制
    CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，
    不会直接往当前容器Object[]添加,而是先将当前容器Object[]进行copy，
    复制出一个新的容器Object[] newElements,然后新的容器Object[]newElements 里添加元素，
     添加完元素之后，再讲原容器的引用指向新的容器setArray(newElements);
    这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。

CopyOnWriteArrayList底层add方法源码分析：
       public boolean add(E e) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                Object[] elements = getArray();
                int len = elements.length;
                Object[] newElements = Arrays.copyOf(elements, len + 1);
                newElements[len] = e;
                setArray(newElements);
                return true;
            } finally {
                lock.unlock();
            }
        }

* */