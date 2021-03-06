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

package net.duboue.thoughtland

import java.net.URI
import java.io.File

/**
 * @author Pablo Duboue <pablo.duboue@gmail.com>
 */
object App {

  def main(args: Array[String]) {
    val tmpDir = new File(File.createTempFile("thoughland", "").getAbsolutePath() + ".dir")
    tmpDir.mkdirs()

    val generated = ThoughtlandDriver("default").apply(TrainingData(new URI(args(0))), args(1),
      args.drop(2), 500)(Environment(new File("/tmp"), tmpDir, Config(1, false)))

    System.out.println(generated)
    System.exit(0)
  }
}
