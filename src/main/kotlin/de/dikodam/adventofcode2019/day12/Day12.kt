package de.dikodam.adventofcode2019.day12

import de.dikodam.adventofcode2019.utils.compareEqualsBy
import de.dikodam.adventofcode2019.utils.lcm
import kotlin.math.abs

fun main() {
    val moonsInput = day12input.split("\n")
        .map { it.drop(1).dropLast(1) }
        .map { line -> inputlineToCoordinates(line) }
        .map { coords ->
            Moon(
                pos = Vector3D(coords.first, coords.second, coords.third),
                vel = Vector3D(0, 0, 0)
            )
        }

    // TASK 1

    var moons = moonsInput
//    printSystemState(0, moons)
    repeat(1000) {
        //        i ->
        moons = completeTimeStep(moons)
//        printSystemState(i + 1, moons)
    }
    val totalSystemEnergy = moons.map { it.energy() }.sum()
    println("Task 1: total energy is $totalSystemEnergy")

    // TASK 2

    // determine cycle count for each coordinate dimension independently
    // determine least common multiple of all three

    val xCycleLength = moons.determineCycleLengthBy { moon -> Pair(moon.pos.x, moon.vel.x) }
    val yCycleLength = moons.determineCycleLengthBy { moon -> Pair(moon.pos.y, moon.vel.y) }
    val zCycleLength = moons.determineCycleLengthBy { moon -> Pair(moon.pos.z, moon.vel.z) }

    val cycleLength = lcm(xCycleLength, lcm(yCycleLength, zCycleLength))

    println("Task 2: Cycle length is $cycleLength")
}

private fun <T> List<Moon>.determineCycleLengthBy(selector: (Moon) -> T): Long {
    var newMoonState = this
    var count = 0L
    do {
        newMoonState = completeTimeStep(newMoonState)
        count++
    } while (
        !compareEqualsBy(newMoonState, this, selector)
    )
    return count
}

private fun completeTimeStep(moons: List<Moon>) =
    moons.map { moon -> moon.applyGravity(moons) }
        .map { moon -> moon.move() }

private fun inputlineToCoordinates(line: String): Triple<Int, Int, Int> {
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

data class Moon(val pos: Vector3D, val vel: Vector3D) {
    fun move(): Moon = this.copy(pos = pos + vel, vel = vel)

    fun applyGravity(moons: Collection<Moon>): Moon {
        val totalGravity = moons.map { otherMoon ->
            if (otherMoon == this) {
                Vector3D(0, 0, 0)
            } else {
                computeGravity(this.pos, otherMoon.pos)
            }
        }.reduce(Vector3D::plus)
        return this.copy(vel = vel + totalGravity)
    }

    fun potentialEnergy(): Int {
        val (px, py, pz) = pos
        return abs(px) + abs(py) + abs(pz)
    }

    fun kineticEnergy(): Int {
        val (vx, vy, vz) = vel
        return abs(vx) + abs(vy) + abs(vz)
    }

    fun energy(): Int = potentialEnergy() * kineticEnergy()

}

private fun computeGravity(v1: Vector3D, v2: Vector3D): Vector3D =
    Vector3D(
        computeGravity(v1.x, v2.x),
        computeGravity(v1.y, v2.y),
        computeGravity(v1.z, v2.z)
    )

private fun computeGravity(v1: Int, v2: Int): Int =
    when {
        v1 > v2 -> {
            -1
        }
        v1 == v2 -> {
            0
        }
        else -> 1
    }

fun printSystemState(step: Int, moons: Collection<Moon>) {
    println("After $step steps:")
    moons.forEach(::println)
}

const val testInput = """<x=-1, y=0, z=2>
<x=2, y=-10, z=-7>
<x=4, y=-8, z=8>
<x=3, y=5, z=-1>"""

const val day12input =
    """<x=-14, y=-4, z=-11>
<x=-9, y=6, z=-7>
<x=4, y=1, z=4>
<x=2, y=-14, z=-9>"""
