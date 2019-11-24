package com.omd.fp.comonads.samples

private[samples] trait ZipperSyntax {
  implicit final def toZipper[F[_]: Zip, A](fa: F[A]): Zippable[F, A] = new Zippable[F, A](fa)

  implicit final def toZipperOps[F[_]: Zip, A](z: Zipper[F, A]): ZipperOps[F, A] = new ZipperOps[F, A](z)
}

final class Zippable[F[_], A](val fa: F[A]) extends AnyVal {
  def zipper(implicit Z: Zip[F]): Zipper[F, A] = Z.zip(fa)
}

final class ZipperOps[F[_], A](val z: Zipper[F, A]) extends AnyVal {
  def toLeft(implicit Z: Zip[F]): Zipper[F, A]  = Z.zipLeft(z)
  def toRight(implicit Z: Zip[F]): Zipper[F, A] = Z.zipRight(z)
}
