package org.functionalkoans.forscala

import org.scalatest._
import support.Master

class Koans extends Suite {
  override def nestedSuites = List(
    new AboutValAndVar,
    new AboutLiteralNumbers,
    new AboutLiteralStrings,
    new AboutClasses,
    new AboutCaseClasses,
    new AboutObjects,
    new AboutTuples,
    new AboutLists,
    new AboutPatternMatching,
    new AboutPreconditions
  )

  override def run(testName: Option[String], reporter: Reporter, stopper: Stopper, filter: Filter,
                   configMap: Map[String, Any], distributor: Option[Distributor], tracker: Tracker) {
    super.run(testName, reporter, Master, filter, configMap, distributor, tracker)
  }

}
