package com.omd.fp.comonads.samples

import cats.Comonad
import syntax._
import instances._

private[samples] trait ComonadInstances {
  implicit final def lazyCommonad: Comonad[LazyList] = new Comonad[LazyList] {
    override def extract[A](as: LazyList[A]): A = as.head

    override def coflatMap[A, B](fa: LazyList[A])(f: LazyList[A] => B): LazyList[B] =
      f(fa) #:: coflatMap(fa.tail)(f)

    override def map[A, B](fa: LazyList[A])(f: A => B): LazyList[B] = fa.map(f)
  }

  implicit final def lazyListZComonad: Comonad[Zipper[LazyList, *]] = new Comonad[Zipper[LazyList, *]] {
    override def extract[A](z: Zipper[LazyList, A]): A = z.focus

    override def coflatMap[A, B](fa: Zipper[LazyList, A])(f: Zipper[LazyList, A] => B): Zipper[LazyList, B] =
      Zipper(
        Comonad[LazyList].coflatMap(fa.left)(l => f(l.zipper)),
        f(fa),
        Comonad[LazyList].coflatMap(fa.right)(l => f(l.zipper))
      )

    override def map[A, B](fa: Zipper[LazyList, A])(f: A => B): Zipper[LazyList, B] =
      Zipper(fa.left.map(f), f(fa.focus), fa.right.map(f))
  }
}
