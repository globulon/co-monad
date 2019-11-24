package com.omd.fp.comonads.samples

import cats.Endo

private[samples] trait ZipperInstances {
  implicit val lazyZip: Zip[LazyList] = new Zip[LazyList] {
    override def zipLeft[A]: Endo[Zipper[LazyList, A]] = {
      case Zipper(l, a, r) => Zipper(a #:: l, r.head, r.tail)
    }

    override def zipRight[A]: Endo[Zipper[LazyList, A]] = {
      case Zipper(l, a, r) => Zipper(l.tail, l.head, a #:: r)
    }
  }
}
