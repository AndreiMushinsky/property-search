package logic

import scala.concurrent.Future

case class Property(id: Int, latitude: Double, longitude: Double, price: Double, priceCurrency: String, keywords: String)

trait PropertiesSearch {
  def findByKeywords(keywords: Seq[String]): Future[Seq[Property]]
}
