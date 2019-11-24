package com.omd.fp.comonads.samples

import cats.Endo

trait Zip[F[_]] {
  def zipLeft[A]: Endo[Zipper[F, A]]
  def zipRight[A]: Endo[Zipper[F, A]]
}
