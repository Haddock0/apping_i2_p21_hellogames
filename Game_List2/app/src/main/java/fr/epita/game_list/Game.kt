package fr.epita.game_list

class Game {
    val id      : Int
    val name    : String
    val picture : String

    constructor(id: Int, name: String, picture: String) {
        this.id = id
        this.name = name
        this.picture = picture
    }
}