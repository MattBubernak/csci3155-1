package org.functionalkoans.forscala

import org.scalatest._
import support.Master

class Koans extends Suite {
  override def nestedSuites = List(
    new AboutMaps,
    new AboutOptions,
    new AboutRange,
    new AboutRecursion,
    new AboutHigherOrderFunctions,
    new AboutPartiallyAppliedFunctions,
    new AboutPartialFunctions,
    new AboutForExpressions,
    new AboutNamedAndDefaultArguments,
    new AboutParentClasses,
    new AboutMethods,
    new AboutEmptyValues,
    new AboutTypeSignatures,
    new AboutExtractors
  )

  override def run(testName: Option[String], reporter: Reporter, stopper: Stopper, filter: Filter,
                   configMap: Map[String, Any], distributor: Option[Distributor], tracker: Tracker) {
    super.run(testName, reporter, Master, filter, configMap, distributor, tracker)
  }

}
