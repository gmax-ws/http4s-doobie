package gmax.repo

import doobie.ConnectionIO
import doobie.implicits.toSqlInterpolator

case class Person(id: Int, name: String, age: Int)

class PersonEntity {

  def find(id: Long): ConnectionIO[Option[Person]] =
    sql"SELECT id, name, age FROM persons WHERE id = $id".query[Person].option

  def all(): ConnectionIO[List[Person]] =
    sql"SELECT id, name, age FROM persons".query[Person].to[List]

  def insert(person: Person): ConnectionIO[Int] =
    sql"INSERT INTO persons (id, name, age) VALUES (${person.id},  ${person.name}, ${person.age})".update.run

  def update(person: Person): ConnectionIO[Int] =
    sql"UPDATE persons SET name = ${person.name}, age = ${person.age} WHERE id = ${person.id}".update.run

  def delete(id: Long): ConnectionIO[Int] =
    sql"DELETE FROM persons WHERE id = $id".update.run

  def drop(): ConnectionIO[Int] = sql"DROP TABLE IF EXISTS persons".update.run

  def create(): ConnectionIO[Int] =
    sql"""CREATE TABLE IF NOT EXISTS persons (
        id         INT PRIMARY KEY,
        name       VARCHAR NOT NULL,
        age        INT NOT NULL)""".update.run
}

object PersonEntity {
  def apply() = new PersonEntity()
}