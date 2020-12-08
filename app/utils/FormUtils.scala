package utils
import scala.jdk.CollectionConverters._

import java.util

object FormUtils {
  def doubleString(theList: util.List[String]): Seq[(String, String)] = {
    theList.asScala.map(value => (value, value)).toSeq
  }
}
