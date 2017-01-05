package logic.actor

import javax.inject.Inject

import akka.actor.Actor
import dao.PropertyDao
import logic.Property
import play.api.Configuration
import play.api.libs.ws._
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.concurrent.ExecutionContext

object InitMessage

class InitActor @Inject()(ws: WSClient, configuration: Configuration, propertyDao: PropertyDao)
                         (implicit ec: ExecutionContext) extends Actor {

  val serviceUrl = configuration.getString("config.actor.service.url").get

  override def receive: Receive = {
    case InitMessage => {
      val result = ws.url(serviceUrl).get().map { response => extractProperties(response.body) }
      result.onSuccess { case properies => propertyDao.clearAndSave(properies) }
      result.onFailure { case error => println(error) }
    }
  }

  private implicit val formats = DefaultFormats

  private def extractProperties(json: String): Seq[Property] = {
    implicit val intDefault = 0
    implicit val doubleDefault = 0.0
    implicit val stringDefault = ""

    def * [T] (key: String)(implicit obj: Map[String, Any], default: T): T = obj.get(key).map { value =>
      (value, default) match {
        case (it: BigInt, default: Double) => it.toDouble.asInstanceOf[T]
        case (it, default) if default.getClass == it.getClass => it.asInstanceOf[T]
        case _ => default
    }}.getOrElse(default)

    (parse(json) \\ "listings").extract[Seq[Map[String, _]]].map { implicit fields =>
      Property(*("id"), *("latitude"), *("longitude"), *("price"), *("price_currency"), *("keywords"))
    }
  }

}
