package dao

import logic.Property

import scala.concurrent.Future

trait PropertyDao {
  def clearAndSave(newProperties: Seq[Property]): Unit
  def findByKeywords(keywords: Seq[String]): Future[Seq[Property]]
}
