package controllers

import javax.inject.{Inject, Singleton}

import logic.PropertiesSearch
import play.api.mvc._
import org.json4s.{NoTypeHints}
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

import scala.concurrent.ExecutionContext

@Singleton
class MainController @Inject()(propertiesSearch: PropertiesSearch)(implicit ec: ExecutionContext) extends Controller {

  val version = "1.0"

  private implicit val formats = Serialization.formats(NoTypeHints)

  def getVersion = Action { Ok(s"Property Search v$version") }

  def getPropertiesByKeywords = Action.async(parse.json) { request =>
    val keywords = request.body.as[Seq[String]]
    propertiesSearch.findByKeywords(keywords).map { properties => Ok(write(properties)).as(JSON) }
  }

}
