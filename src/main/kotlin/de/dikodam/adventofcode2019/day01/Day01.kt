package de.dikodam.adventofcode2019.day01

import de.dikodam.adventofcode2019.utils.printTiming
import de.dikodam.adventofcode2019.utils.withTimer

fun main() {
    val (preppedInput, setupDuration) = withTimer {
        day01input
            .map { it.toInt() }
    }

    val (task1fuel, t1duration) = withTimer {
        preppedInput
            .map { computeFuelForMass(it) }
            .sum()
    }

    val (task2fuel, t2duration) = withTimer {
        preppedInput
            .map { computeFuelForMass(it) }
            .map { it + computeFuelForFuel(it) }
            .sum()
    }

    println("Task 1 fuel: $task1fuel.")
    println("Task 2 fuel: $task2fuel.")
    printTiming(setupDuration, t1duration, t2duration)
}

private fun computeFuelForMass(mass: Int): Int = mass / 3 - 2

private fun computeFuelForFuel(fuelmass: Int): Int {
    val newFuel = computeFuelForMass(fuelmass)
    return if (newFuel > 0) newFuel + computeFuelForFuel(newFuel) else 0
}

private val day01input: List<String> =
    """147129
128896
86366
121702
106854
107418
96021
116460
100395
149526
146314
56215
59911
96016
86483
115837
84522
137658
105769
149691
127499
95302
53109
101940
106343
140421
88790
105898
68085
85027
99405
116253
55338
50009
58244
145865
145270
148777
139954
147397
128691
63082
144279
76143
73006
105508
62796
144807
66587
50828
143778
73793
76852
119991
103181
105618
106320
136345
68771
82534
94528
65802
74863
139414
65854
149543
87063
85691
148931
139653
90728
100710
110159
131407
129323
145874
127227
129006
105828
67468
136905
89273
133439
78783
90794
116324
132792
135413
142086
62659
59178
59080
122465
62753
112104
92551
90638
145232
133984
139994""".split("\n")
