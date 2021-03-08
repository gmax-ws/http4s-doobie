package gmax.routes

import cats.effect.IO
import gmax.json.KVJson._
import gmax.repo.{Person, PersonRepo}
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

sealed trait Routes extends Http4sDsl[IO]

object HealthRoutes extends Routes {
  def healthRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "ready" => Ok("ready")
    case GET -> Root / "live" => Ok("live")
  }
}

object PersonRoutes extends Routes {

  def personRoutes(personRepo: PersonRepo): HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / "persons" => Ok(personRepo.getPersons)

    case req@POST -> Root / "person" =>
      req.decode[Person] { person =>
        Ok(personRepo.addPerson(person))
      }

    case GET -> Root / "person" / IntVar(id) =>
      personRepo.getPerson(id) flatMap {
        case None => NotFound(kvJson("message", s"$id not found"))
        case Some(person) => Ok(person)
      }

    case req@PUT -> Root / "person" =>
      req.decode[Person] { person =>
        Ok(personRepo.updatePerson(person))
      }

    case DELETE -> Root / "person" / IntVar(id) =>
      Ok(personRepo.deletePerson(id))
  }
}