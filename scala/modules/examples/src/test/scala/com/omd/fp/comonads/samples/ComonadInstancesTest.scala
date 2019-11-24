package com.omd.fp.comonads.samples

import cats.Comonad
import com.omd.fp.comonads.samples.instances._
import com.omd.fp.comonads.fixture.samples._
import org.scalatest.{ Matchers, WordSpecLike }

final class ComonadInstancesTest extends WordSpecLike with Matchers {
  "Lazy list comonad" should {
    "extract head properly" in {
      Comonad[LazyList].extract(inc) should be(1.0)
    }

    "map properly" in {
      Comonad[LazyList].map(inc)(_ * 2).take(3) should be(List(2, 4, 6))
    }

    "coflatmap properly" in {
      Comonad[LazyList].coflatMap(inc)(avg(3)).take(3).toList should be(List(2.0, 3.0, 4.0))
    }
  }
}
