package com.omd.fp.comonads.samples

import cats.Comonad
import com.omd.fp.comonads.samples.syntax._

private[samples] trait ComonadInstances {
  implicit final def lazyCommonad: Comonad[LazyList] = new Comonad[LazyList] {
    override def extract[A](as: LazyList[A]): A = as.head

    override def coflatMap[A, B](fa: LazyList[A])(f: LazyList[A] => B): LazyList[B] =
      f(fa) #:: coflatMap(fa.tail)(f)

    override def map[A, B](fa: LazyList[A])(f: A => B): LazyList[B] = fa.map(f)
  }

  implicit final def lazyListZComonad[F[_]: Comonad: Zip]: Comonad[Zipper[F, *]] = new Comonad[Zipper[F, *]] {
    override def extract[A](z: Zipper[F, A]): A = z.focus

    override def coflatMap[A, B](fa: Zipper[F, A])(f: Zipper[F, A] => B): Zipper[F, B] =
      Zipper(
        Comonad[F].coflatMap(fa.left)(l => f(l.zipper)),
        f(fa),
        Comonad[F].coflatMap(fa.right)(l => f(l.zipper))
      )

    override def map[A, B](fa: Zipper[F, A])(f: A => B): Zipper[F, B] =
      Zipper(Comonad[F].map(fa.left)(f), f(fa.focus), Comonad[F].map(fa.right)(f))
  }
}
