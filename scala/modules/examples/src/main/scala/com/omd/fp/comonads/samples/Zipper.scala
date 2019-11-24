package com.omd.fp.comonads.samples

final case class Zipper[F[_], A](left: F[A], focus: A, right: F[A])
