package controllers

import java.util
import scala.jdk.CollectionConverters.ListHasAsScala;

object FormUtils {
    def doubleString(theList: util.List[String]): Seq[(String, String)] = {
      theList.asScala.map(value => (value, value)).toSeq
    }
}
