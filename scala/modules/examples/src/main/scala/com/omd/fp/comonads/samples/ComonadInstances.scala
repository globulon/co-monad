package com.omd.fp.comonads.samples

import cats.Comonad

private[samples] trait ComonadInstances {
  implicit final def lazyCommonad: Comonad[LazyList] = new Comonad[LazyList] {
    override def extract[A](as: LazyList[A]): A = as.head

    override def coflatMap[A, B](fa: LazyList[A])(f: LazyList[A] => B): LazyList[B] =
      f(fa) #:: coflatMap(fa.tail)(f)

    override def map[A, B](fa: LazyList[A])(f: A => B): LazyList[B] = fa.map(f)
  }
}
