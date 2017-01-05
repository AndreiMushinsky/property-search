package logic

import javax.inject.{Inject, Named, Singleton}

import akka.actor._
import dao.PropertyDao
import logic.actor.InitMessage

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DBPropertiesSearch @Inject()(@Named("initializer") initializer: ActorRef, propertyDao: PropertyDao)
                                  (implicit ec: ExecutionContext) extends PropertiesSearch {

  initializer ! InitMessage

  override def findByKeywords(keywords: Seq[String]): Future[Seq[Property]] = propertyDao.findByKeywords(keywords)

}
