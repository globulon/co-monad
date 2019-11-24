package com.omd.fp.comonads.samples

import org.scalatest.{ Matchers, WordSpecLike }
import instances._
import syntax._
import com.omd.fp.comonads.fixture.samples._

final class ZipperInstancesTest extends WordSpecLike with Matchers {
  "Zipper" should {
    "move right" in {
      inc.zipper.toRight should be(Zipper(1 #:: LazyList.empty, 2, inc.drop(2)))
    }

    "not move left" in {
      inc.zipper.toLeft should be(inc.zipper)
    }

    "move left" in {
      inc.zipper.toRight.toLeft should be(inc.zipper)
    }
  }
}
