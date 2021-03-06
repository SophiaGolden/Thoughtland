/*
 *   This file is part of Thoughtland -- Verbalizing n-dimensional objects.
 *   Copyright (C) 2013 Pablo Duboue <pablo.duboue@gmail.com>
 * 
 *   Thoughtland is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as 
 *   published by the Free Software Foundation, either version 3 of 
 *   the License, or (at your option) any later version.
 *
 *   Meetdle is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *   
 *   You should have received a copy of the GNU Affero General Public 
 *   License along with Thoughtland.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.duboue.thoughtland.cloud.weka

import java.lang.reflect.Field

import net.duboue.thoughtland.CloudPoints
import net.duboue.thoughtland.Environment
import net.duboue.thoughtland.TrainingData
import weka.classifiers.Classifier

/**
 * A model parameters extractor. Uses reflection on JVMs with a permissive security
 * manager to extract private field values.
 *
 */
abstract class WekaClassifierExtractor[T <: Classifier] {

  def pinpoint(clazz: Class[_], fieldName: String): Field = {
    val field = clazz.getDeclaredField(fieldName)
    field.setAccessible(true)
    field
  }

  def extractInt(obj: Any, field: Field) = field.getInt(obj)
  def extractDouble(obj: Any, field: Field) = field.getDouble(obj)
  def extractFloat(obj: Any, field: Field) = field.getFloat(obj)
  def extractBoolean(obj: Any, field: Field) = field.getBoolean(obj)

  def extract[X](obj: Any, field: Field): X = field.get(obj).asInstanceOf[X]

  def extract(classifier: T): Array[Double]
}

/**
 * Extract an n-dimensional cloud of points where each point contains the parameters of
 * the model (e.g., weights in a neural network).
 */

class WekaCloudExtractor extends WekaCrossValExtractor {
  def apply(data: TrainingData, algo: String, baseParams: Array[String])(implicit env: Environment): CloudPoints = {

    // map the algo to the extractor
    val extractorName = "net.duboue.thoughtland.cloud.weka." + algo.split("\\.").last + "Extractor"
    val extractor = Class.forName(extractorName).newInstance().asInstanceOf[WekaClassifierExtractor[Classifier]]

    return apply(data, algo, baseParams,
      { (classifier, testInstance, expected, actual) =>
        extractor.extract(classifier)
      })
  }
}