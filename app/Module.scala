import com.google.inject.AbstractModule
import dao.{PostgresPropertyDao, PropertyDao}
import logic.actor.InitActor
import logic.{DBPropertiesSearch, PropertiesSearch}
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {

  override def configure(): Unit = {
    bindActor[InitActor]("initializer")
    bind(classOf[PropertyDao]).to(classOf[PostgresPropertyDao])
    bind(classOf[PropertiesSearch]).to(classOf[DBPropertiesSearch]).asEagerSingleton()
  }

}
