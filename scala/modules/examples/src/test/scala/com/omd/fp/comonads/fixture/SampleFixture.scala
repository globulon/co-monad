package com.omd.fp.comonads.fixture

private[fixture] trait SampleFixture {
  final def avg[N](n: Int)(implicit F: Fractional[N]): LazyList[N] => N =
    ns => F.div(ns.take(n).sum[N], F.fromInt(n))

  val inc: LazyList[Double] = 1 #:: inc.map(_ + 1)
}
