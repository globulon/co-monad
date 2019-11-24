package com.omd.fp.comonads.samples

import cats.Endo

trait Zip[F[_]] {
  def zip[A]: F[A] => Zipper[F, A]
  def zipLeft[A]: Endo[Zipper[F, A]]
  def zipRight[A]: Endo[Zipper[F, A]]
}

object Zip {
  @inline def apply[F[_]](implicit Z: Zip[F]): Zip[F] = Z
}
