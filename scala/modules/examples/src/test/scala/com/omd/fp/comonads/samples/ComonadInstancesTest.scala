package com.omd.fp.comonads.samples

import cats.Comonad
import com.omd.fp.comonads.samples.instances._
import org.scalatest.{ Matchers, WordSpecLike }

final class ComonadInstancesTest extends WordSpecLike with Matchers {
  "Lazy list comonad" should {
    "extract head properly" in {
      Comonad[LazyList].extract(nat) should be(1.0)
    }

    "map properly" in {
      Comonad[LazyList].map(nat)(_ * 2).take(3) should be (List(2, 4, 6))
    }

    "coflatmap properly" in {
      Comonad[LazyList].coflatMap(nat)(avg(3)).take(3).toList should be(List(2.0, 3.0, 4.0))
    }
  }

  private def avg[N](n: Int)(implicit F: Fractional[N]): LazyList[N] => N =
    ns => F.div(ns.take(n).sum[N], F.fromInt(n))

  private val nat: LazyList[Double] = 1 #:: nat.map(_ + 1)
}
