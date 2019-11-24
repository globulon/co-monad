package com.omd.fp.comonads.samples

import cats.Endo

private[samples] trait ZipperInstances {
  implicit val lazyZip: Zip[LazyList] = new Zip[LazyList] {
    override def zipLeft[A]: Endo[Zipper[LazyList, A]] = {
      case z @ Zipper(l, _, _) if l.isEmpty => z
      case Zipper(l, a, r)                  => Zipper(l.tail, l.head, a #:: r)
    }

    override def zipRight[A]: Endo[Zipper[LazyList, A]] = {
      case Zipper(l, a, r) => Zipper(a #:: l, r.head, r.tail)
    }

    override def zip[A]: LazyList[A] => Zipper[LazyList, A] =
      xs => new Zipper[LazyList, A](LazyList.empty, xs.head, xs.tail)
  }
}
