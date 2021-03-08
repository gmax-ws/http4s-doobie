package gmax.repo

import cats.effect.{ContextShift, IO}
import cats.implicits.catsSyntaxTuple5Semigroupal
import doobie.implicits._
import doobie.util.transactor.Transactor.Aux

sealed trait PersonApi[F[_]] {
  def getPerson(id: Long): F[Option[Person]]

  def getPersons: F[List[Person]]

  def addPerson(person: Person): F[Int]

  def deletePerson(id: Long): F[Int]

  def updatePerson(person: Person): F[Int]
}

class PersonRepo(xa: Aux[IO, Unit], entity: PersonEntity)(implicit cs: ContextShift[IO]) extends PersonApi[IO] {

  def getPersons: IO[List[Person]] = entity.all().transact(xa)

  def getPerson(id: Long): IO[Option[Person]] = entity.find(id).transact(xa)

  def addPerson(person: Person): IO[Int] = entity.insert(person).transact(xa)

  def deletePerson(id: Long): IO[Int] = entity.delete(id).transact(xa)

  def updatePerson(person: Person): IO[Int] = entity.update(person).transact(xa)
}

object PersonRepo {
  def apply(xa: Aux[IO, Unit], entity: PersonEntity, createPerson: Boolean = false)
           (implicit cs: ContextShift[IO]): PersonRepo = {
    if (createPerson) {
      (entity.drop(),
        entity.create(),
        entity.insert(Person(1, "John Doe", 22)),
        entity.insert(Person(2, "Mark Bench", 13)),
        entity.insert(Person(3, "Alex Doth", 45))
        ).mapN(_ + _ + _ + _ + _)
        .transact(xa)
        .unsafeRunSync
    }
    new PersonRepo(xa, entity)
  }
}