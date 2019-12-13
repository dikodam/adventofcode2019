package de.dikodam.adventofcode2019.day12

fun main() {
    val moons = day12input.split("\n")
        .map { it.drop(1).dropLast(1) }
        .map { line -> lineToCoordinates(line) }
        .map { coords ->
            Moon(
                position = Vector3D(coords.first, coords.second, coords.third),
                velocity = Vector3D(0, 0, 0)
            )
        }


    // val otherMoons = get All Moons Except Self
    //    ALTERNATIVE: make Pair of this moon with every other moon
    // fore each pair of this moon and other moons, compute gravitationalPull : Vector3D
    // add gravitationalPull to this moon's velocity
    // add velocity to position
    val moon = Moon(Vector3D(0, 0, 0), Vector3D(0, 0, 0))
    val newVelocity = Vector3D(1, 2, 3)
    moon.copy(velocity = newVelocity)


    // time++


}

private fun lineToCoordinates(line: String): Triple<Int, Int, Int> {
    val coords = line
        .split(",")
        .map { coordinate ->
            coordinate
                .split("=")[1]
                .toInt()
        }
    return Triple(coords[0], coords[1], coords[2])
}

data class Vector3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3D): Vector3D =
        Vector3D(this.x + other.x, this.y + other.y, this.z + other.z)
}

data class Moon(val position: Vector3D, val velocity: Vector3D) {
    fun move(): Moon = this.copy(position = position + velocity, velocity = velocity)

}


const val day12input = """<x=-14, y=-4, z=-11>
<x=-9, y=6, z=-7>
<x=4, y=1, z=4>
<x=2, y=-14, z=-9>""""
