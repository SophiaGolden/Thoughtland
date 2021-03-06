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

package net.duboue.thoughtland.nlg

import net.duboue.thoughtland.Generator
import net.duboue.thoughtland.nlg.template.TemplateGenerator
import net.duboue.thoughtland.nlg.simplenlg.SimpleNlgGenerator

object GeneratorEngine extends Enumeration {
  type GeneratorEngine = GeneratorEngineVal

  case class GeneratorEngineVal(name: String, make: () => Generator) extends Val(name)

  val Template = GeneratorEngineVal("template", { () => new TemplateGenerator() })
  val SimpleNLG = GeneratorEngineVal("simplenlg", { () => new SimpleNlgGenerator() })

  implicit def valueToGeneratorEngine(v: Value): GeneratorEngineVal = v.asInstanceOf[GeneratorEngineVal]
}

object GeneratorFactory {
  def apply(engine: GeneratorEngine.GeneratorEngine) = engine.make()
  def apply(engine: String): Generator = GeneratorEngine.withName(engine.toLowerCase()).make()
}