package de.dikodam.adventofcode2019.utils

class SquareMatrix(private val dimensions: Int, private val rows: Array<IntArray>) {


    companion object {
        fun build(dimensions: Int, initializer: (Int, Int) -> Int): SquareMatrix =
            SquareMatrix(dimensions,
                Array(dimensions) { i ->
                    IntArray(dimensions) { j ->
                        initializer(i, j)
                    }
                })
    }

    operator fun get(i: Int, j: Int) {

    }

    operator fun SquareMatrix.times(other: SquareMatrix) {
        SquareMatrix.build(dimensions) {}

    }

    operator fun SquareMatrix.times(other: IntArray) {

    }
}
