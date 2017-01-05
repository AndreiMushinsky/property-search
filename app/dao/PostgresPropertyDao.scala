package dao

import javax.inject.{Inject, Singleton}

import logic.Property
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.Tag
import slick.driver.PostgresDriver.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostgresPropertyDao @Inject() (dbConfigProvider: DatabaseConfigProvider)
                                    (implicit ec: ExecutionContext) extends PropertyDao {

  private val dataBase = dbConfigProvider.get[JdbcProfile].db

  override def clearAndSave(newProperties: Seq[Property]): Unit = dataBase.run {
    DBIO.seq(properties.delete, properties ++= newProperties).transactionally
  }

  override def findByKeywords(keywords: Seq[String]): Future[Seq[Property]] = {
    val query = keywords match {
      case Nil => properties
      case _ => for {
        property <- properties if keywords.map { keyword => property.keywords like s"%$keyword%" } reduceLeft { _ && _ }
      } yield property
    }
    dataBase.run(query.result)
  }

  class Properties(tag: Tag) extends Table[Property](tag, "property") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def latitude = column[Double]("latitude")
    def longitude = column[Double]("longitude")
    def price = column[Double]("price")
    def priceCurrency = column[String]("price_currency")
    def keywords = column[String]("keywords")
    def * = (id, latitude, longitude, price, priceCurrency, keywords) <> (Property.tupled, Property.unapply)
  }

  private val properties = TableQuery[Properties]


}
